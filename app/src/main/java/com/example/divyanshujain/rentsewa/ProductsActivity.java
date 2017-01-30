package com.example.divyanshujain.rentsewa;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.CustomViews.CustomToasts;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.ProductsModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.example.divyanshujain.rentsewa.adapters.ProductsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProductsActivity extends BaseActivity {

    @InjectView(R.id.productsRV)
    RecyclerView productsRV;
    @InjectView(R.id.activity_products)
    LinearLayout activityProducts;

    ArrayList<ProductsModel> productsModels = new ArrayList<>();
    ProductsAdapter productsAdapter;
    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;

    private boolean fromFilters = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        CommonFunctions.getInstance().configureToolbarWithBackButton(this, toolbarView, getString(R.string.products));
        fromFilters = getIntent().getBooleanExtra(Constants.FROM_FILER, false);
        if (fromFilters)
            productsModels = getIntent().getParcelableArrayListExtra(Constants.DATA);
        else
            CallWebService.getInstance(this, true, ApiCodes.GET_PRODUCT_LISTING).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_PRODUCT_LISTING, createJsonForGettingProducts(), this);
        productsRV.setLayoutManager(new LinearLayoutManager(this));
        productsAdapter = new ProductsAdapter(this, productsModels, this);
        productsRV.setAdapter(productsAdapter);
    }

    private JSONObject createJsonForGettingProducts() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.SLUG, getIntent().getStringExtra(Constants.SLUG));
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);

        productsModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), ProductsModel.class);
        productsAdapter.addItem(productsModels);

    }

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
        CustomToasts.getInstance(this).showErrorToast(str);
    }

    @Override
    public void onClickItem(int position, View view) {
        super.onClickItem(position, view);
    }
}
