package com.example.divyanshujain.rentsewa.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CategoryModel implements Parcelable {

    private String id;
    private String cat_name;
    private String slug;
    private String cat_icon;
    private ArrayList<SubCategoryModel> subcatData;

    public CategoryModel() {

    }

    protected CategoryModel(Parcel in) {
        id = in.readString();
        cat_name = in.readString();
        slug = in.readString();
        cat_icon = in.readString();
        subcatData = in.createTypedArrayList(SubCategoryModel.CREATOR);
    }

    public static final Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel in) {
            return new CategoryModel(in);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCat_icon() {
        return cat_icon;
    }

    public void setCat_icon(String cat_icon) {
        this.cat_icon = cat_icon;
    }

    public ArrayList<SubCategoryModel> getSubcatData() {
        return subcatData;
    }

    public void setSubcatData(ArrayList<SubCategoryModel> subcatData) {
        this.subcatData = subcatData;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cat_name);
        dest.writeString(slug);
        dest.writeString(cat_icon);
        dest.writeTypedList(subcatData);
    }
}