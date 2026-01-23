package com.example.foodplanner.data.datasource.remote.catogories;

import com.example.foodplanner.data.models.Category;

import java.util.List;

public class CategoryResponse {
    private List<Category> categories;
    public CategoryResponse(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
