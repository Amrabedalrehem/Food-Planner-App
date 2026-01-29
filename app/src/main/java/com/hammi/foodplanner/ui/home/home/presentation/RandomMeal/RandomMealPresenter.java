package com.hammi.foodplanner.ui.home.home.presentation.RandomMeal;

import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.data.repository.meal.MealRepository;
import com.hammi.foodplanner.data.datasource.remote.meal.NetworkCallback;

public class RandomMealPresenter implements RandomMealContract.Presenter {
    private MealRepository mealRepository;
    private RandomMealContract.View view;

    public RandomMealPresenter(RandomMealContract.View view) {
        this.mealRepository = MealRepository.getInstance();
        this.view = view;
    }

    @Override
    public void getRandomMeal() {
        if (view == null) return;
        view.showLoading();
        mealRepository.getRandomMael(new NetworkCallback<Meal>() {
            @Override
            public void onSuccess(Meal data) {
                if (view != null) {
                    view.hideLoading();
                    view.showRandomMeal(data);
                };
            }
            @Override
            public void onError(String errorMessage) {
               if(view!=null) {
                   view.hideLoading();
                   view.showError(errorMessage);
               }
            }
        });
    }
}
