package com.example.divyanshujain.rentsewa.Models;

/**
 * Created by divyanshuPC on 2/1/2017.
 */

public class ImageModel {
    String image;

    public ImageModel(){

    }
    public ImageModel(String imgUrl){
        this.image = imgUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
