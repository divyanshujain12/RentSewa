package com.example.divyanshujain.rentsewa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.edittext.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;
    @InjectView(R.id.logo)
    ImageView logo;
    @InjectView(R.id.usernameET)
    EditText usernameET;
    @InjectView(R.id.emailET)
    EditText emailET;
    @InjectView(R.id.passwordET)
    EditText passwordET;
    @InjectView(R.id.confPasswordET)
    EditText confPasswordET;
    @InjectView(R.id.signUpBT)
    Button signUpBT;
    @InjectView(R.id.activity_login)
    FrameLayout activityLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        CommonFunctions.getInstance().configureToolbarWithBackButton(this, toolbarView, getString(R.string.create_new_account));
    }

    @OnClick(R.id.signUpBT)
    public void onClick() {
    }
}
