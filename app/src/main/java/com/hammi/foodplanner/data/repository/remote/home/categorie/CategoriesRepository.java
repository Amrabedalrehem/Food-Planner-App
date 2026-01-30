package com.hammi.foodplanner.data.repository.remote.home.categorie;

import com.hammi.foodplanner.data.datasource.remote.home.catogories.CategoryDataSource;
import com.hammi.foodplanner.data.models.remote.Category;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class CategoriesRepository {
    private final CategoryDataSource categoryDataSource;
    private static CategoriesRepository categoriesRepository;

    private CategoriesRepository() {
        categoryDataSource = CategoryDataSource.getInstance();
    }

    public static synchronized CategoriesRepository getInstance() {
        if (categoriesRepository == null) {
            categoriesRepository = new CategoriesRepository();
        }
        return categoriesRepository;
    }

     public Single<List<Category>> getAllCategories() {
        return categoryDataSource.getCategories();
    }
}