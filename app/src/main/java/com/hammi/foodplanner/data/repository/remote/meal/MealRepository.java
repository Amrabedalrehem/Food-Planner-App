package com.hammi.foodplanner.data.repository.remote.meal;

import com.hammi.foodplanner.data.datasource.remote.home.meal.MealRemoteDataSource;
import com.hammi.foodplanner.data.models.remote.Meal;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class MealRepository {
    private static MealRepository mealRepository;
    private final MealRemoteDataSource mealRemoteDataSource;

    private MealRepository() {
        mealRemoteDataSource = MealRemoteDataSource.getInstance();
    }

    public static synchronized MealRepository getInstance() {
        if (mealRepository == null) {
            mealRepository = new MealRepository();
        }
        return mealRepository;
    }

     public Single<List<Meal>> getRandomMeal() {
        return mealRemoteDataSource.getRandomMeal();
    }
}