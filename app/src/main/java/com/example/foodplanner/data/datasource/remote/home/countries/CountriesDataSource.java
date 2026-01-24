package com.example.foodplanner.data.datasource.remote.home.countries;

import com.example.foodplanner.data.datasource.remote.MealService;
import com.example.foodplanner.data.datasource.remote.NetworkCallback;
 import com.example.foodplanner.data.models.Countries;
import com.example.foodplanner.network.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountriesDataSource {
    MealService mealService ;
   public static  CountriesDataSource countriesDataSource;
    private CountriesDataSource()
    {
        mealService = Network.getInstance().mealService;
    }
    public static synchronized CountriesDataSource  getInstance()
    {
        if(countriesDataSource==null)
        {
            countriesDataSource = new CountriesDataSource();

        }
        return  countriesDataSource;
    }
    public void getAllCountries(NetworkCallback<List<Countries>> callback)
    {
        mealService.getListALLCountries("list").enqueue(new Callback<CountriesResponse>() {
            @Override
            public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Countries>  countries = response.body().getCountries();
                    callback.onSuccess(countries);
                } else {
                    callback.onError("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CountriesResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }


}
