package com.example.foodplanner.data.datasource.remote.home.ingredients;

import com.example.foodplanner.data.datasource.remote.MealService;
import com.example.foodplanner.data.datasource.remote.NetworkCallback;
import com.example.foodplanner.data.models.Ingredients;
import com.example.foodplanner.network.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientsDataSource {
MealService mealService ;
public  static  IngredientsDataSource ingredientsDataSource;

 private   IngredientsDataSource()
    {
        mealService = Network.getInstance().mealService;
    }
   public static synchronized IngredientsDataSource getInstance()
    {
        if(ingredientsDataSource ==null)
        {
            ingredientsDataSource = new IngredientsDataSource();
            return ingredientsDataSource;
        }
        return  ingredientsDataSource;
    }



    public  void  getAllIngredients(NetworkCallback<List<Ingredients>> callback)
    {
        mealService.getListAllIngredients("list").enqueue(
                new Callback<IngredientsResponse>() {
                    @Override
                    public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            callback.onSuccess(response.body().getIngredients());
                        } else {
                            callback.onError("No meals found or server error");
                        }
                    }
                    @Override
                    public void onFailure(Call<IngredientsResponse> call, Throwable t) {
                        callback.onError("Network error: " + t.getMessage());
                    }
                }
        );
    }


}
