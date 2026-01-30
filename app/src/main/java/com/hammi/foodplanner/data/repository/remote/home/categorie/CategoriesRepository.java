package com.hammi.foodplanner.data.repository.remote.home.categorie;

import com.hammi.foodplanner.data.datasource.remote.home.catogories.CategoryCallback;
import com.hammi.foodplanner.data.datasource.remote.home.catogories.CategoryDataSource;

public class CategoriesRepository {
    CategoryDataSource categoryDataSource;
  private static   CategoriesRepository categoriesRepository;

    private   CategoriesRepository()
    {
        categoryDataSource = CategoryDataSource.getInstance();
    }
    public  static synchronized  CategoriesRepository getInstance()
    {
        if(categoriesRepository ==null)
        {
            categoriesRepository = new CategoriesRepository();
        }
        return categoriesRepository;
    }

    public void getAllCategories(CategoryCallback callback)
    {

        categoryDataSource.getCategories(callback);


    }

}
