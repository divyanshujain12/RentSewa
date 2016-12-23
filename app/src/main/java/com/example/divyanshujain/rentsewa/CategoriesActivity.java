package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.adapters.CategoriesAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CategoriesActivity extends BaseActivity {

    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;
    @InjectView(R.id.categoriesRV)
    RecyclerView categoriesRV;
    @InjectView(R.id.activity_categories)
    LinearLayout activityCategories;

    private CategoriesAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        CommonFunctions.getInstance().configureToolbarWithOutBackButton(this, toolbarView, getString(R.string.categories));
        categoriesAdapter = new CategoriesAdapter(this, new ArrayList<String>(), this);
        categoriesRV.setLayoutManager(new GridLayoutManager(this, 2));
        categoriesRV.setAdapter(categoriesAdapter);
    }

    @Override
    public void onClickItem(int position, View view) {
        super.onClickItem(position, view);

        startActivity(new Intent(this, SubCategoriesActivity.class));
    }
}
