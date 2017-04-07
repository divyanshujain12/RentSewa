package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.CustomViews.CustomAlertDialogs;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Interfaces.SnackBarCallback;
import com.example.divyanshujain.rentsewa.Models.ProductsModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.MySharedPereference;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.example.divyanshujain.rentsewa.adapters.VendorProductRvAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VendorAllProductListing extends BaseActivity {

    @InjectView(R.id.productListingRV)
    RecyclerView productListingRV;
    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;
    @InjectView(R.id.addProductFAB)
    FloatingActionButton addProductFAB;

    private VendorProductRvAdapter vendorProductRvAdapter;
    private ArrayList<ProductsModel> productsModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_all_product_listing);
        ButterKnife.inject(this);

        initViews();
    }

    private void initViews() {
        CommonFunctions.getInstance().configureToolbarWithBackButton(this, toolbarView, getString(R.string.all_products));
        productListingRV.setLayoutManager(new LinearLayoutManager(this));
        CallWebService.getInstance(this, true, ApiCodes.VENDOR_PRODUCT_LISTING).hitJsonObjectRequestAPI(CallWebService.POST, API.VENDOR_PRODUCT_LISTING, createJsonForGetProducts(), this);
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        switch (apiType) {
            case ApiCodes.VENDOR_PRODUCT_LISTING:
                productsModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), ProductsModel.class);
                vendorProductRvAdapter = new VendorProductRvAdapter(this, productsModels, this);
                productListingRV.setAdapter(vendorProductRvAdapter);
                break;
            case ApiCodes.DELETE_PRODUCT:

                break;
        }
    }

    @Override
    public void onClickItem(final int position, View view) {
        super.onClickItem(position, view);
        switch (view.getId()) {
            case R.id.editIV:

                /*Intent intent = new Intent(this, VendorAddProduct.class);
                intent.putExtra(Constants.DATA, productsModels.get(position));
                startActivity(intent);*/
                break;
            case R.id.deleteIV:
                CustomAlertDialogs.showAlertDialogWithCallBack(this, "Alert", "Are you sure want to delete this product?", new SnackBarCallback() {
                    @Override
                    public void doAction() {
                        CallWebService.getInstance(VendorAllProductListing.this, true, ApiCodes.DELETE_PRODUCT).hitJsonObjectRequestAPI(CallWebService.POST, API.DELETE_PRODUCT, createJsonForDeleteProduct(productsModels.get(position).getId()), VendorAllProductListing.this);
                        vendorProductRvAdapter.deleteProduct(position);
                    }
                });
                break;
            default:
                Intent intent1 = new Intent(this, ProductDescriptionActivity.class);
                intent1.putExtra(Constants.PRODUCT_NAME, productsModels.get(position).getTitle());
                intent1.putExtra(Constants.PRODUCT_ID, productsModels.get(position).getId());
                startActivity(intent1);
                break;
        }
    }

    private JSONObject createJsonForDeleteProduct(String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.P_ID, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private JSONObject createJsonForGetProducts() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.USER_ID, MySharedPereference.getInstance().getString(this, Constants.USER_ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @OnClick(R.id.addProductFAB)
    public void onViewClicked() {
        Intent intent = new Intent(this, VendorAddProduct.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_product_listing_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_user:
                intent = new Intent(this, UserSettingActivity.class);
                break;
            case R.id.action_requests:
                intent = new Intent(this, VendorListingActivity.class);
                break;
        }
        if (intent != null)
            startActivity(intent);
        return true;
    }

}
