package com.hammi.foodplanner.data.datasource.local.meal;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.db.AppDatabase;

import io.reactivex.rxjava3.core.Single;

public class LocalMealDataSource {

    private static LocalMealDataSource instance;
    private final MealDao mealDao;

    private LocalMealDataSource(AppDatabase database) {
        this.mealDao = database.mealDao();
    }

    public static synchronized LocalMealDataSource getInstance(AppDatabase database) {
        if (instance == null) {
            instance = new LocalMealDataSource(database);
        }
        return instance;
    }
    public Single<MealEntity> getMealById(String mealId) {
        return mealDao.getMealById(mealId);
    }
}