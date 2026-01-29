package com.hammi.foodplanner.data.repository.local.mealplan;

import android.content.Context;

import com.hammi.foodplanner.data.datasource.local.mealplan.MealPlanLocalDataSource;
import com.hammi.foodplanner.data.datasource.remote.firebase.FirebaseRemoteDataSource;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.local.MealPlanEntity;
import com.hammi.foodplanner.db.AppDatabase;
import com.hammi.foodplanner.utility.NetworkUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealPlanRepository {

    private static MealPlanRepository instance;
    private final MealPlanLocalDataSource localDataSource;
    private final FirebaseRemoteDataSource remoteDataSource;
    private final Context context;

    private MealPlanRepository(Context context, FirebaseRemoteDataSource remoteDataSource) {
        this.context = context;
        this.remoteDataSource = remoteDataSource;
        AppDatabase database = AppDatabase.getInstance(context);
        this.localDataSource = MealPlanLocalDataSource.getInstance(database);
    }

    public static synchronized MealPlanRepository getInstance(Context context) {
        if (instance == null) {
            instance = new MealPlanRepository(context, new FirebaseRemoteDataSource(context));
        }
        return instance;
    }

     public Completable addMealToPlan(MealEntity meal, long dateTimestamp) {
        MealPlanEntity mealPlan = new MealPlanEntity(meal.getMealId(), dateTimestamp);

        Completable localTask = localDataSource.addMealToPlan(meal, dateTimestamp);
        if (NetworkUtils.isInternetAvailable(context)) {
            return localTask.andThen(remoteDataSource.addToWeeklyPlan(mealPlan));
        }
        return localTask;
    }

     public Completable removeMealFromPlan(int planId, MealPlanEntity planEntity) {
         Completable localTask = localDataSource.removeMealFromPlan(planId);

         if (NetworkUtils.isInternetAvailable(context)) {
            return localTask.andThen(remoteDataSource.removeFromWeeklyPlan(planEntity));
        }
        return localTask;
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