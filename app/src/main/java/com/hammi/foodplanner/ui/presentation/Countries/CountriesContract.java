package com.hammi.foodplanner.ui.presentation.Countries;

import com.hammi.foodplanner.data.models.remote.Countries;

import java.util.List;

public interface CountriesContract {
   public interface View{
       void showCountries(List<Countries> countries);
       void showError(String message);
       void showLoading();
       void hideLoading();
   }
    public interface Presenter{
        void getCountries();
    }

}

