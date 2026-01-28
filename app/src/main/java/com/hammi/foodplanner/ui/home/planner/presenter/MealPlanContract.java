package com.hammi.foodplanner.ui.home.planner.presenter;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.local.MealPlanEntity;

import java.util.List;

public interface MealPlanContract {

    interface View {

        void showMealsForDate(List<MealEntity> meals);
        void showMealPlanEntries(List<MealPlanEntity> planEntries);
        void showEmptyState();
        void showWeekMealsCount(int count);
        void showMealAddedSuccess();
        void showMealRemovedSuccess();
        void showError(String message);
        void showLoading();
        void hideLoading();
    }

    interface Presenter {
        void loadMealsForDate(int year, int month, int day);
        void loadMealPlanEntriesForDate(int year, int month, int day);
        void addMealToPlan(MealEntity meal, int year, int month, int day);
        void removeMealFromPlan(int planId);
        void loadWeekMealsCount(long startOfWeek, long endOfWeek);
        void onDestroy();
    }
}