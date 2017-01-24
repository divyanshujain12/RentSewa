package com.example.divyanshujain.rentsewa;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.ProductsModel;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.adapters.ProductsAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        CommonFunctions.getInstance().configureToolbarWithBackButton(this, toolbarView, getString(R.string.products));
        productsModels = getIntent().getParcelableArrayListExtra(Constants.DATA);
        productsRV.setLayoutManager(new LinearLayoutManager(this));
        productsAdapter = new ProductsAdapter(this, productsModels, this);

        productsRV.setAdapter(productsAdapter);
    }

    @Override
    public void onClickItem(int position, View view) {
        super.onClickItem(position, view);
    }
}
