package com.hammi.foodplanner.data.repository.remote.home.ingredients;

import com.hammi.foodplanner.data.datasource.remote.home.ingredients.IngredientsDataSource;
import com.hammi.foodplanner.data.models.remote.Ingredients;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class IngredientsRepository {

    private final IngredientsDataSource ingredientsDataSource;
    private static IngredientsRepository ingredientsRepository;

    private IngredientsRepository() {
        ingredientsDataSource = IngredientsDataSource.getInstance();
    }

    public static synchronized IngredientsRepository getInstance() {
        if (ingredientsRepository == null) {
            ingredientsRepository = new IngredientsRepository();
        }
        return ingredientsRepository;
    }
     public Single<List<Ingredients>> getAllIngredients() {
        return ingredientsDataSource.getAllIngredients();
    }
}