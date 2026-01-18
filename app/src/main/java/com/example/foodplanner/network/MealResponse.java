package com.example.foodplanner.network;

import com.example.foodplanner.data.models.Meal;

import java.util.List;

public class MealResponse {
     public List<Meal> MealList;
    public MealResponse(List<Meal> MealList)
    {
        this.MealList = MealList;
    }
}
