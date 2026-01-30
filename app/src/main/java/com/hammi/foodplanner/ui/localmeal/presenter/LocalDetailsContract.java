package com.hammi.foodplanner.ui.localmeal.presenter;

import com.hammi.foodplanner.data.models.local.MealEntity;

public interface LocalDetailsContract {

    interface View {
        void showDetails(MealEntity meal);
        void showError(String message);
        void showLoading();
        void hideLoading();
    }

    interface Presenter {
        void loadMealDetails(String mealId);
        void onDestroy();
    }
}