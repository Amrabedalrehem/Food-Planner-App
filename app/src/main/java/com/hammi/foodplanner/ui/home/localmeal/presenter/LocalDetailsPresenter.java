package com.hammi.foodplanner.ui.home.localmeal.presenter;
import android.content.Context;
import com.hammi.foodplanner.data.repository.local.meal.LocalMealRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocalDetailsPresenter implements LocalDetailsContract.Presenter {

    private LocalDetailsContract.View view;
    private LocalMealRepository repository;
    private CompositeDisposable disposables;

    public LocalDetailsPresenter(LocalDetailsContract.View view, Context context) {
        this.view = view;
        this.repository = LocalMealRepository.getInstance(context);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void loadMealDetails(String mealId) {
        if (view == null) return;
        view.showLoading();
        disposables.add(
                repository.getMealById(mealId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(meal -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showDetails(meal);
                                    }
                                },
                                error -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showError(error.getMessage());}
                                }));
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        view = null;
    }
}