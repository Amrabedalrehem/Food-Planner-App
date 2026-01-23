package com.example.foodplanner.data.datasource.remote.home.meal;

import com.example.foodplanner.data.models.Meal;
import com.example.foodplanner.data.datasource.remote.MealService;
import com.example.foodplanner.data.datasource.remote.NetworkCallback;
import com.example.foodplanner.network.Network;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  MealRemoteDataSource {
    private MealService mealService;
    private  List<Meal> productList;
    private  Meal meal;
    private static MealRemoteDataSource mealRemoteDataSource;
    private MealRemoteDataSource()
    {
      mealService  = Network.getInstance().mealService;
    }
    public static MealRemoteDataSource getInstance() {
        if (mealRemoteDataSource == null) {
            mealRemoteDataSource = new MealRemoteDataSource();
        }
        return mealRemoteDataSource;
    }
    public void getMeal(NetworkCallback<Meal> callback) {
        mealService.getRandomMeal().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                     Meal meal = response.body().getMeals().get(0);
                    callback.onSuccess(meal);
                } else {
                    callback.onError("No meals found or server error");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
        }








