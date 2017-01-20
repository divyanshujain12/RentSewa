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

    public final static Parcelable.Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CategoryModel createFromParcel(Parcel in) {
            CategoryModel instance = new CategoryModel();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.cat_name = ((String) in.readValue((String.class.getClassLoader())));
            instance.slug = ((String) in.readValue((String.class.getClassLoader())));
            instance.cat_icon = ((String) in.readValue((String.class.getClassLoader())));
            instance.subcatData = ((ArrayList) in.readValue((ArrayList.class.getClassLoader())));
            return instance;
        }

        public CategoryModel[] newArray(int size) {
            return (new CategoryModel[size]);
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(cat_name);
        dest.writeValue(slug);
        dest.writeValue(cat_icon);
        dest.writeValue(subcatData);
    }

    public int describeContents() {
        return 0;
    }

}