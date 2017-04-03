package com.example.divyanshujain.rentsewa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.example.divyanshujain.rentsewa.Models.StateModel;
import com.example.divyanshujain.rentsewa.R;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;

/**
 * Created by divyanshuPC on 4/2/2017.
 */

public class StateAdapter extends ArrayAdapter<StateModel> {

    LayoutInflater inflater;
    ArrayList<StateModel> stateModels;

    public StateAdapter(Context context, int resource, ArrayList<StateModel> objects) {
        super(context, resource, objects);
        this.stateModels = objects;
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
        TextView row = (TextView) inflater.inflate(R.layout.spinner_rows, parent, false);
        row.setText(stateModels.get(position).getName());
        return row;
    }
}

