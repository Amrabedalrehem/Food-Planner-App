package com.hammi.foodplanner.ui.home.presentation.Categories;

import com.hammi.foodplanner.data.models.remote.Category;

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
