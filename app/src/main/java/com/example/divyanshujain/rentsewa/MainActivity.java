package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.UserModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.MySharedPereference;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.neopixl.pixlui.components.button.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements FacebookCallback<LoginResult> {


    private static final String TAG = "MainActivity";
    @InjectView(R.id.logo)
    ImageView logo;
    @InjectView(R.id.loginAsVendorBT)
    Button loginAsVendorBT;
    @InjectView(R.id.loginAsVisitorBT)
    Button loginAsVisitorBT;
    @InjectView(R.id.loginButtonFacebook)
    LoginButton loginButtonFacebook;
    @InjectView(R.id.activity_main)
    FrameLayout activityMain;
    private CallbackManager callbackManager;
    String objname, objid, objemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        setUpFacebookButton();
        //  CommonFunctions.getInstance().configureToolbarWithOutBackButton(this,toolbarView,"");
    }

    private void setUpFacebookButton() {
        loginButtonFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButtonFacebook.setBackgroundColor(android.R.color.transparent);
        loginButtonFacebook.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf"));
        loginButtonFacebook.setTextSize(TypedValue.COMPLEX_UNIT_SP, (getResources().getDimension(R.dimen.eighteen_sp) / getResources().getDisplayMetrics().density));
        loginButtonFacebook.registerCallback(callbackManager, this);
    }


    protected void facebookSDKInitialize() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }


    @OnClick({R.id.loginAsVendorBT, R.id.loginAsVisitorBT})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.loginAsVendorBT:
                startActivity(new Intent(this, VendorLoginActivity.class));
                break;
            case R.id.loginAsVisitorBT:
                startActivity(new Intent(this, VisitorLoginActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onSuccess(LoginResult loginResult) {
        getUserInfo(loginResult);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }

    public void getUserInfo(LoginResult login_result) {
        GraphRequest request = GraphRequest.newMeRequest(
                login_result.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {
                        try {
                            parseFacebookResponse(json_object);
                        } catch (Exception e) {
                            e.printStackTrace();
                            LoginManager.getInstance().logOut();
                        }
                        if (response != null) {
                            FacebookRequestError error = response.getError();
                            if (error != null) {
                                Log.e(TAG, error.toString());
                            }
                        }

                    }

                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void parseFacebookResponse(JSONObject json_object) throws JSONException {

        JSONObject obj = new JSONObject(json_object.toString());
        objname = obj.getString("name");
        objid = obj.getString("id");
        objemail = obj.optString("email");

        hitApiForValidateUser();
    }

    private void hitApiForValidateUser() {
        CallWebService.getInstance(this, true, ApiCodes.REGISTRATION).hitJsonObjectRequestAPI(CallWebService.POST, API.REGISTRATION_FB_VISITOR, createJsonForVisitorLogin(), this);
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);

        UserModel userModel = UniversalParser.getInstance().parseJsonObject(response.getJSONObject(Constants.DATA), UserModel.class);
        saveDataInSharedPrefs(userModel);

    }

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
        switch (apiType) {
            case ApiCodes.REGISTRATION:
                Intent intent = new Intent(this, VisitorSignUpActivity.class);
                intent.putExtra(Constants.EMAIl, objemail);
                intent.putExtra(Constants.FB_ID, objid);
                intent.putExtra(Constants.NAME, objname);
                startActivity(intent);
                break;
        }
    }

    private void saveDataInSharedPrefs(UserModel userModel) {
        MySharedPereference.getInstance().setString(this, Constants.NAME, userModel.getName());
        MySharedPereference.getInstance().setString(this, Constants.PHONE_NUMBER, userModel.getPhone());
        MySharedPereference.getInstance().setString(this, Constants.EMAIl, userModel.getEmail());
        MySharedPereference.getInstance().setString(this, Constants.DATE_OF_BIRTH, userModel.getDate_of_birth());
        MySharedPereference.getInstance().setString(this, Constants.USER_ID, userModel.getUser_id());
        MySharedPereference.getInstance().setString(this, Constants.USER_TYPE, userModel.getUser_type());
        MySharedPereference.getInstance().setBoolean(this, Constants.IS_LOGGED_IN, true);
        CommonFunctions.getInstance().sendFCMidToServer(this);
        goToVisitorHome();
    }



    private JSONObject createJsonForVisitorLogin() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.FB_ID, objid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    private void goToVisitorHome() {
        Intent categoryIntent = new Intent(this, HomeActivity.class);
        startActivity(categoryIntent);
        finish();
    }
}
