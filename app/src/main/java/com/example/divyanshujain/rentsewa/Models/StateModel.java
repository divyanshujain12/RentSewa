package com.example.divyanshujain.rentsewa.Models;

/**
 * Created by divyanshuPC on 4/2/2017.
 */

public class StateModel {
    private String state_id;
    private String state_name;

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getName() {
        return state_name;
    }

    public void setName(String name) {
        this.state_name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return this.state_id.equals(((StateModel) obj).state_id);
    }
}
