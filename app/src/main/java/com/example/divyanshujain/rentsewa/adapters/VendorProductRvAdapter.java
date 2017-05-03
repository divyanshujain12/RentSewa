package com.example.divyanshujain.rentsewa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.divyanshujain.rentsewa.Interfaces.RecyclerViewClick;
import com.example.divyanshujain.rentsewa.Models.ProductsModel;
import com.example.divyanshujain.rentsewa.R;
import com.example.divyanshujain.rentsewa.Utils.ImageLoading;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;

/**
 * Created by divyanshuPC on 4/7/2017.
 */

public class VendorProductRvAdapter extends RecyclerView.Adapter<VendorProductRvAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ProductsModel> productsModels;
    private RecyclerViewClick recyclerViewClick;
    private ImageLoading imageLoading;

    public VendorProductRvAdapter(Context context, ArrayList<ProductsModel> arrayList, RecyclerViewClick recyclerViewClick) {
        this.recyclerViewClick = recyclerViewClick;
        this.productsModels = arrayList;
        this.context = context;
        imageLoading = new ImageLoading(context, 5);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vendor_all_product_rv_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        ProductsModel productsModel = productsModels.get(position);
        imageLoading.LoadImage(productsModel.getImage1(), holder.productIV, null);
        holder.productTitleTV.setText(productsModel.getTitle());
        holder.productPriceTV.setText(context.getString(R.string.rs) + " " + productsModel.getPrice());
        holder.timePeriodTV.setText(context.getString(R.string.time_period) + " " + productsModel.getTime_period());
        if (productsModel.getStatus().equals("0")) {
            holder.editIV.setVisibility(View.VISIBLE);
        } else {
        holder.editIV.setVisibility(View.GONE);
        }
        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewClick.onClickItem(holder.getAdapterPosition(), v);
            }
        });
        holder.editIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewClick.onClickItem(holder.getAdapterPosition(), v);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewClick.onClickItem(holder.getAdapterPosition(), view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productsModels.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productIV, editIV, deleteIV;
        TextView productTitleTV, productPriceTV, timePeriodTV;

        private MyViewHolder(View itemView) {
            super(itemView);
            productIV = (ImageView) itemView.findViewById(R.id.productIV);
            editIV = (ImageView) itemView.findViewById(R.id.editIV);
            productTitleTV = (TextView) itemView.findViewById(R.id.productTitleTV);
            productPriceTV = (TextView) itemView.findViewById(R.id.productPriceTV);
            timePeriodTV = (TextView) itemView.findViewById(R.id.timePeriodTV);
            deleteIV = (ImageView) itemView.findViewById(R.id.deleteIV);
        }
    }

    public void addItem(ArrayList<ProductsModel> productsModels) {
        this.productsModels = productsModels;
        notifyDataSetChanged();
    }

    public void deleteProduct(int pos) {
        productsModels.remove(pos);
        notifyDataSetChanged();
    }
}


