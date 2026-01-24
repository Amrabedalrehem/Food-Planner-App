package com.example.foodplanner.data.models;

import com.google.gson.annotations.SerializedName;

public class Countries {
    @SerializedName("strArea")
    private String strArea;

    public Countries() {}

    public Countries(String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }
}
