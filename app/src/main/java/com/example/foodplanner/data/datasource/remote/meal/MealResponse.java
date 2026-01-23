package com.example.foodplanner.data.datasource.remote.meal;

import com.example.foodplanner.data.models.Meal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {
@SerializedName("meals")
public List<Meal> MealList;
    public MealResponse(List<Meal> MealList)
    {
        this.MealList = MealList;
    }
}
