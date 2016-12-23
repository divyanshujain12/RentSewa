package com.example.divyanshujain.rentsewa;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.divyanshujain.rentsewa.GlobalClasses.BaseActivity;
import com.example.divyanshujain.rentsewa.Utils.CommonFunctions;
import com.neopixl.pixlui.components.textview.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProductDescriptionActivity extends BaseActivity {

    @InjectView(R.id.toolbarView)
    Toolbar toolbarView;
    @InjectView(R.id.productIV)
    ImageView productIV;
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
    @InjectView(R.id.vendorRatingTV)
    TextView vendorRatingTV;
    @InjectView(R.id.descriptionTV)
    TextView descriptionTV;
    @InjectView(R.id.activity_product_description)
    LinearLayout activityProductDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        ButterKnife.inject(this);

        initViews();
    }

    private void initViews() {
        CommonFunctions.getInstance().configureToolbarWithBackButton(this,toolbarView,getString(R.string.product_name));
    }
}
