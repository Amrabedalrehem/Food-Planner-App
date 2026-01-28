package com.hammi.foodplanner.data.datasource.remote.meal;

import com.hammi.foodplanner.data.models.remote.Meal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {
    @SerializedName("meals")
     private List<Meal> meals;
     public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
