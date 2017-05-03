package com.example.divyanshujain.rentsewa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.divyanshujain.rentsewa.Interfaces.RecyclerViewClick;
import com.example.divyanshujain.rentsewa.Models.ImageModel;
import com.example.divyanshujain.rentsewa.R;
import com.example.divyanshujain.rentsewa.Utils.ImageLoading;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by divyanshu.jain on 5/3/2017.
 */

public class EditProductAddImageRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ImageModel> imageModels;
    private RecyclerViewClick recyclerViewClick;
    private ImageLoading imageLoading;

    public EditProductAddImageRVAdapter(Context context, ArrayList<ImageModel> imageModels, RecyclerViewClick recyclerViewClick) {
        this.recyclerViewClick = recyclerViewClick;
        this.imageModels = imageModels;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        int type = getItemViewType(position);
        switch (type) {
            case 0:
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recyclerViewClick.onClickItem(holder.getAdapterPosition(), view);
                    }
                });
                break;
            case 1:
                MyViewHolder myViewHolder = ((MyViewHolder) holder);
                if (imageModels.get(position).getImage().startsWith("http"))
                    Picasso.with(context).load(imageModels.get(position).getImage()).into(myViewHolder.productIV);
                    //imageLoading.LoadImage(imageModels.get(position).getImage(), myViewHolder.productIV, null);
                else
                    Picasso.with(context).load(new File(imageModels.get(position).getImage())).into(myViewHolder.productIV);
                myViewHolder.editIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recyclerViewClick.onClickItem(holder.getAdapterPosition(), view);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productIV, editIV;

        private MyViewHolder(View itemView) {
            super(itemView);
            productIV = (ImageView) itemView.findViewById(R.id.productIV);
            editIV = (ImageView) itemView.findViewById(R.id.editIV);
        }
    }

    protected class MyAddImageViewHolder extends RecyclerView.ViewHolder {
        private MyAddImageViewHolder(View itemView) {
            super(itemView);
            // productIV = (ImageView) itemView.findViewById(R.id.productIV);
        }
    }

    public void addItem(ArrayList<ImageModel> bitmaps) {
        this.imageModels = bitmaps;
        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        this.imageModels.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == imageModels.size() - 1) {
            return 0;
        } else
            return 1;
    }
}


