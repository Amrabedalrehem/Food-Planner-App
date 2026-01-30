package com.hammi.foodplanner.data.datasource.remote.search;

import com.hammi.foodplanner.data.datasource.remote.meal.MealService;
import com.hammi.foodplanner.data.models.remote.Category;
import com.hammi.foodplanner.data.models.remote.Countries;
import com.hammi.foodplanner.data.models.remote.Ingredients;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.network.Network;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class SearchDataSource {

    private final MealService mealService;
    private static SearchDataSource searchDataSource;

    private SearchDataSource() {
        mealService = Network.getInstance().mealService;
    }

    public static synchronized SearchDataSource getInstance() {
        if (searchDataSource == null) {
            searchDataSource = new SearchDataSource();
        }
        return searchDataSource;
    }


    public Single<List<Meal>> searchMealsByName(String query) {
        return mealService.searchMealByName(query).map(response -> response.getMeals());
    }

    public Single<List<Category>> getAllCategories() {
        return mealService.getMealCategories().map(response -> response.getCategories());
    }

    public Single<List<Meal>> searchByCategory(String category) {
        return mealService.filterByCategory(category).map(response -> response.getMeals());
    }

    public Single<List<Countries>> getAllCountries() {
        return mealService.getListALLCountries("list").map(response -> response.getCountries());
    }

    public Single<List<Meal>> searchByCountry(String country) {
        return mealService.filterByArea(country).map(response -> response.getMeals());
    }

    public Single<List<Ingredients>> getAllIngredients() {
        return mealService.getListAllIngredients("list").map(response -> response.getIngredients());
    }

    public Single<List<Meal>> searchByIngredient(String ingredient) {
        return mealService.filterByMainIngredient(ingredient).map(response -> response.getMeals());
    }

    public Single<List<Meal>> getMealDetails(String mealId) {
        return mealService.getMealDetailsById(mealId).map(response -> response.getMeals());
    }
}