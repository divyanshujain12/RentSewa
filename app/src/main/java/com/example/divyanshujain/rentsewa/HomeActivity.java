package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.CustomViews.CustomToasts;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.CategoryModel;
import com.example.divyanshujain.rentsewa.Models.CitiesModel;
import com.example.divyanshujain.rentsewa.Models.ProductsModel;
import com.example.divyanshujain.rentsewa.Models.SubCategoryModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.example.divyanshujain.rentsewa.adapters.CityAdapter;
import com.example.divyanshujain.rentsewa.adapters.SpinnerCategoryAdapter;
import com.example.divyanshujain.rentsewa.adapters.SpinnerSubCategoryAdapter;
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

public class HomeActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;


    @InjectView(R.id.goBT)
    Button goBT;
    @InjectView(R.id.categoriesBT)
    Button categoriesBT;
    @InjectView(R.id.activity_home)
    LinearLayout activityHome;
    @InjectView(R.id.citiesSP)
    Spinner citiesSP;
    @InjectView(R.id.categorySP)
    Spinner categorySP;
    @InjectView(R.id.subCategorySP)
    Spinner subCategorySP;

    private ArrayList<CitiesModel> citiesModels = new ArrayList<>();
    private ArrayList<CategoryModel> categoryModels = new ArrayList<>();
    private ArrayList<SubCategoryModel> subCategoryModels = new ArrayList<>();
    private ArrayList<ProductsModel> productsModels = new ArrayList<>();
    private CityAdapter cityAdapter;
    private SpinnerCategoryAdapter categoryAdapter;
    private SpinnerSubCategoryAdapter subCategoryAdapter;
    private String selectedCity, selectedCategoryID, selectedSubCategoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        citiesSP.setOnItemSelectedListener(this);
        categorySP.setOnItemSelectedListener(this);
        subCategorySP.setOnItemSelectedListener(this);
        CallWebService.getInstance(this, true, ApiCodes.GET_CITIES).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_CITIES, null, this);

    }

    @OnClick({R.id.goBT, R.id.categoriesBT})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goBT:
                onGoBtClick();
                break;
            case R.id.categoriesBT:
                Intent categoryIntent = new Intent(this, CategoriesActivity.class);
                startActivity(categoryIntent);
                break;
        }
    }

    private void onGoBtClick() {
        if (!selectedCity.equals("") && !selectedCategoryID.equals("") && !selectedSubCategoryID.equals("")) {
            CallWebService.getInstance(this, true, ApiCodes.FILTER).hitJsonObjectRequestAPI(CallWebService.POST, API.FILTER_CATEGORY, createJsonForFilter(), this);
        } else {
            CustomToasts.getInstance(this).showErrorToast(getString(R.string.err_select_all_filters));
        }
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        switch (apiType) {
            case ApiCodes.FILTER:
                parseFilterData(response);
                break;
            case ApiCodes.GET_CITIES:
                parseAndSetCityData(response);
                break;
            case ApiCodes.GET_CATEGORIES:
                parseAndSetCategoryData(response);
                break;
        }
    }

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
        CustomToasts.getInstance(this).showErrorToast(str);
    }

    private void parseFilterData(JSONObject response) throws JSONException {
        productsModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), ProductsModel.class);
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.putExtra(Constants.DATA, productsModels);
        startActivity(intent);
    }

    private void parseAndSetCityData(JSONObject response) throws JSONException {
        citiesModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), CitiesModel.class);
        if (citiesModels != null) {
            cityAdapter = new CityAdapter(this, citiesModels);
            citiesSP.setAdapter(cityAdapter);
            CallWebService.getInstance(this, true, ApiCodes.GET_CATEGORIES).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_CATEGORIES, null, this);
        }
    }

    private void parseAndSetCategoryData(JSONObject response) throws JSONException {
        categoryModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), CategoryModel.class);
        if (categoryModels != null) {
            categoryAdapter = new SpinnerCategoryAdapter(this, categoryModels);
            categorySP.setAdapter(categoryAdapter);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.citiesSP:
                onCitySelected(i);
                break;
            case R.id.categorySP:
                onCategorySelected(i);
                break;
            case R.id.subCategorySP:
                onSubCategorySelected(i);
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void onCitySelected(int i) {
        selectedCity = citiesModels.get(i).getCity_name();
    }

    private void onCategorySelected(int i) {
        selectedCategoryID = categoryModels.get(i).getId();
        setSubCatAdapter(i);
    }

    private void onSubCategorySelected(int i) {
        selectedSubCategoryID = subCategoryModels.get(i).getId();
    }

    private void setSubCatAdapter(int i) {
        subCategoryModels = categoryModels.get(i).getSubcatData();
        if (subCategoryModels != null) {
            subCategoryAdapter = new SpinnerSubCategoryAdapter(this, subCategoryModels);
            subCategorySP.setAdapter(subCategoryAdapter);
        }
    }

    private JSONObject createJsonForFilter() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.FILTER_CITY, selectedCity);
            jsonObject.put(Constants.FILTER_CATEGORY, selectedCategoryID);
            jsonObject.put(Constants.FILTER_SUB_CATEGORY, selectedSubCategoryID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
