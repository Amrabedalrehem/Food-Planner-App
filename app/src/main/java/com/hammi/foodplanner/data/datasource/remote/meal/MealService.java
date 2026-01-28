package com.hammi.foodplanner.data.datasource.remote.meal;

import com.hammi.foodplanner.data.datasource.remote.home.catogories.CategoryResponse;
import com.hammi.foodplanner.data.datasource.remote.home.countries.CountriesResponse;
import com.hammi.foodplanner.data.datasource.remote.home.ingredients.IngredientsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface MealService {

     @GET("search.php")
    Call<MealResponse> searchMealByName(@Query("s") String searchMealName);
     @GET("random.php")
    Call<MealResponse> getRandomMeal();
     @GET("categories.php")
    Call<CategoryResponse> getMealCategories();
    @GET("list.php")
    Call<CountriesResponse> getListALLCountries(@Query("a") String countries);
    @GET("list.php")
    Call<IngredientsResponse> getListAllIngredients(@Query("i") String strIngredient);
    @GET("lookup.php")
    Call<MealResponse> getMealDetailsById(@Query("i") String id);
    @GET("randomselection.php")
    Call<MealResponse> getRandomSelection();
    @GET("latest.php")
    Call<MealResponse> getLatestMeals();
    @GET("search.php")
    Call<MealResponse> searchMealByFirstLetter(@Query("f") String searchFirstLetter);
    @GET("list.php")
    Call<MealResponse> getListAllCategories(@Query("c") String listKeyword);
    @GET("filter.php")
    Call<MealResponse> filterByMainIngredient(@Query("i") String ingredient);
    @GET("filter.php")
    Call<MealResponse> filterByMultiIngredient(@Query("i") String multiIngredients);
    @GET("filter.php")
    Call<MealResponse> filterByCategory(@Query("c") String category);
    @GET("filter.php")
    Call<MealResponse> filterByArea(@Query("a") String area);



}
