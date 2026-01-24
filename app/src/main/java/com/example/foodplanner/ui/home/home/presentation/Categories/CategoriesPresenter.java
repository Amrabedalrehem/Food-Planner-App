package com.example.foodplanner.ui.home.home.presentation.Categories;

import com.example.foodplanner.data.datasource.remote.home.catogories.CategoryCallback;
import com.example.foodplanner.data.models.Category;
import com.example.foodplanner.data.repository.categorie.CategoriesRepository;

import java.util.List;

public class CategoriesPresenter  implements  CategoriesContract.Presenter {
    public CategoriesContract.View view;
    public CategoriesRepository categoriesRepository;

    public CategoriesPresenter(CategoriesContract.View view) {
        categoriesRepository = CategoriesRepository.getInstance();
        this.view = view;
    }

    @Override
    public void getCategories() {
        if (view == null) return;
        view.showLoading();
        categoriesRepository.getAllCategories(new CategoryCallback() {
            @Override
            public void onSuccess(List<Category> categoryList) {
                if (view != null) {
                    view.hideLoading();
                    view.showCategories(categoryList);
                }
            }

            @Override
            public void onError(String errorMessage) {
                if (view != null) {
                    view.hideLoading();
                    view.showError(errorMessage);
                }
            }
        });
    }
}