package com.example.foodplanner.data.repository.meal;

import com.example.foodplanner.data.datasource.remote.home.meal.MealRemoteDataSource;
import com.example.foodplanner.data.models.Meal;
import com.example.foodplanner.data.datasource.remote.NetworkCallback;

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
