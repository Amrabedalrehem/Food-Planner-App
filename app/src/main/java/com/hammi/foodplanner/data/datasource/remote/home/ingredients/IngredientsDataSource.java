package com.hammi.foodplanner.data.datasource.remote.home.ingredients;

import com.hammi.foodplanner.data.datasource.remote.meal.MealService;
import com.hammi.foodplanner.data.models.remote.Ingredients;
import com.hammi.foodplanner.network.Network;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class IngredientsDataSource {
    private final MealService mealService;
    private static IngredientsDataSource ingredientsDataSource;

    private IngredientsDataSource() {
        mealService = Network.getInstance().mealService;
    }

    public static synchronized IngredientsDataSource getInstance() {
        if (ingredientsDataSource == null) {
            ingredientsDataSource = new IngredientsDataSource();
        }
        return ingredientsDataSource;
    }

     public Single<List<Ingredients>> getAllIngredients() {
         return mealService.getListAllIngredients("list")
                .map(response -> response.getIngredients());
    }
}