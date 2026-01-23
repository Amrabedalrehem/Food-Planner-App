package com.example.foodplanner.data.repository.ingredients;

import com.example.foodplanner.data.datasource.remote.NetworkCallback;
import com.example.foodplanner.data.datasource.remote.home.ingredients.IngredientsDataSource;
import com.example.foodplanner.data.models.Ingredients;

import java.util.List;

public class IngredientsRepository {

    IngredientsDataSource ingredientsDataSource;

    public static IngredientsRepository ingredientsRepository;

   private IngredientsRepository ()
    {
        ingredientsDataSource = IngredientsDataSource.getInstance();
    }
    public  static  IngredientsRepository  getInstance()
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
