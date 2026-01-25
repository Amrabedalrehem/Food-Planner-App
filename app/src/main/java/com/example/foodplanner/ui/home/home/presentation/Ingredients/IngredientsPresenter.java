package com.example.foodplanner.ui.home.home.presentation.Ingredients;

import com.example.foodplanner.data.datasource.remote.NetworkCallback;
import com.example.foodplanner.data.models.Ingredients;
import com.example.foodplanner.data.repository.home.ingredients.IngredientsRepository;

import java.util.List;

public class IngredientsPresenter  implements  IngredientsContract.Presenter{
    IngredientsContract.View view;
    IngredientsRepository ingredientsRepository;
   public IngredientsPresenter(IngredientsContract.View view)
    {
        ingredientsRepository = IngredientsRepository.getInstance();
        this.view =view;
    }

    @Override
    public void getIngredients() {
        if (view == null) return;
        view.showLoading();
       ingredientsRepository.getAllIngredients(new NetworkCallback<List<Ingredients>>() {
           @Override
           public void onSuccess(List<Ingredients> data) {
               if (view != null) {
                   view.hideLoading();
                   view.showAllIngredients(data);
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
