package com.example.foodplanner.data.repository.details;

import com.example.foodplanner.data.datasource.remote.NetworkCallback;
import com.example.foodplanner.data.datasource.remote.home.catogories.CategoryCallback;
import com.example.foodplanner.data.datasource.remote.home.catogories.CategoryDataSource;
import com.example.foodplanner.data.datasource.remote.home.details.detailsDataSource;
import com.example.foodplanner.data.models.Meal;
import com.example.foodplanner.data.repository.categorie.CategoriesRepository;

import java.util.List;


public class DetailsRepository
 {
private static DetailsRepository detailsRepository;
     detailsDataSource dataSource;
private   DetailsRepository()
{
    dataSource =detailsDataSource.getInstance();
}
public  static synchronized  DetailsRepository getInstance()
{
    if(detailsRepository ==null)
    {
        detailsRepository = new DetailsRepository();
    }
    return detailsRepository;
}

public void getDetails(String id, NetworkCallback<List<Meal>> callback)
{
    dataSource.getDetails(id,callback);


}

}
