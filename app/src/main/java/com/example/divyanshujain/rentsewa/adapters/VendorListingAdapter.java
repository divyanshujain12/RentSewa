package com.example.divyanshujain.rentsewa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.divyanshujain.rentsewa.Interfaces.RecyclerViewClick;
import com.example.divyanshujain.rentsewa.Models.ProductsModel;
import com.example.divyanshujain.rentsewa.Models.VendorListingModel;
import com.example.divyanshujain.rentsewa.R;
import com.example.divyanshujain.rentsewa.Utils.ImageLoading;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;

/**
 * Created by divyanshuPC on 1/30/2017.
 */

public class VendorListingAdapter extends RecyclerView.Adapter<VendorListingAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<VendorListingModel> vendorListingModels;
    private RecyclerViewClick recyclerViewClick;
    private ImageLoading imageLoading;

    public VendorListingAdapter(Context context, ArrayList<VendorListingModel> vendorListingModels, RecyclerViewClick recyclerViewClick) {
        this.recyclerViewClick = recyclerViewClick;
        this.vendorListingModels = vendorListingModels;
        this.context = context;
        imageLoading = new ImageLoading(context, 5);
    }

    @Override
    public VendorListingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vendor_listing_rv, parent, false);

        return new VendorListingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VendorListingAdapter.MyViewHolder holder, int position) {

        VendorListingModel vendorListingModel = vendorListingModels.get(position);
        imageLoading.LoadImage(vendorListingModel.getImage1(), holder.productIV, null);
        holder.productTitleTV.setText(vendorListingModel.getTitle());
        holder.productPriceTV.setText(context.getString(R.string.rs) + " " + vendorListingModel.getPrice());
        holder.visitorNameTV.setText(vendorListingModel.getVisitor_name());
        holder.dateTV.setText(vendorListingModel.getCreated());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewClick.onClickItem(holder.getAdapterPosition(), view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return vendorListingModels.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productIV;
        TextView productTitleTV, productPriceTV, visitorNameTV, dateTV;

        private MyViewHolder(View itemView) {
            super(itemView);
            productIV = (ImageView) itemView.findViewById(R.id.productIV);
            productTitleTV = (TextView) itemView.findViewById(R.id.productTitleTV);
            productPriceTV = (TextView) itemView.findViewById(R.id.productPriceTV);
            visitorNameTV = (TextView) itemView.findViewById(R.id.visitorNameTV);
            dateTV = (TextView) itemView.findViewById(R.id.dateTV);
        }
    }

    public void addItem(ArrayList<VendorListingModel> vendorListingModels) {
        this.vendorListingModels = vendorListingModels;
        notifyDataSetChanged();
    }
}


