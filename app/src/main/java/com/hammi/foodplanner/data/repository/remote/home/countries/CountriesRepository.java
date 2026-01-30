package com.hammi.foodplanner.data.repository.remote.home.countries;

import com.hammi.foodplanner.data.datasource.remote.home.countries.CountriesDataSource;
import com.hammi.foodplanner.data.models.remote.Countries;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class CountriesRepository {

    private final CountriesDataSource countriesDataSource;
    private static CountriesRepository countriesRepository;

    private CountriesRepository() {
        countriesDataSource = CountriesDataSource.getInstance();
    }

    public static synchronized CountriesRepository getInstance() {
        if (countriesRepository == null) {
            countriesRepository = new CountriesRepository();
        }
        return countriesRepository;
    }

     public Single<List<Countries>> getAllCountries() {
        return countriesDataSource.getAllCountries();
    }
}