package com.hammi.foodplanner.data.repository.home.countries;

import com.hammi.foodplanner.data.datasource.remote.meal.NetworkCallback;
import com.hammi.foodplanner.data.datasource.remote.home.countries.CountriesDataSource;
import com.hammi.foodplanner.data.models.remote.Countries;

import java.util.List;

public class CountriesRepository {

    CountriesDataSource countriesDataSource ;
    private static CountriesRepository countriesRepository;

    private   CountriesRepository()
    {
        countriesDataSource = CountriesDataSource.getInstance();
    }
    public  static synchronized  CountriesRepository getInstance()
    {
        if(countriesRepository==null)
        {
            countriesRepository = new CountriesRepository();
        }
        return countriesRepository;
    }

    public void getAllCountries(NetworkCallback<List<Countries>> callback)
    {
     countriesDataSource.getAllCountries(callback);
    }

}
