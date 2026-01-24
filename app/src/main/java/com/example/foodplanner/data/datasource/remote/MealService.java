package com.example.foodplanner.data.datasource.remote;

import com.example.foodplanner.data.datasource.remote.home.catogories.CategoryResponse;
import com.example.foodplanner.data.datasource.remote.home.countries.CountriesResponse;
import com.example.foodplanner.data.datasource.remote.home.ingredients.IngredientsResponse;
import com.example.foodplanner.data.datasource.remote.home.meal.MealResponse;
import com.example.foodplanner.data.models.Ingredients;

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

}


 /*
 lookup.php?i=52772
   @GET("randomselection.php")
    Call<ListOfMealResponse> getRandomSelection();

   @GET("latest.php")
    Call<ListOfMealResponse> getLatestMeals();

     @GET("search.php")
    Call<ListOfMealResponse> searchMealByFirstLetter(@Query("f") String searchFirstLetter);



    @GET("list.php")
    Call<ListOfMealResponse> getListAllCategories(@Query("c") String listKeyword);





     @GET("filter.php")
    Call<ListOfMealResponse> filterByMainIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<ListOfMealResponse> filterByMultiIngredient(@Query("i") String multiIngredients);

    @GET("filter.php")
    Call<ListOfMealResponse> filterByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<ListOfMealResponse> filterByArea(@Query("a") String area);
    * */