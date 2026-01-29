package com.hammi.foodplanner.data.datasource.remote.home.catogories;

import com.hammi.foodplanner.data.models.remote.Category;

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
