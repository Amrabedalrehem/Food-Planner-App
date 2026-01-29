package com.hammi.foodplanner.data.repository.local.favorites;
import android.content.Context;

import com.hammi.foodplanner.data.datasource.local.favourite.FavoriteDao;
import com.hammi.foodplanner.data.datasource.local.favourite.FavoritesLocalDataSource;
import com.hammi.foodplanner.data.datasource.local.meal.MealDao;
import com.hammi.foodplanner.data.datasource.local.mealplan.MealPlanLocalDataSource;
import com.hammi.foodplanner.data.datasource.remote.firebase.FirebaseRemoteDataSource;
import com.hammi.foodplanner.data.models.local.FavoriteEntity;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.local.MealPlanEntity;
import com.hammi.foodplanner.db.AppDatabase;
import com.hammi.foodplanner.utility.NetworkUtils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritesRepository {

    private static FavoritesRepository instance;
    private final FavoritesLocalDataSource localDataSource;
    private final MealPlanLocalDataSource planLocalDataSource;
    private final FirebaseRemoteDataSource remoteDataSource;
    private final Context context;

    private FavoritesRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        this.localDataSource = FavoritesLocalDataSource.getInstance(database);
        this.remoteDataSource = new FirebaseRemoteDataSource(context);
        this.planLocalDataSource = MealPlanLocalDataSource.getInstance(database);
        this.context = context;
    }

    public static synchronized FavoritesRepository getInstance(Context context) {
        if (instance == null) {
            instance = new FavoritesRepository(context);
        }
        return instance;
    }

    public Completable addToFavorites(MealEntity meal) {
        FavoriteEntity fav = new FavoriteEntity(meal.getMealId(), System.currentTimeMillis());

        Completable localTask = localDataSource.addToFavorites(meal);

        if (NetworkUtils.isInternetAvailable(context)) {
            return localTask.andThen(remoteDataSource.addToFavorites(fav));
        }
        return localTask;
    }

    public Completable removeFromFavorites(MealEntity meal) {
        Completable localTask = localDataSource.removeFromFavorites(meal.getMealId());

        if (NetworkUtils.isInternetAvailable(context)) {
            FavoriteEntity fav = new FavoriteEntity(meal.getMealId(), 0);
            return localTask.andThen(remoteDataSource.removeFromFavorites(fav));
        }
        return localTask;
    }

    public Flowable<List<MealEntity>> getAllFavorites() {
        return localDataSource.getAllFavorites();
    }

    public Single<Boolean> isFavorite(String mealId) {
        return localDataSource.isFavorite(mealId);
    }


}