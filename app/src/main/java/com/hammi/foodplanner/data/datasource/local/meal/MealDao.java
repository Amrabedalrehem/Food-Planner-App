package com.hammi.foodplanner.data.datasource.local.meal;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hammi.foodplanner.data.models.local.MealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(MealEntity meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeals(List<MealEntity> meals);

    @Query("SELECT * FROM meals WHERE mealId = :mealId")
    Single<MealEntity> getMealById(String mealId);

    @Query("SELECT * FROM meals")
    Flowable<List<MealEntity>> getAllMeals();

    @Query("SELECT COUNT(*) FROM meals WHERE mealId = :mealId")
    Single<Integer> isMealExists(String mealId);

    @Query("DELETE FROM meals WHERE mealId = :mealId")
    Completable deleteMeal(String mealId);

    @Query("DELETE FROM meals")
    Completable deleteAllMeals();
}