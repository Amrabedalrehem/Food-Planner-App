package com.hammi.foodplanner.data.repository.remote.home.ingredients;

import com.hammi.foodplanner.data.datasource.remote.meal.NetworkCallback;
import com.hammi.foodplanner.data.datasource.remote.home.ingredients.IngredientsDataSource;
import com.hammi.foodplanner.data.models.remote.Ingredients;

import java.util.List;

public class IngredientsRepository {

    IngredientsDataSource ingredientsDataSource;

    public static IngredientsRepository ingredientsRepository;

   private IngredientsRepository ()
    {
        ingredientsDataSource = IngredientsDataSource.getInstance();
    }
    public  static synchronized  IngredientsRepository  getInstance()
    {
        if(null == ingredientsRepository)
        {
            ingredientsRepository = new IngredientsRepository();
        return  ingredientsRepository;
        }
        return  ingredientsRepository;
    }

    public void getAllIngredients(NetworkCallback<List<Ingredients>>callback)
    {
        ingredientsDataSource.getAllIngredients(callback);
    }



}
