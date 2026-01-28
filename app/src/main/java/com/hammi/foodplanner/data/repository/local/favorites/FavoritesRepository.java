package com.hammi.foodplanner.data.repository.local.favorites;
import android.content.Context;

import com.hammi.foodplanner.data.datasource.local.favourite.FavoritesLocalDataSource;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.db.AppDatabase;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class FavoritesRepository {

    private static FavoritesRepository instance;
    private final FavoritesLocalDataSource localDataSource;
    private FavoritesRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        this.localDataSource = FavoritesLocalDataSource.getInstance(database);
    }

    public static synchronized FavoritesRepository getInstance(Context context) {
        if (instance == null) {
            instance = new FavoritesRepository(context);
        }
        return instance;
    }


    public Completable addToFavorites(MealEntity meal) {
        return localDataSource.addToFavorites(meal);
    }

    public Completable removeFromFavorites(String mealId) {
        return localDataSource.removeFromFavorites(mealId);
    }

    public Flowable<List<MealEntity>> getAllFavorites() {
        return localDataSource.getAllFavorites();
    }

    public Single<Boolean> isFavorite(String mealId) {
        return localDataSource.isFavorite(mealId);
    }
}