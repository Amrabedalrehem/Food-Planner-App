package com.hammi.foodplanner.data.datasource.local.mealplan;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hammi.foodplanner.data.models.local.FavoriteEntity;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.local.MealPlanEntity;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealPlanDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addMealToPlan(MealPlanEntity mealPlan);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addMealsToPlan(List<MealPlanEntity> mealPlans);
    @Query("SELECT meals.* FROM meals " +
            "INNER JOIN meal_plan ON meals.mealId = meal_plan.mealId " +
            "WHERE meal_plan.plannedDate BETWEEN :startOfDay AND :endOfDay " +
            "ORDER BY meal_plan.id ASC")
    Flowable<List<MealEntity>> getMealsForDate(long startOfDay, long endOfDay);

    @Query("SELECT meals.* FROM meals " +
            "INNER JOIN meal_plan ON meals.mealId = meal_plan.mealId " +
            "WHERE meal_plan.plannedDate BETWEEN :startDate AND :endDate " +
            "ORDER BY meal_plan.plannedDate ASC")
    Flowable<List<MealEntity>> getMealsInRange(long startDate, long endDate);

    @Query("SELECT * FROM meal_plan " +
            "WHERE plannedDate BETWEEN :startOfDay AND :endOfDay " +
            "ORDER BY id ASC")
    Flowable<List<MealPlanEntity>> getMealPlanEntriesForDate(long startOfDay, long endOfDay);

    @Query("SELECT COUNT(*) FROM meal_plan " +
            "WHERE plannedDate BETWEEN :startOfWeek AND :endOfWeek")
    Flowable<Integer> getWeekMealsCount(long startOfWeek, long endOfWeek);
    @Query("DELETE FROM meal_plan WHERE id = :planId")
    Completable deleteMealPlanById(int planId);

    @Query("DELETE FROM meal_plan " +
            "WHERE plannedDate BETWEEN :startOfDay AND :endOfDay")
    Completable clearDayPlan(long startOfDay, long endOfDay);
    @Query("DELETE FROM meal_plan")
    Completable clearAllPlans();

    @Query("SELECT * FROM meal_plan")
    Single<List<MealPlanEntity>> getAllMealPlansOnce();
     @Query("SELECT * FROM meal_plan")
    List<MealPlanEntity> getAllMealPlansDirect();
 }