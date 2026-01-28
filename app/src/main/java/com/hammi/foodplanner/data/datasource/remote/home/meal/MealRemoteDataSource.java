package com.hammi.foodplanner.data.datasource.remote.home.meal;

import com.hammi.foodplanner.data.datasource.remote.meal.MealResponse;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.data.datasource.remote.meal.MealService;
import com.hammi.foodplanner.data.datasource.remote.meal.NetworkCallback;
import com.hammi.foodplanner.network.Network;


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
    public static synchronized MealRemoteDataSource getInstance() {
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








