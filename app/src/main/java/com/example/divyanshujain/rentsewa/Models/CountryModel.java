package com.example.divyanshujain.rentsewa.Models;

/**
 * Created by divyanshuPC on 4/2/2017.
 */

public class CountryModel {
    private String country_id;
    private String country__name;

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getName() {
        return country__name;
    }

    public void setName(String name) {
        this.country__name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return this.country_id.equals(((CountryModel) obj).country_id);
    }
}
