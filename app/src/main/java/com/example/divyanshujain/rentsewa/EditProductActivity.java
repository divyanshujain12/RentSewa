package com.example.divyanshujain.rentsewa;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.divyanshujain.rentsewa.CustomViews.CustomAlertDialogs;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Interfaces.SnackBarCallback;
import com.example.divyanshujain.rentsewa.Models.CategoryModel;
import com.example.divyanshujain.rentsewa.Models.CitiesModel;
import com.example.divyanshujain.rentsewa.Models.CountryModel;
import com.example.divyanshujain.rentsewa.Models.EditProductDetailModel;
import com.example.divyanshujain.rentsewa.Models.StateModel;
import com.example.divyanshujain.rentsewa.Models.SubCategoryModel;
import com.example.divyanshujain.rentsewa.Models.ValidationModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.MultipartUtility;
import com.example.divyanshujain.rentsewa.Utils.MySharedPereference;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class EditProductActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, RuntimePermissionHeadlessFragment.PermissionCallback {
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
    CityAdapter productLocationCityAdapter;
    CityAdapter cityAdapter;
    StateAdapter stateAdapter;
    CountryAdapter countryAdapter;
    @InjectView(R.id.scrollview)
    ScrollView scrollview;
    @InjectView(R.id.productImagesRV)
    RecyclerView productImagesRV;
    @InjectView(R.id.brandET)
    EditText brandET;
    @InjectView(R.id.addressET)
    EditText addressET;
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
    HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();
    ArrayList<Bitmap> bitmapsList = new ArrayList<>();
    private Validation validation;
    private AddImagesRvAdapter addImagesRvAdapter;
    ProgressDialog progressDialog;
    private HashMap<View, String> valuesHashMap = new HashMap<>();
    private String productID = "";
    EditProductDetailModel editProductDetailModel;
    private String selectedProductLocationID, selectedCityID, selectedCategoryID, selectedSubCategoryID, selectedStateID, selectedCountryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        initViews();
    }

    private void initViews() {

        mRequiredPermissions = new String[]{
                android.Manifest.permission.READ_EXTERNAL_STORAGE
        };
        runtimePermissionHeadlessFragment = CommonFunctions.getInstance().addRuntimePermissionFragment(this, this);

        scrollview.requestFocusFromTouch();

        validation = new Validation();
        validation.addValidationField(new ValidationModel(titleET, Validation.TYPE_EMPTY_FIELD_VALIDATION, "Title Cant Left Blank!"));
        validation.addValidationField(new ValidationModel(emailET, Validation.TYPE_EMAIL_VALIDATION, "Invalid Email"));
        validation.addValidationField(new ValidationModel(phoneET, Validation.TYPE_PHONE_VALIDATION, "Invalid Phone Number"));
        validation.addValidationField(new ValidationModel(priceET, Validation.TYPE_EMPTY_FIELD_VALIDATION, "Invalid Price"));
        validation.addValidationField(new ValidationModel(pinCodeET, Validation.TYPE_EMPTY_FIELD_VALIDATION, "Invalid Pin Code"));
        validation.addValidationField(new ValidationModel(timeET, Validation.TYPE_EMPTY_FIELD_VALIDATION, "Invalid Time!"));
        validation.addValidationField(new ValidationModel(descET, Validation.TYPE_EMPTY_FIELD_VALIDATION, "Description Cant Left Blank!"));
        validation.addValidationField(new ValidationModel(brandET, Validation.TYPE_EMPTY_FIELD_VALIDATION, "Invalid Brand!"));
        validation.addValidationField(new ValidationModel(addressET, Validation.TYPE_EMPTY_FIELD_VALIDATION, "Invalid Address!"));

        pLocationcitiesSP.setOnItemSelectedListener(this);
        citiesSP.setOnItemSelectedListener(this);
        stateSP.setOnItemSelectedListener(this);
        countrySP.setOnItemSelectedListener(this);
        categorySP.setOnItemSelectedListener(this);
        subCategorySP.setOnItemSelectedListener(this);
        initializeBitmapArray();
        productImagesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        addImagesRvAdapter = new AddImagesRvAdapter(this, bitmapsList, this);
        productImagesRV.setAdapter(addImagesRvAdapter);

        CallWebService.getInstance(this, false, ApiCodes.GET_PRODUCT_DETAIL_BY_PRODUCT_ID).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_PRODUCT_DETAIL_BY_PRODUCT_ID, createJsonForGettingProduct(), this);

    }


    @OnClick({R.id.addImageIV, R.id.postBT})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addImageIV:


                break;
            case R.id.postBT:
                valuesHashMap = validation.validate(this);
                if (valuesHashMap != null && bitmapsList.size() > 0) {
                    for (String imagePath : bitmapHashMap.keySet()) {
                        new UploadFileToServer(this, imagePath, API.POST_PRODUCT, false).execute();
                    }
                }
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
            case ApiCodes.GET_PRODUCT_DETAIL_BY_PRODUCT_ID:
                editProductDetailModel = UniversalParser.getInstance().parseJsonObject(response, EditProductDetailModel.class);
                updateUI();
                CallWebService.getInstance(this, false, ApiCodes.GET_PRODUCT_LOCATION).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_PRODUCT_LOCATION, null, this);
                CallWebService.getInstance(this, false, ApiCodes.GET_ALL_COUNTRY).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_ALL_COUNTRY, null, this);
                CallWebService.getInstance(this, false, ApiCodes.GET_CATEGORIES).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_CATEGORIES, null, this);
                break;
            case ApiCodes.GET_PRODUCT_LOCATION:
                productLocationCityModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), CitiesModel.class);
                setUpProductLocationSP();
                break;
            case ApiCodes.GET_ALL_COUNTRY:
                countryModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), CountryModel.class);
                setUpCountrySP();
                break;
            case ApiCodes.GET_ALL_STATE:
                stateModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), StateModel.class);
                setUpStateSP();
                break;
            case ApiCodes.GET_ALL_CITIES:
                cityModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), CitiesModel.class);
                setUpCitySP();
                break;
            case ApiCodes.GET_CATEGORIES:
                categoryModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), CategoryModel.class);
                setUpCategorySP();
                break;
            case ApiCodes.POST_PRODUCT:
                break;
            case ApiCodes.PRODUCT_EDIT_PROCESS:
                break;
        }
    }

    private void setUpProductLocationSP() {
        productLocationCityAdapter = new CityAdapter(this, productLocationCityModels);
        pLocationcitiesSP.setAdapter(productLocationCityAdapter);
        setProductLocationSelected();
    }

    private void setUpCountrySP() {
        countryAdapter = new CountryAdapter(this, 0, countryModels);
        countrySP.setAdapter(countryAdapter);
        setCountrySelected();
    }

    private void setUpSubCategorySP(int position) {
        subCategoryModels = categoryModels.get(position).getSubcatData();
        subCategoryAdapter = new SpinnerSubCategoryAdapter(this, subCategoryModels);
        subCategorySP.setAdapter(subCategoryAdapter);
        setSubCategorySelected();
    }

    private void setUpStateSP() {
        stateAdapter = new StateAdapter(this, 0, stateModels);
        stateSP.setAdapter(stateAdapter);
        setStateSelected();
    }

    private void setUpCitySP() {
        cityAdapter = new CityAdapter(this, cityModels);
        citiesSP.setAdapter(cityAdapter);
        setCitySelected();
    }

    private void setUpCategorySP() {
        categoryAdapter = new SpinnerCategoryAdapter(this, categoryModels);
        categorySP.setAdapter(categoryAdapter);
        setCategorySelected();
    }

    private void setProductLocationSelected() {
        CitiesModel citiesModel = new CitiesModel();
        selectedProductLocationID = editProductDetailModel.getPlocation();
        citiesModel.setId(selectedProductLocationID);
        int selectedPLocation = productLocationCityModels.indexOf(citiesModel);
        pLocationcitiesSP.setSelection(selectedPLocation);
    }

    private void setCategorySelected() {
        CategoryModel categoryModel = new CategoryModel();
        selectedCategoryID = editProductDetailModel.getPcategory();
        categoryModel.setId(selectedCategoryID);
        int selectedPCategory = categoryModels.indexOf(categoryModel);
        categorySP.setSelection(selectedPCategory);
    }

    private void setSubCategorySelected() {
        SubCategoryModel subCategoryModel = new SubCategoryModel();
        selectedSubCategoryID = editProductDetailModel.getPsubcategory();
        subCategoryModel.setId(selectedSubCategoryID);
        int selectedPSubCategory = subCategoryModels.indexOf(subCategoryModel);
        subCategorySP.setSelection(selectedPSubCategory);
    }

    private void setCountrySelected() {
        CountryModel countryModel = new CountryModel();
        selectedCountryID = editProductDetailModel.getPcountry();
        countryModel.setCountry_id(selectedCountryID);
        int selectedPCountry = countryModels.indexOf(countryModel);
        countrySP.setSelection(selectedPCountry);
    }

    private void setStateSelected() {
        StateModel stateModel = new StateModel();
        selectedStateID = editProductDetailModel.getPstate();
        stateModel.setState_id(selectedStateID);
        int selectedState = stateModels.indexOf(stateModel);
        stateSP.setSelection(selectedState);
    }

    private void setCitySelected() {
        CitiesModel citiesModel = new CitiesModel();
        selectedCityID = editProductDetailModel.getPcity();
        citiesModel.setId(selectedCityID);
        int selectedPCity = productLocationCityModels.indexOf(citiesModel);
        citiesSP.setSelection(selectedPCity);
    }

    private void updateUI() {
        titleET.setText(editProductDetailModel.getPtitle());
        emailET.setText(editProductDetailModel.getPemail());
        phoneET.setText(editProductDetailModel.getPphone());
        priceET.setText(editProductDetailModel.getPprice());
        pinCodeET.setText(editProductDetailModel.getPzip());
        timeET.setText(editProductDetailModel.getPtime_period());
        descET.setText(editProductDetailModel.getDescription());
        brandET.setText(editProductDetailModel.getPbrand());
        addressET.setText(editProductDetailModel.getPaddress());
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
                setUpSubCategorySP(position);

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

    private JSONObject createJsonForGettingProduct() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.P_ID, getIntent().getStringExtra(Constants.P_ID));
        } catch (Exception e) {
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
                initializeBitmapArray();
                if (data.getData() != null) {
                    bitmapHashMap = PictureHelper.getInstance().retrievePicturePath(this, requestCode, resultCode, data);

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
                            bitmapsList.add(0, PictureHelper.getInstance().retrieveAllSelectedPicturePath(this, uri));
                        }
                    }
                }
                for (String imagePath : bitmapHashMap.keySet()) {
                    Bitmap bitmap = bitmapHashMap.get(imagePath);
                    bitmapsList.add(0, bitmap);
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

    public class UploadFileToServer extends AsyncTask<String, String, String> {
        long totalSize = 0;
        String filePath;
        File sourceFile;
        String charset = "UTF-8";
        Context context;
        boolean onlyImages;
        String webUrl;

        public UploadFileToServer(Context context, String filePath, String webUrl, boolean onlyImages) {
            this.filePath = filePath;
            this.onlyImages = onlyImages;
            this.context = context;
            this.webUrl = webUrl;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Uploading Data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            sourceFile = new File(filePath);
            totalSize = (int) sourceFile.length();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            Log.d("PROG", progress[0]);
        }

        @Override
        protected String doInBackground(String... args) {
            String responseString = "";
            try {
                MultipartUtility multipart = new MultipartUtility(webUrl, charset);

                multipart.addHeaderField("User-Agent", "CodeJava");
                multipart.addHeaderField("Test-Header", "Header-Value");
                if (!onlyImages) {
                    setParams(context, filePath, multipart);
                } else {
                    setParamsForOnlyImages(filePath, multipart, productID);
                }
                multipart.addFilePart(Constants.P_IMAGE, new File(filePath));

                List<String> response = multipart.finish();

                System.out.println("SERVER REPLIED:");

                for (String line : response) {
                    responseString = line;
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Response", "Response from server: " + result);
            super.onPostExecute(result);
            if (progressDialog != null)
                progressDialog.cancel();
            try {

                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean(Constants.STATUS_CODE)) {
                    CustomAlertDialogs.showAlertDialog(EditProductActivity.this, getString(R.string.congratulation), jsonObject.getString(Constants.MESSAGE), new SnackBarCallback() {
                        @Override
                        public void doAction() {
                            // finish();
                        }
                    });
                } else {
                    CustomAlertDialogs.showAlertDialog(EditProductActivity.this, getString(R.string.alert), jsonObject.getString(Constants.MESSAGE), new SnackBarCallback() {
                        @Override
                        public void doAction() {

                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //reset();

        }

        private void setParams(Context context, String filename, MultipartUtility multipartUtility) {
            multipartUtility.addFormField(Constants.USER_ID, MySharedPereference.getInstance().getString(context, Constants.USER_ID));
            multipartUtility.addFormField(Constants.P_TITLE, valuesHashMap.get(titleET));
            multipartUtility.addFormField(Constants.P_BRAND, valuesHashMap.get(brandET));
            multipartUtility.addFormField(Constants.P_CATEGORY, selectedCategoryID);
            multipartUtility.addFormField(Constants.P_SUB_CATEGORY, selectedSubCategoryID);
            multipartUtility.addFormField(Constants.P_PRICE, valuesHashMap.get(priceET));
            multipartUtility.addFormField(Constants.P_TIME_PERIOD, valuesHashMap.get(timeET));
            multipartUtility.addFormField(Constants.P_DESC, valuesHashMap.get(descET));
            multipartUtility.addFormField(Constants.P_NAME, valuesHashMap.get(titleET));
            multipartUtility.addFormField(Constants.P_IMAGE, filename);
            multipartUtility.addFormField(Constants.P_LOCATION, selectedProductLocationID);
            multipartUtility.addFormField(Constants.P_EMAIL, valuesHashMap.get(emailET));
            multipartUtility.addFormField(Constants.P_PHONE, valuesHashMap.get(phoneET));
            multipartUtility.addFormField(Constants.P_ADDRESS, valuesHashMap.get(addressET));
            multipartUtility.addFormField(Constants.P_COUNTRY, selectedCountryID);
            multipartUtility.addFormField(Constants.P_STATE, selectedStateID);
            multipartUtility.addFormField(Constants.P_CITY, selectedCityID);
            multipartUtility.addFormField(Constants.P_ZIP, valuesHashMap.get(pinCodeET));
        }

        private void setParamsForOnlyImages(String filename, MultipartUtility multipartUtility, String productID) {
            multipartUtility.addFormField(Constants.P_ID, productID);
            multipartUtility.addFormField(Constants.P_IMAGES_MULTIPLE, filename);

        }
    }

    private void initializeBitmapArray() {
        bitmapsList = new ArrayList<>();
        bitmapsList.add(null);
    }
}

