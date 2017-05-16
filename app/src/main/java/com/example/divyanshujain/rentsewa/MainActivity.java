package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.ImageModel;
import com.example.divyanshujain.rentsewa.Models.UserModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.MySharedPereference;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.example.divyanshujain.rentsewa.adapters.CustomPagerAdapter;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

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
    RelativeLayout activityMain;
    @InjectView(R.id.backgroundImagesVP)
    ViewPager backgroundImagesVP;
    private CallbackManager callbackManager;
    String objname, objid, objemail;
    ArrayList<ImageModel> imageModels;
    private CustomPagerAdapter customPagerAdapter;
    int currentItem;

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

        CallWebService.getInstance(this, false, ApiCodes.GET_SLIDER).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_SLIDER, null, this);
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
        switch (apiType) {
            case ApiCodes.REGISTRATION:
                UserModel userModel = UniversalParser.getInstance().parseJsonObject(response.getJSONObject(Constants.DATA), UserModel.class);
                saveDataInSharedPrefs(userModel);
                break;
            case ApiCodes.GET_SLIDER:
                imageModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), ImageModel.class);
                customPagerAdapter = new CustomPagerAdapter(this, imageModels);
                backgroundImagesVP.setAdapter(customPagerAdapter);
                startTimer();
                break;
        }

    }

    private void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentItem = backgroundImagesVP.getCurrentItem();
                if (currentItem < imageModels.size() - 1) {
                    currentItem++;
                } else {
                    currentItem = 0;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        backgroundImagesVP.setCurrentItem(currentItem);
                    }
                });

            }
        }, 2000, 2000);
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
