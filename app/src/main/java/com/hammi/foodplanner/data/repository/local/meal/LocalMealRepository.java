package com.hammi.foodplanner.data.repository.local.meal;

import android.content.Context;

import com.hammi.foodplanner.data.datasource.local.meal.LocalMealDataSource;
 import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.db.AppDatabase;
import io.reactivex.rxjava3.core.Single;
public class LocalMealRepository {

    private static LocalMealRepository instance;
    private final LocalMealDataSource localDataSource;

    private LocalMealRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        this.localDataSource = LocalMealDataSource.getInstance(database);
    }

    public static synchronized LocalMealRepository getInstance(Context context) {
        if (instance == null) {
            instance = new LocalMealRepository(context);
        }
        return instance;
    }

    public Single<MealEntity> getMealById(String mealId) {
        return localDataSource.getMealById(mealId);
    }
}