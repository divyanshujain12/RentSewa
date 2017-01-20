package com.example.divyanshujain.rentsewa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.divyanshujain.rentsewa.Interfaces.RecyclerViewClick;
import com.example.divyanshujain.rentsewa.Models.CategoryModel;
import com.example.divyanshujain.rentsewa.R;
import com.example.divyanshujain.rentsewa.Utils.ImageLoading;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;

/**
 * Created by divyanshu.jain on 12/23/2016.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CategoryModel> categoryModels;
    private RecyclerViewClick recyclerViewClick;
    private ImageLoading imageLoading;

    public CategoriesAdapter(Context context, ArrayList<CategoryModel> arrayList, RecyclerViewClick recyclerViewClick) {
        this.recyclerViewClick = recyclerViewClick;
        this.categoryModels = arrayList;
        this.context = context;
        imageLoading = new ImageLoading(context, 5);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categories_adapter_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        CategoryModel categoryModel = categoryModels.get(position);
        imageLoading.LoadImage(categoryModel.getCat_icon(), holder.categoryIV, null);
        holder.categoryNameTV.setText(categoryModel.getCat_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewClick.onClickItem(holder.getAdapterPosition(), view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryIV;
        TextView categoryNameTV;

        private MyViewHolder(View itemView) {
            super(itemView);
            categoryIV = (ImageView) itemView.findViewById(R.id.categoryIV);
            categoryNameTV = (TextView) itemView.findViewById(R.id.categoryNameTV);

        }
    }

    public void addItem(ArrayList<CategoryModel> categoryModels){
        this.categoryModels = categoryModels;
        notifyDataSetChanged();
    }
}

