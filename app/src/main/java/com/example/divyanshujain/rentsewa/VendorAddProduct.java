package com.example.divyanshujain.rentsewa;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.CategoryModel;
import com.example.divyanshujain.rentsewa.Models.CitiesModel;
import com.example.divyanshujain.rentsewa.Models.CountryModel;
import com.example.divyanshujain.rentsewa.Models.StateModel;
import com.example.divyanshujain.rentsewa.Models.SubCategoryModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.example.divyanshujain.rentsewa.Utils.Validation;
import com.example.divyanshujain.rentsewa.adapters.CityAdapter;
import com.example.divyanshujain.rentsewa.adapters.CountryAdapter;
import com.example.divyanshujain.rentsewa.adapters.SpinnerCategoryAdapter;
import com.example.divyanshujain.rentsewa.adapters.SpinnerSubCategoryAdapter;
import com.example.divyanshujain.rentsewa.adapters.StateAdapter;
import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.edittext.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VendorAddProduct extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @InjectView(R.id.addedImageLL)
    LinearLayout addedImageLL;
    @InjectView(R.id.addImageIV)
    ImageView addImageIV;
    @InjectView(R.id.titleET)
    EditText titleET;
    @InjectView(R.id.emailET)
    EditText emailET;
    @InjectView(R.id.phoneET)
    EditText phoneET;
    @InjectView(R.id.priceET)
    EditText priceET;
    @InjectView(R.id.pinCodeET)
    EditText pinCodeET;
    @InjectView(R.id.timeET)
    EditText timeET;
    @InjectView(R.id.descET)
    EditText descET;
    @InjectView(R.id.categorySP)
    Spinner categorySP;
    @InjectView(R.id.subCategorySP)
    Spinner subCategorySP;
    @InjectView(R.id.citiesSP)
    Spinner citiesSP;
    @InjectView(R.id.stateSP)
    Spinner stateSP;
    @InjectView(R.id.countrySP)
    Spinner countrySP;
    @InjectView(R.id.pLocationcitiesSP)
    Spinner pLocationcitiesSP;
    @InjectView(R.id.postBT)
    Button postBT;
    @InjectView(R.id.activity_vendor_add_product)
    RelativeLayout activityVendorAddProduct;

    CityAdapter productLocationCityAdapter;
    CityAdapter cityAdapter;
    StateAdapter stateAdapter;
    CountryAdapter countryAdapter;
    private SpinnerCategoryAdapter categoryAdapter;
    private SpinnerSubCategoryAdapter subCategoryAdapter;

    private ArrayList<CitiesModel> cityModels = new ArrayList<>();
    private ArrayList<CitiesModel> productLocationCityModels = new ArrayList<>();
    private ArrayList<StateModel> stateModels = new ArrayList<>();
    private ArrayList<CountryModel> countryModels = new ArrayList<>();
    private ArrayList<CategoryModel> categoryModels = new ArrayList<>();
    private ArrayList<SubCategoryModel> subCategoryModels = new ArrayList<>();

    private Validation validation;

    private String selectedCity = "", selectedCategoryID = "", selectedSubCategoryID = "", selectedStateName, selectedCountryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_add_product);
        ButterKnife.inject(this);

        initViews();
    }

    private void initViews() {

        productLocationCityAdapter = new CityAdapter(this, productLocationCityModels);
        cityAdapter = new CityAdapter(this, cityModels);
        stateAdapter = new StateAdapter(this, 0, stateModels);
        countryAdapter = new CountryAdapter(this, 0, countryModels);
        categoryAdapter = new SpinnerCategoryAdapter(this, categoryModels);
        subCategoryAdapter = new SpinnerSubCategoryAdapter(this, subCategoryModels);

        pLocationcitiesSP.setAdapter(productLocationCityAdapter);
        citiesSP.setAdapter(cityAdapter);
        stateSP.setAdapter(stateAdapter);
        countrySP.setAdapter(countryAdapter);
        categorySP.setAdapter(categoryAdapter);
        subCategorySP.setAdapter(subCategoryAdapter);

        pLocationcitiesSP.setOnItemSelectedListener(this);
        citiesSP.setOnItemSelectedListener(this);
        stateSP.setOnItemSelectedListener(this);
        countrySP.setOnItemSelectedListener(this);
        categorySP.setOnItemSelectedListener(this);
        subCategorySP.setOnItemSelectedListener(this);

        CallWebService.getInstance(this, true, ApiCodes.GET_PRODUCT_LOCATION).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_PRODUCT_LOCATION, null, this);
        CallWebService.getInstance(this, true, ApiCodes.GET_ALL_COUNTRY).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_ALL_COUNTRY, null, this);
    }

    @OnClick({R.id.addImageIV, R.id.postBT})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addImageIV:
                break;
            case R.id.postBT:
                break;
        }
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        switch (apiType) {
            case ApiCodes.GET_PRODUCT_LOCATION:
                productLocationCityModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), CitiesModel.class);
                break;
            case ApiCodes.GET_ALL_COUNTRY:
                break;
            case ApiCodes.GET_ALL_STATE:
                break;
            case ApiCodes.GET_ALL_CITIES:
                break;
            case ApiCodes.POST_PRODUCT:
                break;
            case ApiCodes.PRODUCT_EDIT_PROCESS:
                break;
            case ApiCodes.VENDOR_PRODUCT_LISTING:
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.categorySP:
                break;
            case R.id.subCategorySP:
                break;
            case R.id.citiesSP:
                break;
            case R.id.stateSP:
                break;
            case R.id.countrySP:
                break;
            case R.id.pLocationcitiesSP:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
