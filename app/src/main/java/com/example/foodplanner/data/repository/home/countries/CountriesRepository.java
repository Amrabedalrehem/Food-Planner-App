package com.example.foodplanner.data.repository.home.countries;

import com.example.foodplanner.data.datasource.remote.NetworkCallback;
import com.example.foodplanner.data.datasource.remote.home.countries.CountriesDataSource;
import com.example.foodplanner.data.models.remote.Countries;

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
