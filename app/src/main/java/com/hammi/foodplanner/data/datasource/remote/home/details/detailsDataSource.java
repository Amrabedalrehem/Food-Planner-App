package com.hammi.foodplanner.data.datasource.remote.home.details;

import com.hammi.foodplanner.data.datasource.remote.meal.MealService;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.network.Network;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class detailsDataSource {
    private static detailsDataSource dataSource;
    private final MealService mealService;

    private detailsDataSource() {
         mealService = Network.getInstance().mealService;
    }

    public static synchronized detailsDataSource getInstance() {
        if (dataSource == null) {
            dataSource = new detailsDataSource();
        }
        return dataSource;
    }

     public Single<List<Meal>> getDetails(String id) {
        return mealService.getMealDetailsById(id)
                .map(response -> {
                    if (response.getMeals() != null && !response.getMeals().isEmpty()) {
                        return response.getMeals();
                    } else {
                         throw new Exception("No meals found for ID: " + id);
                    }
                });
    }
}