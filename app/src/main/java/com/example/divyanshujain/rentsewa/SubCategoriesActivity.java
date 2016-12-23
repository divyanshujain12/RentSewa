package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.adapters.SubCategoriesAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SubCategoriesActivity extends BaseActivity {

    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;
    @InjectView(R.id.subCategoriesRV)
    RecyclerView subCategoriesRV;
    @InjectView(R.id.activity_categories)
    LinearLayout activityCategories;

    private SubCategoriesAdapter subCategoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        CommonFunctions.getInstance().configureToolbarWithBackButton(this, toolbarView, getString(R.string.category_name));
        subCategoriesAdapter = new SubCategoriesAdapter(this, new ArrayList<String>(), this);
        subCategoriesRV.setLayoutManager(new LinearLayoutManager(this));
        subCategoriesRV.setAdapter(subCategoriesAdapter);
    }

    @Override
    public void onClickItem(int position, View view) {
        super.onClickItem(position, view);

        Intent intent = new Intent(this, ProductDescriptionActivity.class);
        startActivity(intent);
    }
}
