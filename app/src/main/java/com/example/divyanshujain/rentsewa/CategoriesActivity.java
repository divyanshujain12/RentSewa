package com.example.divyanshujain.rentsewa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.Constants.API;
import com.example.divyanshujain.rentsewa.Constants.ApiCodes;
import com.example.divyanshujain.rentsewa.Constants.Constants;
import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Models.CategoryModel;
import com.example.divyanshujain.rentsewa.Utils.CallWebService;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.example.divyanshujain.rentsewa.Utils.UniversalParser;
import com.example.divyanshujain.rentsewa.adapters.CategoriesAdapter;

import org.json.JSONException;
import org.json.JSONObject;

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
    ArrayList<CategoryModel> categoryModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        CommonFunctions.getInstance().configureToolbarWithOutBackButton(this, toolbarView, getString(R.string.categories));
        categoriesAdapter = new CategoriesAdapter(this, categoryModels, this);
        categoriesRV.setLayoutManager(new GridLayoutManager(this, 2));
        categoriesRV.setAdapter(categoriesAdapter);

        CallWebService.getInstance(this, true, ApiCodes.GET_CATEGORIES).hitJsonObjectRequestAPI(CallWebService.POST, API.GET_CATEGORIES, null, this);
    }


    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);

        categoryModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray(Constants.DATA), CategoryModel.class);
        categoriesAdapter.addItem(categoryModels);

    }

    @Override
    public void onClickItem(int position, View view) {
        super.onClickItem(position, view);

        Intent intent = new Intent(this, SubCategoriesActivity.class);
        intent.putExtra(Constants.NAME,categoryModels.get(position).getCat_name());
        intent.putExtra(Constants.DATA,categoryModels.get(position).getSubcatData());
        startActivity(intent);
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
