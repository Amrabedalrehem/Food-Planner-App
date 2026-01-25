package com.example.foodplanner.data.datasource.remote.home.details;

import com.example.foodplanner.data.datasource.remote.MealService;
import com.example.foodplanner.data.datasource.remote.NetworkCallback;
import com.example.foodplanner.data.datasource.remote.MealResponse;
import com.example.foodplanner.data.models.Meal;
import com.example.foodplanner.network.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detailsDataSource {
   public static detailsDataSource dataSource;
    MealService mealService;
    private detailsDataSource()
    {
        mealService = Network.instance.mealService;
    }
   public static synchronized detailsDataSource  getInstance()
   {
       if(dataSource ==null)
       {
           dataSource = new detailsDataSource();
       return  dataSource;
       }
       return  dataSource;
   }

   public  void  getDetails(String id,NetworkCallback<List<Meal>> callback){
       mealService.getMealDetailsById(id).enqueue(new Callback<MealResponse>() {
           @Override
           public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
               if (response.isSuccessful()
                       && response.body() != null
                       && response.body().getMeals() != null
                       && !response.body().getMeals().isEmpty()) {

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

}
