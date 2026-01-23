package com.example.foodplanner.data.repository.categorie;

import com.example.foodplanner.data.datasource.remote.catogories.CategoryCallback;
import com.example.foodplanner.data.datasource.remote.catogories.CategoryDataSource;

public class CategoriesRepository {
    CategoryDataSource categoryDataSource;
  private static   CategoriesRepository categoriesRepository;

    private   CategoriesRepository()
    {
        categoryDataSource = CategoryDataSource.getInstance();
    }
    public  static  CategoriesRepository getInstance()
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
