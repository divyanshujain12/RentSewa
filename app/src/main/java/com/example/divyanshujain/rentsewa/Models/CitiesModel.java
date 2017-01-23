package com.example.divyanshujain.rentsewa.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by divyanshu.jain on 1/23/2017.
 */

public class CitiesModel implements Parcelable {

    String id;
    String city_name;

    public CitiesModel() {
    }

    protected CitiesModel(Parcel in) {
        id = in.readString();
        city_name = in.readString();
    }

    public static final Creator<CitiesModel> CREATOR = new Creator<CitiesModel>() {
        @Override
        public CitiesModel createFromParcel(Parcel in) {
            return new CitiesModel(in);
        }

        @Override
        public CitiesModel[] newArray(int size) {
            return new CitiesModel[size];
        }
    };

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(city_name);
    }
}
