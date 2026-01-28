package com.hammi.foodplanner.data.datasource.local.favourite;
import com.hammi.foodplanner.data.datasource.local.meal.MealDao;
import com.hammi.foodplanner.data.models.local.FavoriteEntity;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.db.AppDatabase;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class FavoritesLocalDataSource {

    private static FavoritesLocalDataSource instance;
    private final MealDao mealDao;
    private final FavoriteDao favoriteDao;

    private FavoritesLocalDataSource(AppDatabase database) {
        this.mealDao = database.mealDao();
        this.favoriteDao = database.favoriteDao();
    }

    public static synchronized FavoritesLocalDataSource getInstance(AppDatabase database) {
        if (instance == null) {
            instance = new FavoritesLocalDataSource(database);
        }
        return instance;
    }


    public Completable addToFavorites(MealEntity meal) {
        return mealDao.insertMeal(meal).andThen(favoriteDao.addFavorite(new FavoriteEntity(meal.getMealId(), System.currentTimeMillis())));
    }

    public Completable removeFromFavorites(String mealId) {
        return favoriteDao.removeFavorite(mealId);
    }

    public Flowable<List<MealEntity>> getAllFavorites() {
        return favoriteDao.getAllFavoriteMeals();
    }

    public Single<Boolean> isFavorite(String mealId) {
        return favoriteDao.isFavorite(mealId)
                .map(count -> count > 0);
    }
}