package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.edittext.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_vendor);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        CommonFunctions.getInstance().configureToolbarWithBackButton(this, toolbarView, getString(R.string.vendor_login));
    }

    @OnClick(R.id.loginBT)
    public void onClick() {
        startActivity(new Intent(this,CategoriesActivity.class));
    }
}
