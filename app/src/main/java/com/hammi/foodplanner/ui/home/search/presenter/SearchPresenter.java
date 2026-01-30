package com.hammi.foodplanner.ui.home.search.presenter;

import com.hammi.foodplanner.data.datasource.remote.meal.NetworkCallback;
import com.hammi.foodplanner.data.models.remote.Category;
import com.hammi.foodplanner.data.models.remote.Countries;
import com.hammi.foodplanner.data.models.remote.Ingredients;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.data.repository.remote.search.SearchRepository;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View view;
    private SearchRepository repository;
    public SearchPresenter(SearchContract.View view) {
        this.view = view;
        this.repository = SearchRepository.getInstance();
    }
    @Override
    public void searchMealsByName(String query) {
         if (query == null || query.trim().isEmpty()) {
            view.showError("Please enter a search term");
            return;
        }
        view.showLoading();
        view.hideSubFilterChips();
        repository.searchMealsByName(query, new NetworkCallback<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                view.hideLoading();

                if (meals != null && !meals.isEmpty()) {
                    view.showMeals(meals);
                } else {
                    view.showEmptyState();
                }
            }

            @Override
            public void onError(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void loadCategories() {
        view.showLoading();
        repository.getAllCategories(new NetworkCallback<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                view.hideLoading();

                if (categories != null && !categories.isEmpty()) {
                    view.showCategories(categories);
                    view.showSubFilterChips();
                } else {
                    view.showError("No categories found");
                }
            }
            @Override
            public void onError(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void searchByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            view.showError("Please select a category");
            return;
        }
        view.showLoading();
        repository.searchByCategory(category, new NetworkCallback<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                view.hideLoading();

                if (meals != null && !meals.isEmpty()) {
                    view.showMeals(meals);
                } else {
                    view.showEmptyState();
                }
            }
            @Override
            public void onError(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void loadCountries() {
        view.showLoading();

        repository.getAllCountries(new NetworkCallback<List<Countries>>() {
            @Override
            public void onSuccess(List<Countries> countries) {
                view.hideLoading();

                if (countries != null && !countries.isEmpty()) {
                    view.showCountries(countries);
                    view.showSubFilterChips();
                } else {
                    view.showError("No countries found");
                }
            }
            @Override
            public void onError(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void searchByCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            view.showError("Please select a country");
            return;
        }

        view.showLoading();

        repository.searchByCountry(country, new NetworkCallback<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                view.hideLoading();

                if (meals != null && !meals.isEmpty()) {
                    view.showMeals(meals);
                } else {
                    view.showEmptyState();
                }
            }

            @Override
            public void onError(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);
            }
        });
    }
    @Override
    public void loadIngredients() {
        view.showLoading();

        repository.getAllIngredients(new NetworkCallback<List<Ingredients>>() {
            @Override
            public void onSuccess(List<Ingredients> ingredients) {
                view.hideLoading();

                if (ingredients != null && !ingredients.isEmpty()) {
                    view.showIngredients(ingredients);
                    view.showSubFilterChips();
                } else {
                    view.showError("No ingredients found");
                }
            }

            @Override
            public void onError(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void searchByIngredient(String ingredient) {
        if (ingredient == null || ingredient.trim().isEmpty()) {
            view.showError("Please select an ingredient");
            return;
        }

        view.showLoading();

        repository.searchByIngredient(ingredient, new NetworkCallback<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                view.hideLoading();

                if (meals != null && !meals.isEmpty()) {
                    view.showMeals(meals);
                } else {
                    view.showEmptyState();
                }
            }

            @Override
            public void onError(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);
            }
        });
    }
    @Override
    public void onDestroy() {
         this.view = null;
    }
}