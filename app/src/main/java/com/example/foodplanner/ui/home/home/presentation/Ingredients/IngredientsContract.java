package com.example.foodplanner.ui.home.home.presentation.Ingredients;

import com.example.foodplanner.data.models.remote.Ingredients;

import java.util.List;

public interface IngredientsContract {
    public interface View{
        void showAllIngredients(List<Ingredients> ingredients);
        void showError(String message);
        void showLoading();
        void hideLoading();
    }
    public interface Presenter{
        void getIngredients();
    }
}
