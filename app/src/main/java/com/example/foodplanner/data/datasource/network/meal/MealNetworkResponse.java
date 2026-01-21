package com.example.foodplanner.data.datasource.network.meal;

import com.example.foodplanner.data.models.Meal;
import java.util.List;

public interface MealNetworkResponse {

    void onSuccess(List<Meal> productList);
    void onError(String errorMessage);
}
