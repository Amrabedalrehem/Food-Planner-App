package com.hammi.foodplanner.data.datasource.remote.home.catogories;

import com.hammi.foodplanner.data.models.remote.Category;

import java.util.List;

public interface CategoryCallback {
    void onSuccess(List<Category>categoryList);
    void onError(String errorMessage);
}
