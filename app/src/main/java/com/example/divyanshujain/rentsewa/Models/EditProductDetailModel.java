package com.example.divyanshujain.rentsewa.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by divyanshu.jain on 5/1/2017.
 */

public class EditProductDetailModel implements Parcelable{
    String pid;
    String user_id;
    String ptitle;
    String pprice;
    String pcategory;
    String psubcategory;
    String pbrand;
    String ptime_period;
    String description;
    String pimage;
    String pimage_fullpath;
    String city;
    String pname;
    String plocation;
    String pemail;
    String pphone;
    String paddress;
    String pcountry;
    String pstate;
    String pcity;
    String pzip;
    ArrayList<ImageModel> imagesArray;

    public EditProductDetailModel(){}

    protected EditProductDetailModel(Parcel in) {
        pid = in.readString();
        user_id = in.readString();
        ptitle = in.readString();
        pprice = in.readString();
        pcategory = in.readString();
        psubcategory = in.readString();
        pbrand = in.readString();
        ptime_period = in.readString();
        description = in.readString();
        pimage = in.readString();
        pimage_fullpath = in.readString();
        city = in.readString();
        pname = in.readString();
        plocation = in.readString();
        pemail = in.readString();
        pphone = in.readString();
        paddress = in.readString();
        pcountry = in.readString();
        pstate = in.readString();
        pcity = in.readString();
        pzip = in.readString();
        imagesArray = in.createTypedArrayList(ImageModel.CREATOR);
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPtitle() {
        return ptitle;
    }

    public void setPtitle(String ptitle) {
        this.ptitle = ptitle;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPcategory() {
        return pcategory;
    }

    public void setPcategory(String pcategory) {
        this.pcategory = pcategory;
    }

    public String getPsubcategory() {
        return psubcategory;
    }

    public void setPsubcategory(String psubcategory) {
        this.psubcategory = psubcategory;
    }

    public String getPbrand() {
        return pbrand;
    }

    public void setPbrand(String pbrand) {
        this.pbrand = pbrand;
    }

    public String getPtime_period() {
        return ptime_period;
    }

    public void setPtime_period(String ptime_period) {
        this.ptime_period = ptime_period;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public String getPimage_fullpath() {
        return pimage_fullpath;
    }

    public void setPimage_fullpath(String pimage_fullpath) {
        this.pimage_fullpath = pimage_fullpath;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPlocation() {
        return plocation;
    }

    public void setPlocation(String plocation) {
        this.plocation = plocation;
    }

    public String getPemail() {
        return pemail;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail;
    }

    public String getPphone() {
        return pphone;
    }

    public void setPphone(String pphone) {
        this.pphone = pphone;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public String getPcountry() {
        return pcountry;
    }

    public void setPcountry(String pcountry) {
        this.pcountry = pcountry;
    }

    public String getPstate() {
        return pstate;
    }

    public void setPstate(String pstate) {
        this.pstate = pstate;
    }

    public String getPcity() {
        return pcity;
    }

    public void setPcity(String pcity) {
        this.pcity = pcity;
    }

    public String getPzip() {
        return pzip;
    }

    public void setPzip(String pzip) {
        this.pzip = pzip;
    }

    public ArrayList<ImageModel> getImagesArray() {
        return imagesArray;
    }

    public void setImagesArray(ArrayList<ImageModel> imagesArray) {
        this.imagesArray = imagesArray;
    }

    public static final Creator<EditProductDetailModel> CREATOR = new Creator<EditProductDetailModel>() {
        @Override
        public EditProductDetailModel createFromParcel(Parcel in) {
            return new EditProductDetailModel(in);
        }

        @Override
        public EditProductDetailModel[] newArray(int size) {
            return new EditProductDetailModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pid);
        parcel.writeString(user_id);
        parcel.writeString(ptitle);
        parcel.writeString(pprice);
        parcel.writeString(pcategory);
        parcel.writeString(psubcategory);
        parcel.writeString(pbrand);
        parcel.writeString(ptime_period);
        parcel.writeString(description);
        parcel.writeString(pimage);
        parcel.writeString(pimage_fullpath);
        parcel.writeString(city);
        parcel.writeString(pname);
        parcel.writeString(plocation);
        parcel.writeString(pemail);
        parcel.writeString(pphone);
        parcel.writeString(paddress);
        parcel.writeString(pcountry);
        parcel.writeString(pstate);
        parcel.writeString(pcity);
        parcel.writeString(pzip);
        parcel.writeTypedList(imagesArray);
    }
}
