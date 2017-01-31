package com.example.divyanshujain.rentsewa;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.CustomViews.CustomToasts;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.VendorListingModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.MySharedPereference;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.example.divyanshujain.rentsewa.adapters.VendorListingAdapter;
import com.example.divyanshujain.rentsewa.fragments.RuntimePermissionHeadlessFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VendorListingActivity extends BaseActivity implements RuntimePermissionHeadlessFragment.PermissionCallback {

    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;
    @InjectView(R.id.vendorRV)
    RecyclerView vendorRV;
    @InjectView(R.id.activity_vendor_listing)
    LinearLayout activityVendorListing;

    ArrayList<VendorListingModel> vendorListingModels = new ArrayList<>();
    private VendorListingAdapter vendorListingAdapter;
    String[] callPermission;
    RuntimePermissionHeadlessFragment runtimePermissionHeadlessFragment;
    private static final int CALL_REQUEST = 101;
    private String phoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_listing);
        ButterKnife.inject(this);
        InitViews();
    }

    private void InitViews() {

        callPermission = new String[]{
                Manifest.permission.CALL_PHONE
        };
        runtimePermissionHeadlessFragment = CommonFunctions.getInstance().addRuntimePermissionFragment(this, this);

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

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
        CustomToasts.getInstance(this).showErrorToast(str);
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
    public void onClickItem(int position, View view) {
        super.onClickItem(position, view);
        switch (view.getId()) {

            case R.id.callIV:
                phoneNumber = vendorListingModels.get(position).getVisitor_phone();
                runtimePermissionHeadlessFragment.addAndCheckPermission(callPermission, CALL_REQUEST);
                break;
            default:
                Intent intent1 = new Intent(this, VendorListingDetailActivity.class);
                intent1.putExtra(Constants.DATA, vendorListingModels.get(position));
                startActivity(intent1);
                break;
        }
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

    @Override
    public void onPermissionGranted(int permissionType) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Override
    public void onPermissionDenied(int permissionType) {

    }
}
