package com.example.divyanshujain.rentsewa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.divyanshujain.rentsewa.Interfaces.RecyclerViewClick;
import com.example.divyanshujain.rentsewa.Models.SubCategoryModel;
import com.example.divyanshujain.rentsewa.R;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;

/**
 * Created by divyanshu.jain on 12/23/2016.
 */

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<SubCategoryModel> subCategoryModels;
    private RecyclerViewClick recyclerViewClick;

    public SubCategoriesAdapter(Context context, ArrayList<SubCategoryModel> arrayList, RecyclerViewClick recyclerViewClick) {
        this.recyclerViewClick = recyclerViewClick;
        this.subCategoryModels = arrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_categories_adapter_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SubCategoryModel subCategoryModel = subCategoryModels.get(position);
        holder.productNameTV.setText(subCategoryModel.getSubcat_name());
        holder.remainingProductsTV.setText("Remaining Items: " + String.valueOf(subCategoryModel.getPcount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewClick.onClickItem(holder.getAdapterPosition(), view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategoryModels.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView subCategoryIV;
        TextView productNameTV, remainingProductsTV;

        private MyViewHolder(View itemView) {
            super(itemView);
            productNameTV = (TextView) itemView.findViewById(R.id.productNameTV);
            remainingProductsTV = (TextView) itemView.findViewById(R.id.remainingProductsTV);
        }
    }
}


