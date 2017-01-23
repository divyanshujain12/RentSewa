package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.UserModel;
import com.example.divyanshujain.rentsewa.Models.ValidationModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.MySharedPereference;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.example.divyanshujain.rentsewa.Utils.Validation;
import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.edittext.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VendorLoginActivity extends BaseActivity {

    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;
    @InjectView(R.id.logo)
    ImageView logo;
    @InjectView(R.id.usernameET)
    EditText usernameET;
    @InjectView(R.id.passwordET)
    EditText passwordET;
    @InjectView(R.id.loginBT)
    Button loginBT;
    @InjectView(R.id.activity_login)
    LinearLayout activityLogin;
    @InjectView(R.id.visitorSignupBT)
    Button visitorSignupBT;
    private String VENDOR = "vendor";
    private String VISITOR = "visitor";

    Validation validation;
    private HashMap<View, String> validationMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_vendor);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        CommonFunctions.getInstance().configureToolbarWithBackButton(this, toolbarView, getString(R.string.vendor_login));
        addValidation();
    }

    private void addValidation() {
        validation = new Validation();
        validation.addValidationField(new ValidationModel(usernameET, Validation.TYPE_NAME_VALIDATION, getString(R.string.err_user_name)));
        validation.addValidationField(new ValidationModel(passwordET, Validation.TYPE_PASSWORD_VALIDATION, getString(R.string.err_pass)));
        //emailET.setText("divyanshujain12@hotmail.com");
        //passwordET.setText("divyanshu");
    }


    @OnClick({R.id.loginBT, R.id.visitorSignupBT})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBT:
                goToHome();
                //validateFields();
                break;
            case R.id.visitorSignupBT:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }

    private void validateFields() {

        validationMap = validation.validate(this);
        if (validationMap != null) {
            CallWebService.getInstance(this, true, ApiCodes.LOGIN).hitJsonObjectRequestAPI(CallWebService.POST, API.LOGIN, createJsonForSignIn(), this);
        }
    }

    private JSONObject createJsonForSignIn() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.EMAIl, validationMap.get(usernameET));
            jsonObject.put(Constants.PASSWORD, validationMap.get(passwordET));
            jsonObject.put(Constants.USER_TYPE, VENDOR);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);

        UserModel userModel = UniversalParser.getInstance().parseJsonObject(response.getJSONObject(Constants.DATA), UserModel.class);
        MySharedPereference.getInstance().setString(this, Constants.NAME, userModel.getName());
        MySharedPereference.getInstance().setString(this, Constants.PHONE_NUMBER, userModel.getPhone());
        MySharedPereference.getInstance().setString(this, Constants.EMAIl, userModel.getEmail());
        MySharedPereference.getInstance().setString(this, Constants.DATE_OF_BIRTH, userModel.getDate_of_birth());
        MySharedPereference.getInstance().setString(this, Constants.PASSWORD, validationMap.get(passwordET));
        MySharedPereference.getInstance().setString(this, Constants.USER_ID, userModel.getUser_id());
        MySharedPereference.getInstance().setBoolean(this, Constants.IS_LOGGED_IN, true);

        goToHome();
    }

    private void goToHome() {
        Intent categoryIntent = new Intent(this, CategoriesActivity.class);
        startActivity(categoryIntent);
        finish();
    }
}
