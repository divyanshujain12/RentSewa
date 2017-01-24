package com.example.divyanshujain.rentsewa;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.CustomViews.CustomAlertDialogs;
import com.example.divyanshujain.rentsewa.CustomViews.CustomTopSnackbars;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.ValidationModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.Validation;
import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.edittext.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VendorSignupActivity extends BaseActivity {

    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;
    @InjectView(R.id.logo)
    ImageView logo;

    @InjectView(R.id.emailET)
    EditText emailET;
    @InjectView(R.id.phoneET)
    EditText phoneET;
    @InjectView(R.id.passwordET)
    EditText passwordET;
    @InjectView(R.id.confPasswordET)
    EditText confPasswordET;
    @InjectView(R.id.signUpBT)
    Button signUpBT;
    @InjectView(R.id.activity_login)
    FrameLayout activityLogin;

    Validation validation;
    @InjectView(R.id.shopNameET)
    EditText shopNameET;
    private HashMap<View, String> formValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_signup);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        CommonFunctions.getInstance().configureToolbarWithBackButton(this, toolbarView, getString(R.string.create_new_account));
        addValidation();
    }

    private void addValidation() {
        validation = new Validation();
        validation.addValidationField(new ValidationModel(shopNameET, Validation.TYPE_NAME_VALIDATION, getString(R.string.err_user_name)));
        validation.addValidationField(new ValidationModel(phoneET, Validation.TYPE_PHONE_VALIDATION, getString(R.string.err_phone_number)));
        validation.addValidationField(new ValidationModel(emailET, Validation.TYPE_EMAIL_VALIDATION, getString(R.string.err_email)));
        validation.addValidationField(new ValidationModel(passwordET, Validation.TYPE_PASSWORD_VALIDATION, getString(R.string.err_pass)));
        validation.addValidationField(new ValidationModel(confPasswordET, Validation.TYPE_PASSWORD_VALIDATION, getString(R.string.err_re_enter_pass)));
    }

    @OnClick(R.id.signUpBT)
    public void onClick() {
        hitWebService();
    }

    private void hitWebService() {
        formValues = validation.validate(this);
        if (formValues != null) {
            if (!formValues.get(passwordET).equals(formValues.get(confPasswordET))) {
                CustomTopSnackbars.getInstance().getErrorSnackbar(this).setText(R.string.err_password_mismatch).show();
                return;
            }

            CallWebService.getInstance(this, true, ApiCodes.REGISTRATION).hitJsonObjectRequestAPI(CallWebService.POST, API.REGISTRATION, createJsonForSignUp(), this);
        }
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        CustomAlertDialogs.showAlertDialog(this, getString(R.string.congratulation), response.getString(Constants.MESSAGE), this);
    }

    private JSONObject createJsonForSignUp() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.SHOP_NAME, formValues.get(shopNameET));
            jsonObject.put(Constants.PHONE_NUMBER, formValues.get(phoneET));
            jsonObject.put(Constants.EMAIl, formValues.get(emailET));
            jsonObject.put(Constants.PASSWORD, formValues.get(passwordET));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void doAction() {
        super.doAction();
        onBackPressed();
    }
}


