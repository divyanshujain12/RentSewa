package com.example.divyanshujain.rentsewa.Utils;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by divyanshu.jain on 11/30/2016.
 */
public class CommonFunctions {
    private static CommonFunctions ourInstance = new CommonFunctions();

    public static CommonFunctions getInstance() {
        return ourInstance;
    }

    private CommonFunctions() {
    }
    public void configureToolbarWithBackButton(final AppCompatActivity appCompatActivity, Toolbar toolbar, String name) {
        appCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        actionBar.setTitle(name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appCompatActivity.onBackPressed();
            }
        });
    }

    public void configureToolbarWithOutBackButton(final AppCompatActivity appCompatActivity, Toolbar toolbar, String name) {
        appCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        actionBar.setTitle(name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appCompatActivity.onBackPressed();
            }
        });
    }
}
