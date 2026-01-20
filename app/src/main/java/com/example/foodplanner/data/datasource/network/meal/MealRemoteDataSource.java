package com.example.foodplanner.data.datasource.network;

import com.example.foodplanner.data.models.Meal;
import com.example.foodplanner.network.MealResponse;
import com.example.foodplanner.network.MealService;
import com.example.foodplanner.network.Network;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  MealRemoteDataSource {
    private MealService mealService;
    private  List<Meal> productList;

    public MealRemoteDataSource()
    {
        mealService = Network.getInstance().mealService;
    }

    public void getProducts(MealNetworkResponse callback){

        mealService.getLatestMeals().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    productList = response.body().MealList;
                    callback.onSuccess(productList);


                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {

            }}
);
        }
    }





