package com.example.foodplanner.ui.home.home.presentation.Countries;

import com.example.foodplanner.data.datasource.remote.NetworkCallback;
import com.example.foodplanner.data.models.Countries;
import com.example.foodplanner.data.repository.home.countries.CountriesRepository;
import java.util.List;
public class CountriesPresenter implements  CountriesContract.Presenter{
     CountriesRepository countriesRepository;
     CountriesContract.View view;
   public CountriesPresenter(CountriesContract.View view){

       countriesRepository  = CountriesRepository.getInstance();
       this.view =view;
   }

    @Override
    public void getCountries() {
        if (view == null) return;
        view.showLoading();
       countriesRepository.getAllCountries(new NetworkCallback<List<Countries>>() {

           @Override
           public void onSuccess(List<Countries> data) {

               if (view != null) {
                   view.hideLoading();
                   view.showCountries(data);
               }

           }

           @Override
           public void onError(String errorMessage) {
               if (view != null) {
                   view.hideLoading();
                   view.showError(errorMessage);
               }
           }
       });

    }
}
