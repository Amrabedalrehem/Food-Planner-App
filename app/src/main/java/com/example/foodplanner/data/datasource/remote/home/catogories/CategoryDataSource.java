package com.example.foodplanner.data.datasource.remote.home.catogories;
import com.example.foodplanner.data.models.Category;
import com.example.foodplanner.data.datasource.remote.MealService;
import com.example.foodplanner.network.Network;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class CategoryDataSource {
    private static CategoryDataSource instance;
    private MealService mealService;

     private CategoryDataSource() {
        mealService = Network.getInstance().mealService;
    }

     public static synchronized CategoryDataSource getInstance() {
        if (instance == null) {
            instance = new CategoryDataSource();
        }
        return instance;
    }


    public void getCategories(CategoryCallback callback) {
        mealService.getMealCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Category> categories = response.body().getCategories();
                    callback.onSuccess(categories);
                } else {
                    callback.onError("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}

