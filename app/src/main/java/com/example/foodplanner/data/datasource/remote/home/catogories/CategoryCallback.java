package com.example.foodplanner.data.datasource.remote.home.catogories;

import com.example.foodplanner.data.models.remote.Category;

import java.util.List;

public interface CategoryCallback {
    void onSuccess(List<Category>categoryList);
    void onError(String errorMessage);
}
