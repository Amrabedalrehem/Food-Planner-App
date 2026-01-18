package com.example.foodplanner.network;

import com.example.foodplanner.data.models.Category;

import java.util.List;

public class CategoryResponse {
    private List<Category> categoryList;

    public CategoryResponse(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
