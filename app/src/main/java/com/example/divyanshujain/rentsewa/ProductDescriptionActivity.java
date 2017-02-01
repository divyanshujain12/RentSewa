package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.CustomViews.CustomAlertDialogs;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.ImageModel;
import com.example.divyanshujain.rentsewa.Models.ProductDetailModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.ImageLoading;
import com.example.divyanshujain.rentsewa.Utils.MySharedPereference;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.example.divyanshujain.rentsewa.adapters.CustomPagerAdapter;
import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.textview.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ProductDescriptionActivity extends BaseActivity {


    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;

    @InjectView(R.id.brandTV)
    TextView brandTV;
    @InjectView(R.id.priceTV)
    TextView priceTV;
    @InjectView(R.id.categoryTV)
    TextView categoryTV;
    @InjectView(R.id.subCategoryTV)
    TextView subCategoryTV;
    @InjectView(R.id.addedTV)
    TextView addedTV;
    @InjectView(R.id.viewsTV)
    TextView viewsTV;
    @InjectView(R.id.cityTV)
    TextView cityTV;
    @InjectView(R.id.vendorNameTV)
    TextView vendorNameTV;
    @InjectView(R.id.timePeriodTV)
    TextView timePeriodTV;
    @InjectView(R.id.descriptionTV)
    TextView descriptionTV;
    @InjectView(R.id.activity_product_description)
    LinearLayout activityProductDescription;
    @InjectView(R.id.clickToContactBT)
    Button clickToContactBT;
    @InjectView(R.id.pager)
    ViewPager pager;

    private String productName, productID;
    private ImageLoading imageLoading;
    private CustomPagerAdapter customPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        ButterKnife.inject(this);

        initViews();
    }

    private void initViews() {
        productName = getIntent().getStringExtra(Constants.PRODUCT_NAME);
        productID = getIntent().getStringExtra(Constants.PRODUCT_ID);
        CommonFunctions.getInstance().configureToolbarWithBackButton(this, toolbarView, productName);
        imageLoading = new ImageLoading(this);
        CallWebService.getInstance(this, true, ApiCodes.PRODUCT_DETAIL).hitJsonObjectRequestAPI(CallWebService.POST, API.PRODUCT_DETAIL, createJsonForGetProductDetail(), this);

    }


    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        switch (apiType) {
            case ApiCodes.PRODUCT_DETAIL:
                ProductDetailModel productDetailModel = UniversalParser.getInstance().parseJsonObject(response.getJSONObject(Constants.DATA), ProductDetailModel.class);
                updateUI(productDetailModel);
                break;
            case ApiCodes.REQUEST_FOR_A_CALL:
                CustomAlertDialogs.showAlertDialog(this, "Congratulation", response.getString(Constants.MESSAGE), this);
                break;
        }

    }

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
        switch (apiType) {

            case ApiCodes.REQUEST_FOR_A_CALL:
                CustomAlertDialogs.showAlertDialog(this, "Congratulation", str, this);
                break;
        }
    }

    private void updateUI(ProductDetailModel productDetailModel) {

        brandTV.setText(productDetailModel.getBrand_name());
        priceTV.setText(productDetailModel.getPrice());
        categoryTV.setText(productDetailModel.getCat_name());
        subCategoryTV.setText(productDetailModel.getSubcat_name());
        addedTV.setText(productDetailModel.getCreated());
        viewsTV.setText(productDetailModel.getViews());
        cityTV.setText(productDetailModel.getCity());
        vendorNameTV.setText(productDetailModel.getVendor_name());
        timePeriodTV.setText(productDetailModel.getTime_period());
        descriptionTV.setText(productDetailModel.getDescription());
        ArrayList<ImageModel> imageModels = productDetailModel.getImagesArray();
        if (imageModels == null)
            imageModels = new ArrayList<>();
        imageModels.add(0, new ImageModel(productDetailModel.getImage1()));
        customPagerAdapter = new CustomPagerAdapter(this, imageModels);
        pager.setAdapter(customPagerAdapter);
    }

    @OnClick(R.id.clickToContactBT)
    public void onClick() {
        CallWebService.getInstance(this, true, ApiCodes.REQUEST_FOR_A_CALL).hitJsonObjectRequestAPI(CallWebService.POST, API.REQUEST_FOR_A_CALL, createJsonForRequestCall(), this);

    }

    private JSONObject createJsonForRequestCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.P_ID, productID);
            jsonObject.put(Constants.USER_ID, MySharedPereference.getInstance().getString(this, Constants.USER_ID));
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject createJsonForGetProductDetail() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.ID, productID);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void doAction() {
        super.doAction();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_icon_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_user:
                intent = new Intent(this, UserSettingActivity.class);
                break;
        }
        if (intent != null)
            startActivity(intent);
        return true;
    }
}
