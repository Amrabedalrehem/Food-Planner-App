package com.hammi.foodplanner.ui.presentation.Ingredients;

import com.hammi.foodplanner.data.models.remote.Ingredients;

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
       void detachView();
    }
}
