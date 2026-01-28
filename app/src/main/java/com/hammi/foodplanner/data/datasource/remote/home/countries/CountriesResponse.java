package com.hammi.foodplanner.data.datasource.remote.home.countries;

import com.hammi.foodplanner.data.models.remote.Countries;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesResponse {
    @SerializedName("meals")
    private List<Countries> countries;

    public CountriesResponse(List<Countries> countries)
    {
        this.countries = countries;
    }

    public List<Countries> getCountries() {
        return countries;
    }

    public void setCountries(List<Countries> countries) {
        this.countries = countries;
    }
}
