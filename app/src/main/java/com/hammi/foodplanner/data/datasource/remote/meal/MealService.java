package com.hammi.foodplanner.data.datasource.remote.meal;

import com.hammi.foodplanner.data.datasource.remote.home.catogories.CategoryResponse;
import com.hammi.foodplanner.data.datasource.remote.home.countries.CountriesResponse;
import com.hammi.foodplanner.data.datasource.remote.home.ingredients.IngredientsResponse;
 import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("search.php")
    Single<MealResponse> searchMealByName(@Query("s") String searchMealName);

    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    @GET("categories.php")
    Single<CategoryResponse> getMealCategories();

    @GET("list.php")
    Single<CountriesResponse> getListALLCountries(@Query("a") String countries);

    @GET("list.php")
    Single<IngredientsResponse> getListAllIngredients(@Query("i") String strIngredient);

    @GET("lookup.php")
    Single<MealResponse> getMealDetailsById(@Query("i") String id);

    @GET("randomselection.php")
    Single<MealResponse> getRandomSelection();

    @GET("latest.php")
    Single<MealResponse> getLatestMeals();

    @GET("search.php")
    Single<MealResponse> searchMealByFirstLetter(@Query("f") String searchFirstLetter);

    @GET("list.php")
    Single<MealResponse> getListAllCategories(@Query("c") String listKeyword);

    @GET("filter.php")
    Single<MealResponse> filterByMainIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Single<MealResponse> filterByMultiIngredient(@Query("i") String multiIngredients);

    @GET("filter.php")
    Single<MealResponse> filterByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealResponse> filterByArea(@Query("a") String area);
}