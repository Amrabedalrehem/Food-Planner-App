package com.hammi.foodplanner.data.datasource.local.mealplan;
import com.hammi.foodplanner.data.datasource.local.meal.MealDao;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.local.MealPlanEntity;
import com.hammi.foodplanner.db.AppDatabase;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealPlanLocalDataSource {

    private static MealPlanLocalDataSource instance;

    private final MealDao mealDao;
    private final MealPlanDao mealPlanDao;

    private MealPlanLocalDataSource(AppDatabase database) {
        this.mealDao = database.mealDao();
        this.mealPlanDao = database.mealPlanDao();
    }

    public static synchronized MealPlanLocalDataSource getInstance(AppDatabase database) {
        if (instance == null) {
            instance = new MealPlanLocalDataSource(database);
        }
        return instance;
    }


    public Completable addMealToPlan(MealEntity meal, long dateTimestamp) {
        return mealDao.insertMeal(meal)
                .andThen(mealPlanDao.addMealToPlan(
                        new MealPlanEntity(
                                meal.getMealId(),
                                dateTimestamp
                        )
                ));
    }

    public Completable removeMealFromPlan(int planId) {
        return mealPlanDao.deleteMealPlanById(planId);
    }

    public Flowable<List<MealEntity>> getMealsForDate(long startOfDay, long endOfDay) {
        return mealPlanDao.getMealsForDate(startOfDay, endOfDay);
    }

    public Flowable<List<MealPlanEntity>> getMealPlanEntriesForDate(long startOfDay, long endOfDay) {
        return mealPlanDao.getMealPlanEntriesForDate(startOfDay, endOfDay);
    }

    public Flowable<Integer> getWeekMealsCount(long startOfWeek, long endOfWeek) {
        return mealPlanDao.getWeekMealsCount(startOfWeek, endOfWeek);
    }
}