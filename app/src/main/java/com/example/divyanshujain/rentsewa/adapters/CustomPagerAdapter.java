package com.example.divyanshujain.rentsewa.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.divyanshujain.rentsewa.Models.ImageModel;
import com.example.divyanshujain.rentsewa.R;
import com.example.divyanshujain.rentsewa.Utils.ImageLoading;


import java.util.ArrayList;

/**
 * Created by Lenovo on 05-11-2015.
 */
public class CustomPagerAdapter extends PagerAdapter implements View.OnClickListener {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private ArrayList<ImageModel> imageModels;
    private ImageLoading loader;
    ImageView imageView;

    public CustomPagerAdapter(Context context, ArrayList<ImageModel> bannerModels) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageModels = bannerModels;
        loader = new ImageLoading(mContext);
    }

    @Override
    public int getCount() {
        return imageModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        loader.LoadImage(imageModels.get(position).getImage(), imageView, null);
        container.addView(itemView);
        imageView.setId(position);
        imageView.setOnClickListener(this);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public void onClick(View view) {

    }
}

