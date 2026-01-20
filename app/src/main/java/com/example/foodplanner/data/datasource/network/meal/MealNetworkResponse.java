package com.example.foodplanner.data.datasource.network;

import com.example.foodplanner.data.models.Meal;
import com.example.mvp.data.product.model.Product;

import java.util.List;

public interface MealNetworkResponse {

    void onSuccess(List<Meal> productList);
    void onError(String errorMessage);
}
