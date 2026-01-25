package com.example.foodplanner.data.repository.search;

import com.example.foodplanner.data.datasource.remote.NetworkCallback;
import com.example.foodplanner.data.datasource.remote.search.SearchDataSource;
import com.example.foodplanner.data.models.Category;
import com.example.foodplanner.data.models.Countries;
import com.example.foodplanner.data.models.Ingredients;
import com.example.foodplanner.data.models.Meal;

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