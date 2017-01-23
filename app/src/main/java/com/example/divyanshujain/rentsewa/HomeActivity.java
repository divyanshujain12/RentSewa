package com.example.divyanshujain.rentsewa;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.CitiesModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.neopixl.pixlui.components.button.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by divyanshu.jain on 1/23/2017.
 */

public class HomeActivity extends BaseActivity {
    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;
    @InjectView(R.id.categorySP)
    Spinner categorySP;
    @InjectView(R.id.subCategorySP)
    Spinner subCategorySP;
    @InjectView(R.id.subSubCategorySP)
    Spinner subSubCategorySP;
    @InjectView(R.id.goBT)
    Button goBT;
    @InjectView(R.id.categoriesBT)
    Button categoriesBT;
    @InjectView(R.id.activity_home)
    LinearLayout activityHome;

    private ArrayList<CitiesModel> citiesModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        CallWebService.getInstance(this,true, ApiCodes.GET_CITIES).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_CITIES,null,this);
    }

    @OnClick({R.id.goBT, R.id.categoriesBT})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goBT:
                break;
            case R.id.categoriesBT:
                break;
        }
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        switch (apiType){
            case ApiCodes.GET_CITIES:
                break;
        }
    }
}
