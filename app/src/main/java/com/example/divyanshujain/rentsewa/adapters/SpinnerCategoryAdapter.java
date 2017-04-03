package com.example.divyanshujain.rentsewa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.divyanshujain.rentsewa.Models.CategoryModel;
import com.example.divyanshujain.rentsewa.R;

import java.util.ArrayList;

/**
 * Created by divyanshu.jain on 1/24/2017.
 */

public class SpinnerCategoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<CategoryModel> citiesModels;
    LayoutInflater inflter;

    public SpinnerCategoryAdapter(Context applicationContext, ArrayList<CategoryModel> categoryModels) {
        this.context = applicationContext;
        this.citiesModels = categoryModels;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return citiesModels.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) view;
        names.setText(citiesModels.get(i).getCat_name());
        return view;
    }
}
