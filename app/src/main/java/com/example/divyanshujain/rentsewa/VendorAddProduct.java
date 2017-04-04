package com.example.divyanshujain.rentsewa;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.PictureHelper;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.example.divyanshujain.rentsewa.Utils.Validation;
import com.example.divyanshujain.rentsewa.adapters.AddImagesRvAdapter;
import com.example.divyanshujain.rentsewa.adapters.CityAdapter;
import com.example.divyanshujain.rentsewa.adapters.CountryAdapter;
import com.example.divyanshujain.rentsewa.adapters.SpinnerCategoryAdapter;
import com.example.divyanshujain.rentsewa.adapters.SpinnerSubCategoryAdapter;
import com.example.divyanshujain.rentsewa.adapters.StateAdapter;
import com.example.divyanshujain.rentsewa.fragments.RuntimePermissionHeadlessFragment;
import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.edittext.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VendorAddProduct extends BaseActivity implements AdapterView.OnItemSelectedListener, RuntimePermissionHeadlessFragment.PermissionCallback {


    private static final int EXTERNAL_STORAGE_REQUEST = 101;
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

    private static final int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;

    CityAdapter productLocationCityAdapter;
    CityAdapter cityAdapter;
    StateAdapter stateAdapter;
    CountryAdapter countryAdapter;
    @InjectView(R.id.scrollview)
    ScrollView scrollview;
    @InjectView(R.id.productImagesRV)
    RecyclerView productImagesRV;
    private SpinnerCategoryAdapter categoryAdapter;
    private SpinnerSubCategoryAdapter subCategoryAdapter;

    private ArrayList<CitiesModel> cityModels = new ArrayList<>();
    private ArrayList<CitiesModel> productLocationCityModels = new ArrayList<>();
    private ArrayList<StateModel> stateModels = new ArrayList<>();
    private ArrayList<CountryModel> countryModels = new ArrayList<>();
    private ArrayList<CategoryModel> categoryModels = new ArrayList<>();
    private ArrayList<SubCategoryModel> subCategoryModels = new ArrayList<>();
    protected String[] mRequiredPermissions = {};
    RuntimePermissionHeadlessFragment runtimePermissionHeadlessFragment;
    ArrayList<Bitmap> bitmapsList = new ArrayList<>();
    private Validation validation;
    private AddImagesRvAdapter addImagesRvAdapter;


    private String selectedProductLocationID, selectedCityID, selectedCategoryID, selectedSubCategoryID, selectedStateID, selectedCountryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_add_product);
        ButterKnife.inject(this);

        initViews();
    }

    private void initViews() {

        mRequiredPermissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        runtimePermissionHeadlessFragment = CommonFunctions.getInstance().addRuntimePermissionFragment(this, this);

        scrollview.requestFocusFromTouch();
        pLocationcitiesSP.setOnItemSelectedListener(this);
        citiesSP.setOnItemSelectedListener(this);
        stateSP.setOnItemSelectedListener(this);
        countrySP.setOnItemSelectedListener(this);
        categorySP.setOnItemSelectedListener(this);
        subCategorySP.setOnItemSelectedListener(this);
        bitmapsList.add(null);
        productImagesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        addImagesRvAdapter = new AddImagesRvAdapter(this, bitmapsList, this);
        productImagesRV.setAdapter(addImagesRvAdapter);


        CallWebService.getInstance(this, false, ApiCodes.GET_PRODUCT_LOCATION).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_PRODUCT_LOCATION, null, this);
        CallWebService.getInstance(this, false, ApiCodes.GET_ALL_COUNTRY).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_ALL_COUNTRY, null, this);
        CallWebService.getInstance(this, false, ApiCodes.GET_CATEGORIES).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_CATEGORIES, null, this);
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

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        switch (apiType) {
            case ApiCodes.GET_PRODUCT_LOCATION:
                productLocationCityModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), CitiesModel.class);
                productLocationCityAdapter = new CityAdapter(this, productLocationCityModels);
                pLocationcitiesSP.setAdapter(productLocationCityAdapter);
                break;
            case ApiCodes.GET_ALL_COUNTRY:
                countryModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), CountryModel.class);
                countryAdapter = new CountryAdapter(this, 0, countryModels);
                countrySP.setAdapter(countryAdapter);
                break;
            case ApiCodes.GET_ALL_STATE:
                stateModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), StateModel.class);
                stateAdapter = new StateAdapter(this, 0, stateModels);
                stateSP.setAdapter(stateAdapter);
                break;
            case ApiCodes.GET_ALL_CITIES:
                cityModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), CitiesModel.class);
                cityAdapter = new CityAdapter(this, cityModels);
                citiesSP.setAdapter(cityAdapter);
                break;
            case ApiCodes.GET_CATEGORIES:
                categoryModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), CategoryModel.class);
                categoryAdapter = new SpinnerCategoryAdapter(this, categoryModels);
                categorySP.setAdapter(categoryAdapter);
                break;
            case ApiCodes.POST_PRODUCT:
                break;
            case ApiCodes.PRODUCT_EDIT_PROCESS:
                break;


        }

    }


    @Override
    public void onClickItem(int position, View view) {
        super.onClickItem(position, view);
        runtimePermissionHeadlessFragment.addAndCheckPermission(mRequiredPermissions, EXTERNAL_STORAGE_REQUEST);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.categorySP:
                selectedCategoryID = categoryModels.get(position).getId();
                setUpSubCategroySP(position);
                break;
            case R.id.subCategorySP:
                selectedSubCategoryID = subCategoryModels.get(position).getId();
                break;
            case R.id.citiesSP:
                selectedCityID = cityModels.get(position).getId();
                break;
            case R.id.stateSP:
                selectedStateID = stateModels.get(position).getState_id();
                CallWebService.getInstance(this, true, ApiCodes.GET_ALL_CITIES).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_ALL_CITIES, createJsonForGettingCities(selectedStateID), this);
                break;
            case R.id.countrySP:
                selectedCountryID = countryModels.get(position).getCountry_id();
                CallWebService.getInstance(this, true, ApiCodes.GET_ALL_STATE).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_ALL_STATE, createJsonForGettingStates(selectedCountryID), this);
                break;
            case R.id.pLocationcitiesSP:
                selectedProductLocationID = productLocationCityModels.get(position).getId();
                break;
        }
    }

    private void setUpSubCategroySP(int position) {
        subCategoryModels = categoryModels.get(position).getSubcatData();
        subCategoryAdapter = new SpinnerSubCategoryAdapter(this, subCategoryModels);
        subCategorySP.setAdapter(subCategoryAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private JSONObject createJsonForGettingStates(String countryId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.COUNTRY_ID, countryId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private JSONObject createJsonForGettingCities(String cityId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.STATE_ID, cityId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                if (data.getData() != null) {
                    Uri mImageUri = data.getData();
                    // Get the cursor
                    bitmapsList.add(PictureHelper.getInstance().retrieveAllSelectedPicturePath(this, mImageUri));
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        if (mClipData.getItemCount() > 5) {
                            Toast.makeText(this, "You can select max 5 images",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            bitmapsList.add(0,PictureHelper.getInstance().retrieveAllSelectedPicturePath(this, uri));
                        }
                    }
                }

                addImagesRvAdapter.addItem(bitmapsList);
                //addImageToScrollView();
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addImageToScrollView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for (int i = 0; i < bitmapsList.size(); i++) {
            View view = layoutInflater.inflate(R.layout.add_product_images_view, null);
            ImageView productIV = (ImageView) view.findViewById(R.id.productIV);
            Bitmap bitmap = bitmapsList.get(i);
            productIV.setId(i);
            productIV.setImageBitmap(bitmap);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.rightMargin = 10;
            addedImageLL.addView(productIV, layoutParams);
        }
    }

    @Override
    public void onPermissionGranted(int permissionType) {
        openGallery();
    }

    @Override
    public void onPermissionDenied(int permissionType) {

    }
}
