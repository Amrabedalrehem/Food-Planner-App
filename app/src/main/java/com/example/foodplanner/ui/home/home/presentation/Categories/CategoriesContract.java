package com.example.foodplanner.ui.home.home.presentation.Categories;

import com.example.foodplanner.data.models.Category;

import java.util.List;
public interface CategoriesContract {
    interface View {
        void showCategories(List<Category> categories);
        void showError(String message);
        void showLoading();
        void hideLoading();
    }

    interface Presenter {
        void getCategories();
    }
}
