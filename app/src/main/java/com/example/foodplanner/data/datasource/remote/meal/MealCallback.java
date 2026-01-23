package com.example.foodplanner.data.datasource.remote.meal;

import com.example.foodplanner.data.models.Meal;
import java.util.List;

public interface MealCallback {

    void onSuccess(List<Meal> productList);
    void onError(String errorMessage);
}
