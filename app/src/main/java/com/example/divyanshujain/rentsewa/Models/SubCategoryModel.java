package com.example.divyanshujain.rentsewa.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class SubCategoryModel implements Parcelable {

    private String id;
    private String cat_id;
    private String subcat_name;
    private String slug;
    private Integer pcount;
    public SubCategoryModel(){

    }


    protected SubCategoryModel(Parcel in) {
        id = in.readString();
        cat_id = in.readString();
        subcat_name = in.readString();
        slug = in.readString();
        pcount = in.readInt();
    }

    public static final Creator<SubCategoryModel> CREATOR = new Creator<SubCategoryModel>() {
        @Override
        public SubCategoryModel createFromParcel(Parcel in) {
            return new SubCategoryModel(in);
        }

        @Override
        public SubCategoryModel[] newArray(int size) {
            return new SubCategoryModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getSubcat_name() {
        return subcat_name;
    }

    public void setSubcat_name(String subcat_name) {
        this.subcat_name = subcat_name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getPcount() {
        return pcount;
    }

    public void setPcount(Integer pcount) {
        this.pcount = pcount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cat_id);
        dest.writeString(subcat_name);
        dest.writeString(slug);
        dest.writeInt(pcount);
    }
}
