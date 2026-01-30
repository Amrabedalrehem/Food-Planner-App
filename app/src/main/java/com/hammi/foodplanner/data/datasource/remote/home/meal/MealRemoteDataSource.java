package com.hammi.foodplanner.data.datasource.remote.home.meal;

import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.data.datasource.remote.meal.MealService;
import com.hammi.foodplanner.network.Network;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class MealRemoteDataSource {
    private final MealService mealService;
    private static MealRemoteDataSource mealRemoteDataSource;

    private MealRemoteDataSource() {
        mealService = Network.getInstance().mealService;
    }

    public static synchronized MealRemoteDataSource getInstance() {
        if (mealRemoteDataSource == null) {
            mealRemoteDataSource = new MealRemoteDataSource();
        }
        return mealRemoteDataSource;
    }

     public Single<List<Meal>> getRandomMeal() {
        return mealService.getRandomMeal()
                .map(response -> {
                    if (response.getMeals() != null && !response.getMeals().isEmpty()) {
                        return response.getMeals();
                    } else {
                        throw new Exception("Empty meal list");
                    }
                });
    }
}