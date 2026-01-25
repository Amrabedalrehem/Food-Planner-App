package com.example.foodplanner.data.datasource.remote.search;
import com.example.foodplanner.data.datasource.remote.MealResponse;
import com.example.foodplanner.data.datasource.remote.MealService;
import com.example.foodplanner.data.datasource.remote.NetworkCallback;
import com.example.foodplanner.data.datasource.remote.home.catogories.CategoryResponse;
import com.example.foodplanner.data.datasource.remote.home.countries.CountriesResponse;
import com.example.foodplanner.data.datasource.remote.home.ingredients.IngredientsResponse;
 import com.example.foodplanner.data.models.Category;
import com.example.foodplanner.data.models.Countries;
import com.example.foodplanner.data.models.Ingredients;
import com.example.foodplanner.data.models.Meal;
import com.example.foodplanner.network.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDataSource {

    private MealService mealService;
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


    public void searchMealsByName(String query, NetworkCallback<List<Meal>> callback) {
        mealService.searchMealByName(query).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMeals());
                } else {
                    callback.onError("No meals found");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }



    public void getAllCategories(NetworkCallback<List<Category>> callback) {
        mealService.getMealCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getCategories());
                } else {
                    callback.onError("Failed to load categories");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    public void searchByCategory(String category, NetworkCallback<List<Meal>> callback) {
        mealService.filterByCategory(category).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMeals());
                } else {
                    callback.onError("No meals found in this category");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }


    public void getAllCountries(NetworkCallback<List<Countries>> callback) {
        mealService.getListALLCountries("list").enqueue(new Callback<CountriesResponse>() {
            @Override
            public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getCountries());
                } else {
                    callback.onError("Failed to load countries");
                }
            }

            @Override
            public void onFailure(Call<CountriesResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    public void searchByCountry(String country, NetworkCallback<List<Meal>> callback) {
        mealService.filterByArea(country).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMeals());
                } else {
                    callback.onError("No meals found from this country");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }


    public void getAllIngredients(NetworkCallback<List<Ingredients>> callback) {
        mealService.getListAllIngredients("list").enqueue(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getIngredients());
                } else {
                    callback.onError("Failed to load ingredients");
                }
            }

            @Override
            public void onFailure(Call<IngredientsResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    public void searchByIngredient(String ingredient, NetworkCallback<List<Meal>> callback) {
        mealService.filterByMainIngredient(ingredient).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMeals());
                } else {
                    callback.onError("No meals found with this ingredient");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }


    public void getMealDetails(String mealId, NetworkCallback<List<Meal>> callback) {
        mealService.getMealDetailsById(mealId).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMeals());
                } else {
                    callback.onError("Failed to load meal details");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
}