package com.hammi.foodplanner.ui.presentation.Details;

import com.hammi.foodplanner.data.models.remote.Meal;

public interface DetailsContract {

    interface View {

        void showDetails(Meal meal);
        void showError(String message);
        void showLoading();
        void hideLoading();
        void showFavoriteAdded();
        void showFavoriteRemoved();
        void updateFavoriteButton(boolean isFavorite);
        void showAddToPlanDialog();
        void showMealAddedToPlan();
        void showMealPlanError(String message);
    }

    interface Presenter {
        void getDetails(String id);
        void toggleFavorite();
        void onAddToPlanClicked();
        void addMealToPlan(int year, int month, int day);
        void onDestroy();
    }
}