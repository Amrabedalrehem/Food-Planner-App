package com.hammi.foodplanner.data.repository.home.details;
import android.content.Context;

import com.hammi.foodplanner.data.datasource.MealMapper;
import com.hammi.foodplanner.data.datasource.local.favourite.FavoritesLocalDataSource;
import com.hammi.foodplanner.data.datasource.local.mealplan.MealPlanLocalDataSource;
import com.hammi.foodplanner.data.datasource.remote.meal.NetworkCallback;
import com.hammi.foodplanner.data.datasource.remote.home.details.detailsDataSource;
 import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.db.AppDatabase;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class DetailsRepository {

    private static DetailsRepository detailsRepository;
    private detailsDataSource remoteDataSource;
    private FavoritesLocalDataSource favoritesDataSource;
    private MealPlanLocalDataSource mealPlanDataSource;

    private DetailsRepository(Context context) {
        this.remoteDataSource = detailsDataSource.getInstance();
        AppDatabase database = AppDatabase.getInstance(context);
        this.favoritesDataSource = FavoritesLocalDataSource.getInstance(database);
        this.mealPlanDataSource = MealPlanLocalDataSource.getInstance(database);
    }

    public static synchronized DetailsRepository getInstance(Context context) {
        if (detailsRepository == null) {
            detailsRepository = new DetailsRepository(context);
        }
        return detailsRepository;
    }



    public void getDetails(String id, NetworkCallback<List<Meal>> callback) {
        remoteDataSource.getDetails(id, callback);
    }



    public Completable addToFavorites(Meal meal) {
        MealEntity entity = MealMapper.toEntity(meal);
        return favoritesDataSource.addToFavorites(entity);
    }

    public Completable removeFromFavorites(String mealId) {
        return favoritesDataSource.removeFromFavorites(mealId);
    }

    public Single<Boolean> isFavorite(String mealId) {
        return favoritesDataSource.isFavorite(mealId);
    }

    public Completable addMealToPlan(Meal meal, long dateTimestamp) {
        MealEntity entity = MealMapper.toEntity(meal);
        return mealPlanDataSource.addMealToPlan(entity, dateTimestamp);
    }
}