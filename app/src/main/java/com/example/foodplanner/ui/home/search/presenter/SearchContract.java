package com.example.foodplanner.ui.home.search.presenter;

import com.example.foodplanner.data.models.remote.Category;
import com.example.foodplanner.data.models.remote.Countries;
import com.example.foodplanner.data.models.remote.Ingredients;
import com.example.foodplanner.data.models.remote.Meal;

import java.util.List;


public class SearchContract {

    public interface View {
        void showMeals(List<Meal> meals);
        void showCategories(List<Category> categories);
        void showCountries(List<Countries> countries);
        void showIngredients(List<Ingredients> ingredients);
        void showError(String message);
        void showLoading();
        void hideLoading();
        void showEmptyState();
        void hideSubFilterChips();
        void showSubFilterChips();
    }

    public interface Presenter {
        void searchMealsByName(String query);
        void loadCategories();
        void searchByCategory(String category);
        void loadCountries();
        void searchByCountry(String country);
        void loadIngredients();
        void searchByIngredient(String ingredient);
        void onDestroy();
    }
}