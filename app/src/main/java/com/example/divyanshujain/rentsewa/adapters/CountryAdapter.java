package com.example.divyanshujain.rentsewa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.example.divyanshujain.rentsewa.Models.CountryModel;
import com.example.divyanshujain.rentsewa.R;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;

/**
 * Created by divyanshuPC on 4/2/2017.
 */

public class CountryAdapter extends ArrayAdapter<CountryModel> {

    LayoutInflater inflater;
    ArrayList<CountryModel> countryModels;

    public CountryAdapter(Context context, int resource, ArrayList<CountryModel> objects) {
        super(context, resource, objects);
        this.countryModels = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        TextView row = (TextView) inflater.inflate(R.layout.custom_spinner_items, parent, false);
        row.setText(countryModels.get(position).getName());
        return row;
    }
}
