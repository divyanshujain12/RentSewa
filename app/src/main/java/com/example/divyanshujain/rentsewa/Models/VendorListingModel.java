package com.example.divyanshujain.rentsewa.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by divyanshuPC on 1/30/2017.
 */

public class VendorListingModel implements Parcelable {
    String id;
    String title;
    String price;
    String cat_name;
    String subcat_name;
    String created;
    String views;
    String time_period;
    String visitor_name;
    String visitor_email;
    String visitor_phone;
    String image1;

    public VendorListingModel() {
    }

    protected VendorListingModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        price = in.readString();
        cat_name = in.readString();
        subcat_name = in.readString();
        created = in.readString();
        views = in.readString();
        time_period = in.readString();
        visitor_name = in.readString();
        visitor_email = in.readString();
        visitor_phone = in.readString();
        image1 = in.readString();
    }

    public static final Creator<VendorListingModel> CREATOR = new Creator<VendorListingModel>() {
        @Override
        public VendorListingModel createFromParcel(Parcel in) {
            return new VendorListingModel(in);
        }

        @Override
        public VendorListingModel[] newArray(int size) {
            return new VendorListingModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getSubcat_name() {
        return subcat_name;
    }

    public void setSubcat_name(String subcat_name) {
        this.subcat_name = subcat_name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getTime_period() {
        return time_period;
    }

    public void setTime_period(String time_period) {
        this.time_period = time_period;
    }

    public String getVisitor_name() {
        return visitor_name;
    }

    public void setVisitor_name(String visitor_name) {
        this.visitor_name = visitor_name;
    }

    public String getVisitor_email() {
        return visitor_email;
    }

    public void setVisitor_email(String visitor_email) {
        this.visitor_email = visitor_email;
    }

    public String getVisitor_phone() {
        return visitor_phone;
    }

    public void setVisitor_phone(String visitor_phone) {
        this.visitor_phone = visitor_phone;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(price);
        parcel.writeString(cat_name);
        parcel.writeString(subcat_name);
        parcel.writeString(created);
        parcel.writeString(views);
        parcel.writeString(time_period);
        parcel.writeString(visitor_name);
        parcel.writeString(visitor_email);
        parcel.writeString(visitor_phone);
        parcel.writeString(image1);
    }
}
