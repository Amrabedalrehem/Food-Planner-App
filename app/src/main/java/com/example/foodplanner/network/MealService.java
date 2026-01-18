package com.example.foodplanner.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface MealService {

     @GET("search.php")
    Call<MealResponse> searchMealByName(@Query("s") String searchMealName);

     @GET("search.php")
    Call<MealResponse> searchMealByFirstLetter(@Query("f") String searchFirstLetter);

     @GET("lookup.php")
    Call<MealResponse> getMealDetailsById(@Query("i") String id);

     @GET("random.php")
    Call<MealResponse> getRandomMeal();

     @GET("randomselection.php")
    Call<MealResponse> getRandomSelection();

     @GET("categories.php")
    Call<CategoryResponse> getMealCategories();

     @GET("latest.php")
    Call<MealResponse> getLatestMeals();


    @GET("list.php")
    Call<MealResponse> getListAllCategories(@Query("c") String listKeyword);

    @GET("list.php")
    Call<MealResponse> getListAllArea(@Query("a") String listKeyword);

    @GET("list.php")
    Call<MealResponse> getListAllIngredients(@Query("i") String listKeyword);

     @GET("filter.php")
    Call<MealResponse> filterByMainIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<MealResponse> filterByMultiIngredient(@Query("i") String multiIngredients);

    @GET("filter.php")
    Call<MealResponse> filterByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealResponse> filterByArea(@Query("a") String area);
}