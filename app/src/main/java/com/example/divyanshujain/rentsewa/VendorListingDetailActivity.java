package com.example.divyanshujain.rentsewa;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.Models.ImageModel;
import com.example.divyanshujain.rentsewa.Models.VendorListingModel;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.ImageLoading;
import com.example.divyanshujain.rentsewa.adapters.CustomPagerAdapter;
import com.example.divyanshujain.rentsewa.fragments.RuntimePermissionHeadlessFragment;
import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VendorListingDetailActivity extends AppCompatActivity implements RuntimePermissionHeadlessFragment.PermissionCallback {

    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;

    @InjectView(R.id.priceTV)
    TextView priceTV;
    @InjectView(R.id.visitorNameTV)
    TextView visitorNameTV;
    @InjectView(R.id.visitorNumberTV)
    TextView visitorNumberTV;
    @InjectView(R.id.visitorMailTV)
    TextView visitorMailTV;
    @InjectView(R.id.categoryTV)
    TextView categoryTV;
    @InjectView(R.id.subCategoryTV)
    TextView subCategoryTV;
    @InjectView(R.id.enquiredTV)
    TextView enquiredTV;
    @InjectView(R.id.viewsTV)
    TextView viewsTV;
    @InjectView(R.id.timePeriodTV)
    TextView timePeriodTV;
    @InjectView(R.id.activity_product_description)
    LinearLayout activityProductDescription;
    @InjectView(R.id.clickToContactBT)
    Button clickToContactBT;

    ImageLoading imageLoading;
    @InjectView(R.id.pager)
    ViewPager pager;
    private VendorListingModel vendorListingModel;
    String[] callPermission;
    RuntimePermissionHeadlessFragment runtimePermissionHeadlessFragment;
    private static final int CALL_REQUEST = 101;
    private CustomPagerAdapter customPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_listing_detail);
        ButterKnife.inject(this);

        initViews();
    }

    private void initViews() {
        imageLoading = new ImageLoading(this);
        callPermission = new String[]{
                Manifest.permission.CALL_PHONE
        };
        runtimePermissionHeadlessFragment = CommonFunctions.getInstance().addRuntimePermissionFragment(this, this);
        vendorListingModel = getIntent().getParcelableExtra(Constants.DATA);
        CommonFunctions.getInstance().configureToolbarWithBackButton(this, toolbarView, vendorListingModel.getTitle());
        setUpViews();
    }

    private void setUpViews() {

        priceTV.setText(vendorListingModel.getPrice());
        visitorNameTV.setText(vendorListingModel.getVisitor_name());
        visitorNumberTV.setText(vendorListingModel.getVisitor_phone());
        visitorMailTV.setText(vendorListingModel.getVisitor_email());
        categoryTV.setText(vendorListingModel.getCat_name());
        subCategoryTV.setText(vendorListingModel.getSubcat_name());
        enquiredTV.setText(vendorListingModel.getCreated());
        viewsTV.setText(vendorListingModel.getViews());
        timePeriodTV.setText(vendorListingModel.getTime_period());
        ArrayList<ImageModel> imageModels = vendorListingModel.getImagesArray();
        if (imageModels == null)
            imageModels = new ArrayList<>();
        imageModels.add(0, new ImageModel(vendorListingModel.getImage1()));
        customPagerAdapter = new CustomPagerAdapter(this, imageModels);
        pager.setAdapter(customPagerAdapter);
    }

    @OnClick(R.id.clickToContactBT)
    public void onClick() {
        runtimePermissionHeadlessFragment.addAndCheckPermission(callPermission, CALL_REQUEST);
    }

    @Override
    public void onPermissionGranted(int permissionType) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + vendorListingModel.getVisitor_phone()));
        startActivity(intent);
    }

    @Override
    public void onPermissionDenied(int permissionType) {

    }
}
