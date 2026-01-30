package com.hammi.foodplanner.network;

import com.hammi.foodplanner.data.datasource.remote.meal.MealService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

   public MealService mealService ;
    private static final String URL = "https://www.themealdb.com/api/json/v1/1/";
    public static  Network instance = null ;
    private Network(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }


    public  static synchronized Network getInstance(){

        if (instance == null){

            instance = new Network();
        }
        return instance;
    }

}
