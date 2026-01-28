package com.hammi.foodplanner.data.repository.local.mealplan;
import android.content.Context;

import com.hammi.foodplanner.data.datasource.local.mealplan.MealPlanLocalDataSource;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.local.MealPlanEntity;
import com.hammi.foodplanner.db.AppDatabase;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealPlanRepository {

    private static MealPlanRepository instance;
    private final MealPlanLocalDataSource localDataSource;

    private MealPlanRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        this.localDataSource = MealPlanLocalDataSource.getInstance(database);
    }

    public static synchronized MealPlanRepository getInstance(Context context) {
        if (instance == null) {
            instance = new MealPlanRepository(context);
        }
        return instance;
    }

    public Completable addMealToPlan(MealEntity meal, long dateTimestamp) {
        return localDataSource.addMealToPlan(meal, dateTimestamp);
    }

    public Completable removeMealFromPlan(int planId) {
        return localDataSource.removeMealFromPlan(planId);
    }

    public Flowable<List<MealEntity>> getMealsForDate(long startOfDay, long endOfDay) {
        return localDataSource.getMealsForDate(startOfDay, endOfDay);
    }

    public Flowable<List<MealPlanEntity>> getMealPlanEntriesForDate(long startOfDay, long endOfDay) {
        return localDataSource.getMealPlanEntriesForDate(startOfDay, endOfDay);
    }

    public Flowable<Integer> getWeekMealsCount(long startOfWeek, long endOfWeek) {
        return localDataSource.getWeekMealsCount(startOfWeek, endOfWeek);
    }
}