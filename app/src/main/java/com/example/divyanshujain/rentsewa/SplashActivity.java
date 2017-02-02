package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.Utils.MySharedPereference;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.divyanshujain.rentsewa.Constants.Constants.VENDOR;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    @InjectView(R.id.logoLL)
    LinearLayout logoLL;
    Animation logoAnim = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        logoAnim = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        logoAnim.setAnimationListener(this);
        logoLL.startAnimation(logoAnim);


    }

    private void go() {
        Intent i = null;
        if (!MySharedPereference.getInstance().getBoolean(SplashActivity.this, Constants.IS_LOGGED_IN)) {
            i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();

        } else {
            String userType = MySharedPereference.getInstance().getString(SplashActivity.this, Constants.USER_TYPE);
            if (userType.equalsIgnoreCase(VENDOR))
                goToVendorHome();
            else
                goToVisitorHome();

        }

    }

    Handler handler = new Handler();

    private void goToVisitorHome() {
        Intent categoryIntent = new Intent(this, HomeActivity.class);
        startActivity(categoryIntent);
        finish();
    }

    private void goToVendorHome() {
        Intent categoryIntent = new Intent(this, VendorListingActivity.class);
        startActivity(categoryIntent);
        finish();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                go();
            }
        }, 3000);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
