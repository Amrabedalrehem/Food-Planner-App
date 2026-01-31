package com.hammi.foodplanner.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hammi.foodplanner.data.datasource.local.favourite.FavoriteDao;
import com.hammi.foodplanner.data.datasource.local.meal.MealDao;
import com.hammi.foodplanner.data.datasource.local.mealplan.MealPlanDao;
import com.hammi.foodplanner.data.models.local.FavoriteEntity;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.local.MealPlanEntity;

@Database(
        entities = {MealEntity.class, FavoriteEntity.class, MealPlanEntity.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MealDao mealDao();
    public abstract FavoriteDao favoriteDao();
    public abstract MealPlanDao mealPlanDao();
    private static volatile AppDatabase INSTANCE;
    private static final String DATABASE_NAME = "food_planner_db";

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
              INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();

            }
        }
        return INSTANCE;
    }



}