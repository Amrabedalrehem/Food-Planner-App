package com.hammi.foodplanner.data.repository.remote.home.details;
import android.content.Context;
import com.hammi.foodplanner.data.datasource.local.meal.MealMapper;
import com.hammi.foodplanner.data.datasource.remote.meal.NetworkCallback;
import com.hammi.foodplanner.data.datasource.remote.home.details.detailsDataSource;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.data.repository.local.favorites.FavoritesRepository;
import com.hammi.foodplanner.data.repository.local.mealplan.MealPlanRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class DetailsRepository {

    private static DetailsRepository detailsRepository;
    private final detailsDataSource remoteDataSource;
    private final FavoritesRepository favoritesRepository;
     private  final MealPlanRepository mealPlanRepository;
    private DetailsRepository(Context context, MealPlanRepository mealPlanRepository) {
        this.mealPlanRepository = mealPlanRepository;
        this.remoteDataSource = detailsDataSource.getInstance();
          this.favoritesRepository = FavoritesRepository.getInstance(context);
     }

    public static synchronized DetailsRepository getInstance(Context context) {
        if (detailsRepository == null) {
            detailsRepository = new DetailsRepository(context, MealPlanRepository.getInstance(context));
        }
        return detailsRepository;
    }

    public void getDetails(String id, NetworkCallback<List<Meal>> callback) {
        remoteDataSource.getDetails(id, callback);
    }

     public Completable addToFavorites(Meal meal) {
        MealEntity entity = MealMapper.toEntity(meal);
         return favoritesRepository.addToFavorites(entity);
    }

     public Completable removeFromFavorites(Meal meal) {
         MealEntity entity = MealMapper.toEntity(meal);
         return favoritesRepository.removeFromFavorites(entity);
    }

    public Single<Boolean> isFavorite(String mealId) {
        return favoritesRepository.isFavorite(mealId);
    }

    public Completable addMealToPlan(Meal meal) {
        MealEntity entity = MealMapper.toEntity(meal);
        return mealPlanRepository.addMealToPlan(entity, System.currentTimeMillis());
    }




}