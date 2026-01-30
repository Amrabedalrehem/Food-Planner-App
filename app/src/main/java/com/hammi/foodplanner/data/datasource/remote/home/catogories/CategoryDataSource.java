package com.hammi.foodplanner.data.datasource.remote.home.catogories;

import com.hammi.foodplanner.data.models.remote.Category;
import com.hammi.foodplanner.data.datasource.remote.meal.MealService;
import com.hammi.foodplanner.network.Network;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class CategoryDataSource {
    private static CategoryDataSource instance;
    private final MealService mealService;

    private CategoryDataSource() {
         mealService = Network.getInstance().mealService;
    }

    public static synchronized CategoryDataSource getInstance() {
        if (instance == null) {
            instance = new CategoryDataSource();
        }
        return instance;
    }

     public Single<List<Category>> getCategories() {
        return mealService.getMealCategories()
                .map(response -> response.getCategories());
    }
}