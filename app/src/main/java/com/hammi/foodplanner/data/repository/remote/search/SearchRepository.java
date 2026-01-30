package com.hammi.foodplanner.data.repository.remote.search;

import com.hammi.foodplanner.data.datasource.remote.search.SearchDataSource;
import com.hammi.foodplanner.data.models.remote.Category;
import com.hammi.foodplanner.data.models.remote.Countries;
import com.hammi.foodplanner.data.models.remote.Ingredients;
import com.hammi.foodplanner.data.models.remote.Meal;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class SearchRepository {

    private static SearchRepository searchRepository;
    private final SearchDataSource searchDataSource;

    private SearchRepository() {
        searchDataSource = SearchDataSource.getInstance();
    }

    public static synchronized SearchRepository getInstance() {
        if (searchRepository == null) {
            searchRepository = new SearchRepository();
        }
        return searchRepository;
    }

    public Single<List<Meal>> searchMealsByName(String query) {
        return searchDataSource.searchMealsByName(query);
    }

    public Single<List<Category>> getAllCategories() {
        return searchDataSource.getAllCategories();
    }

    public Single<List<Meal>> searchByCategory(String category) {
        return searchDataSource.searchByCategory(category);
    }

    public Single<List<Countries>> getAllCountries() {
        return searchDataSource.getAllCountries();
    }

    public Single<List<Meal>> searchByCountry(String country) {
        return searchDataSource.searchByCountry(country);
    }

    public Single<List<Ingredients>> getAllIngredients() {
        return searchDataSource.getAllIngredients();
    }

    public Single<List<Meal>> searchByIngredient(String ingredient) {
        return searchDataSource.searchByIngredient(ingredient);
    }

    public Single<List<Meal>> getMealDetails(String mealId) {
        return searchDataSource.getMealDetails(mealId);
    }
}