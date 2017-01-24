package com.example.divyanshujain.rentsewa.Utils;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.fragments.RuntimePermissionHeadlessFragment;

import org.json.JSONException;
import org.json.JSONObject;

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

    public RuntimePermissionHeadlessFragment addRuntimePermissionFragment(AppCompatActivity activity, RuntimePermissionHeadlessFragment.PermissionCallback permissionCallback) {
        RuntimePermissionHeadlessFragment runtimePermissionHeadlessFragment = RuntimePermissionHeadlessFragment.newInstance(permissionCallback);
        activity.getSupportFragmentManager().beginTransaction().add(runtimePermissionHeadlessFragment, runtimePermissionHeadlessFragment.getClass().getName()).commit();
        return runtimePermissionHeadlessFragment;
    }

    public static JSONObject customerIdJsonObject(Activity userSettingActivity) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.USER_ID, MySharedPereference.getInstance().getString(userSettingActivity, Constants.USER_ID));
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void hideKeyBoard(Activity activity, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
