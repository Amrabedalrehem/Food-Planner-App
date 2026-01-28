package com.hammi.foodplanner.ui.home.home.presentation.Details;

import com.hammi.foodplanner.data.models.remote.Meal;

import java.util.List;



public interface DetailsContract {
    public interface View{
        void showDetails(List<Meal> details);
        void showError(String message);
        void showLoading();
        void hideLoading();
    }
    public interface Presenter{
        void getDetails();
    }

}

