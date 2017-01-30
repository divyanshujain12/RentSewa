package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.VendorListingModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.MySharedPereference;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.example.divyanshujain.rentsewa.adapters.VendorListingAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VendorListingActivity extends BaseActivity {

    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;
    @InjectView(R.id.vendorRV)
    RecyclerView vendorRV;
    @InjectView(R.id.activity_vendor_listing)
    LinearLayout activityVendorListing;

    ArrayList<VendorListingModel> vendorListingModels = new ArrayList<>();
    private VendorListingAdapter vendorListingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_listing);
        ButterKnife.inject(this);

        InitViews();
    }

    private void InitViews() {
        CommonFunctions.getInstance().configureToolbarWithOutBackButton(this, toolbarView, MySharedPereference.getInstance().getString(this, Constants.NAME));
        CallWebService.getInstance(this, true, ApiCodes.GET_PRODUCT_REQUESTS).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_PRODUCT_REQUESTS, createJsonForGetRequests(), this);
        vendorListingAdapter = new VendorListingAdapter(this, vendorListingModels, this);
        vendorRV.setLayoutManager(new LinearLayoutManager(this));
        vendorRV.setAdapter(vendorListingAdapter);
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        vendorListingModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), VendorListingModel.class);
        vendorListingAdapter.addItem(vendorListingModels);
    }

    private JSONObject createJsonForGetRequests() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.USER_ID, MySharedPereference.getInstance().getString(this, Constants.USER_ID));
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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
