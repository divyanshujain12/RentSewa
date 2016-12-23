package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.neopixl.pixlui.components.button.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.loginAsVendorBT)
    Button loginAsVendorBT;
    @InjectView(R.id.loginAsVisitorBT)
    Button loginAsVisitorBT;
    @InjectView(R.id.activity_main)
    FrameLayout activityMain;
    @InjectView(R.id.visitorSignupBT)
    Button visitorSignupBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        //  CommonFunctions.getInstance().configureToolbarWithOutBackButton(this,toolbarView,"");
    }

    @OnClick({R.id.loginAsVendorBT, R.id.loginAsVisitorBT,R.id.visitorSignupBT})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginAsVendorBT:
                startActivity(new Intent(this, VendorLoginActivity.class));
                break;
            case R.id.loginAsVisitorBT:
                startActivity(new Intent(this, VisitorLoginActivity.class));
                break;
            case R.id.visitorSignupBT:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }


}
