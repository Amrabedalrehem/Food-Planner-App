package com.hammi.foodplanner.ui.home.home.presentation.Countries;

import com.hammi.foodplanner.data.repository.remote.home.countries.CountriesRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CountriesPresenter implements CountriesContract.Presenter {
    private final CountriesRepository countriesRepository;
    private CountriesContract.View view;
     private final CompositeDisposable disposable = new CompositeDisposable();

    public CountriesPresenter(CountriesContract.View view) {
        this.countriesRepository = CountriesRepository.getInstance();
        this.view = view;
    }

    @Override
    public void getCountries() {
        if (view == null) return;

        view.showLoading();

        disposable.add(
                countriesRepository.getAllCountries()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                countriesList -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showCountries(countriesList);
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showError(throwable.getMessage());
                                    }
                                }
                        )
        );
    }

     public void detachView() {
        disposable.clear();
        this.view = null;
    }
}