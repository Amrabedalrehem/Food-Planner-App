package com.hammi.foodplanner.data.datasource;

import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.google.gson.Gson;

public class MealMapper {

    private static final Gson gson = new Gson();


    public static MealEntity toEntity(Meal meal) {
        if (meal == null) {
            return null;
        }

        MealEntity entity = new MealEntity();
        entity.setMealId(meal.getIdMeal());
        entity.setName(meal.getStrMeal());
        entity.setCategory(meal.getStrCategory());
        entity.setArea(meal.getStrArea());
        entity.setInstructions(meal.getStrInstructions());
        entity.setThumbnailUrl(meal.getStrMealThumb());
        entity.setYoutubeUrl(meal.getStrYoutube());
        String ingredientsJson = gson.toJson(meal.getFullIngredients());
        entity.setIngredientsJson(ingredientsJson);

        return entity;
    }


    public static Meal fromEntity(MealEntity entity) {
        return null;
    }
}