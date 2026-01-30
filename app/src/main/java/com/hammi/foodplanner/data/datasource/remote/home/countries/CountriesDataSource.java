package com.hammi.foodplanner.data.datasource.remote.home.countries;

import com.hammi.foodplanner.data.datasource.remote.meal.MealService;
import com.hammi.foodplanner.data.models.remote.Countries;
import com.hammi.foodplanner.network.Network;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class CountriesDataSource {
    private final MealService mealService;
    private static CountriesDataSource countriesDataSource;

    private CountriesDataSource() {
        mealService = Network.getInstance().mealService;
    }

    public static synchronized CountriesDataSource getInstance() {
        if (countriesDataSource == null) {
            countriesDataSource = new CountriesDataSource();
        }
        return countriesDataSource;
    }

     public Single<List<Countries>> getAllCountries() {
         return mealService.getListALLCountries("list")
                .map(response -> response.getCountries());
    }
}