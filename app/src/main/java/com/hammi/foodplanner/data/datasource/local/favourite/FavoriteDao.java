package com.hammi.foodplanner.data.datasource.local.favourite;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.hammi.foodplanner.data.models.local.FavoriteEntity;
import com.hammi.foodplanner.data.models.local.MealEntity;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addFavorite(FavoriteEntity favorite);

    @Query("SELECT meals.* FROM meals " +
            "INNER JOIN favorites ON meals.mealId = favorites.mealId " +
            "ORDER BY favorites.timestamp DESC")
    Flowable<List<MealEntity>> getAllFavoriteMeals();

    @Query("SELECT COUNT(*) FROM favorites WHERE mealId = :mealId")
    Single<Integer> isFavorite(String mealId);

    @Query("SELECT COUNT(*) FROM favorites")
    Flowable<Integer> getFavoritesCount();


    @Query("DELETE FROM favorites WHERE mealId = :mealId")
    Completable removeFavorite(String mealId);

    @Query("DELETE FROM favorites")
    Completable clearAllFavorites();
}