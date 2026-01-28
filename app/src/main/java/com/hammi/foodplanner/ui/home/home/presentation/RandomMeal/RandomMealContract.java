package com.hammi.foodplanner.ui.home.home.presentation.RandomMeal;
 import com.hammi.foodplanner.data.models.remote.Meal;

public class RandomMealContract {
    public interface View {
        void showRandomMeal(Meal meal);
        void showError(String message);
        void showLoading();
        void hideLoading();
    }

    public interface Presenter {
        void getRandomMeal();
    }
}

