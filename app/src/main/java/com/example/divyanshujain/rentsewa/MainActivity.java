package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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
        loginButtonFacebook.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
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
         /*   case R.id.visitorSignupBT:
                startActivity(new Intent(this, SignUpActivity.class));
                break;*/
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
        GraphRequest data_request = GraphRequest.newMeRequest(
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
                        }
                        if (response != null) {
                            FacebookRequestError error = response.getError();
                            if (error != null) {
                                Log.e(TAG, error.toString());
                            }
                        }

                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }

    private void parseFacebookResponse(JSONObject json_object) throws JSONException {

        JSONObject obj = new JSONObject(json_object.toString());
        String objname = obj.getString("name");
        String objid = obj.getString("id");
        String objemail = obj.getString("email");
       /* JSONObject urlobj = (JSONObject) obj.get("picture");
        JSONObject dataUrl = (JSONObject) urlobj.get("data");
        String url = dataUrl.getString("url");*/

    }
}
