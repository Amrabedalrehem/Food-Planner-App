package com.hammi.foodplanner.data.repository.remote.meal;

import com.hammi.foodplanner.data.datasource.remote.home.meal.MealRemoteDataSource;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.data.datasource.remote.meal.NetworkCallback;

public class MealRepository {
    private static MealRepository mealRepository;
    private MealRemoteDataSource mealRemoteDataSource;

    private MealRepository() {
        mealRemoteDataSource = MealRemoteDataSource.getInstance();
    }

    public static synchronized MealRepository getInstance() {
        if (mealRepository == null) {
            mealRepository = new MealRepository();
        }
        return mealRepository;
    }

    public void getRandomMael(NetworkCallback<Meal> callback) {

        mealRemoteDataSource.getMeal(callback);
    }

}
