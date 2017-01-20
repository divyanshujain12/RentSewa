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
    public final static Parcelable.Creator<SubCategoryModel> CREATOR = new Creator<SubCategoryModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SubCategoryModel createFromParcel(Parcel in) {
            SubCategoryModel instance = new SubCategoryModel();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.cat_id = ((String) in.readValue((String.class.getClassLoader())));
            instance.subcat_name = ((String) in.readValue((String.class.getClassLoader())));
            instance.slug = ((String) in.readValue((String.class.getClassLoader())));
            instance.pcount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public SubCategoryModel[] newArray(int size) {
            return (new SubCategoryModel[size]);
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(cat_id);
        dest.writeValue(subcat_name);
        dest.writeValue(slug);
        dest.writeValue(pcount);
    }

    public int describeContents() {
        return 0;
    }

}
