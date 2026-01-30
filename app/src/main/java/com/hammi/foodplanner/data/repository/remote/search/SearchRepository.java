package com.hammi.foodplanner.data.repository.remote.search;

import com.hammi.foodplanner.data.datasource.remote.meal.NetworkCallback;
import com.hammi.foodplanner.data.datasource.remote.search.SearchDataSource;
import com.hammi.foodplanner.data.models.remote.Category;
import com.hammi.foodplanner.data.models.remote.Countries;
import com.hammi.foodplanner.data.models.remote.Ingredients;
import com.hammi.foodplanner.data.models.remote.Meal;

import java.util.List;

public class SearchRepository {

    private static SearchRepository searchRepository;
    private SearchDataSource searchDataSource;

    private SearchRepository() {
        searchDataSource = SearchDataSource.getInstance();
    }

    public static synchronized SearchRepository getInstance() {
        if (searchRepository == null) {
            searchRepository = new SearchRepository();
        }
        return searchRepository;
    }

    public void searchMealsByName(String query, NetworkCallback<List<Meal>> callback)
    {
        searchDataSource.searchMealsByName(query, callback);
    }
    public void getAllCategories(NetworkCallback<List<Category>> callback)
    {
        searchDataSource.getAllCategories(callback);
    }

    public void searchByCategory(String category, NetworkCallback<List<Meal>> callback) {
        searchDataSource.searchByCategory(category, callback);
    }

    public void getAllCountries(NetworkCallback<List<Countries>> callback)
    {
        searchDataSource.getAllCountries(callback);
    }

    public void searchByCountry(String country, NetworkCallback<List<Meal>> callback)
      {
        searchDataSource.searchByCountry(country, callback);
    }

    public void getAllIngredients(NetworkCallback<List<Ingredients>> callback)
      {
        searchDataSource.getAllIngredients(callback);
    }

    public void searchByIngredient(String ingredient, NetworkCallback<List<Meal>> callback) {
        searchDataSource.searchByIngredient(ingredient, callback);
       }

    public void getMealDetails(String mealId, NetworkCallback<List<Meal>> callback)
        {
        searchDataSource.getMealDetails(mealId, callback);
    }
}