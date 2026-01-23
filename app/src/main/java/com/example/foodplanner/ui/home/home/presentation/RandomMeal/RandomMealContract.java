package com.example.foodplanner.ui.home.home.presentation.RandomMeal;
 import com.example.foodplanner.data.models.Meal;
import java.util.List;

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

