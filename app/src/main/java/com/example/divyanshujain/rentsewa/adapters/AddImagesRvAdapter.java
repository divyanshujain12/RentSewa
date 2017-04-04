package com.example.divyanshujain.rentsewa.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.divyanshujain.rentsewa.Interfaces.RecyclerViewClick;
import com.example.divyanshujain.rentsewa.R;
import com.example.divyanshujain.rentsewa.Utils.ImageLoading;

import java.util.ArrayList;

/**
 * Created by divyanshu.jain on 4/4/2017.
 */

public class AddImagesRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Bitmap> bitmaps;
    private RecyclerViewClick recyclerViewClick;
    private ImageLoading imageLoading;

    public AddImagesRvAdapter(Context context, ArrayList<Bitmap> bitmaps, RecyclerViewClick recyclerViewClick) {
        this.recyclerViewClick = recyclerViewClick;
        this.bitmaps = bitmaps;
        this.context = context;
        imageLoading = new ImageLoading(context, 5);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case 0:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.add_image_button_view, parent, false);
                return new MyAddImageViewHolder(itemView);

            case 1:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.add_product_images_view, parent, false);
                break;
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        int type = getItemViewType(position);
        switch (type) {
            case 0:
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recyclerViewClick.onClickItem(position, view);
                    }
                });
                break;
            case 1:
                MyViewHolder myViewHolder = ((MyViewHolder) holder);
                myViewHolder.productIV.setImageBitmap(bitmaps.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productIV;
        private MyViewHolder(View itemView) {
            super(itemView);
            productIV = (ImageView) itemView.findViewById(R.id.productIV);
        }
    }

    protected class MyAddImageViewHolder extends RecyclerView.ViewHolder {
        private MyAddImageViewHolder(View itemView) {
            super(itemView);
            // productIV = (ImageView) itemView.findViewById(R.id.productIV);
        }
    }

    public void addItem(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == bitmaps.size() - 1) {
            return 0;
        } else
            return 1;
    }
}


