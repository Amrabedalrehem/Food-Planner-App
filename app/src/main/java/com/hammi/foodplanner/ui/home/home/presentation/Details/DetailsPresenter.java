package com.hammi.foodplanner.ui.home.home.presentation.Details;

import android.content.Context;

import com.hammi.foodplanner.data.datasource.remote.meal.NetworkCallback;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.data.repository.home.details.DetailsRepository;

import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsPresenter implements DetailsContract.Presenter {

    private DetailsContract.View view;
    private DetailsRepository repository;
    private CompositeDisposable disposables;
    private Meal currentMeal;

    public DetailsPresenter(DetailsContract.View view, Context context) {
        this.view = view;
        this.repository = DetailsRepository.getInstance(context);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void getDetails(String id) {
        if (view == null) return;
        view.showLoading();
        repository.getDetails(id, new NetworkCallback<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> data) {
                if (view != null && !data.isEmpty()) {
                    view.hideLoading();
                    currentMeal = data.get(0);
                    view.showDetails(currentMeal);
                    checkIfFavorite(currentMeal.getIdMeal());
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

    @Override
    public void toggleFavorite() {
        if (currentMeal == null) return;
        disposables.add(repository.isFavorite(currentMeal.getIdMeal())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isFavorite -> {
                                    if (isFavorite) {
                                        removeFavorite(currentMeal.getIdMeal());
                                    } else {
                                        addFavorite(currentMeal);
                                    }
                                },
                                error -> {
                                    if (view != null) {
                                        view.showError(error.getMessage());
                                    }
                                }
                        )
        );
    }

    private void addFavorite(Meal meal) {
        disposables.add(
                repository.addToFavorites(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {if (view != null) {
                                        view.showFavoriteAdded();
                                        view.updateFavoriteButton(true);}
                                },
                                error -> {
                                    if (view != null) {
                                        view.showError(error.getMessage());
                                    }}));
    }

    private void removeFavorite(String mealId) {
        disposables.add(repository.removeFromFavorites(mealId).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {if (view != null) {
                                        view.showFavoriteRemoved();
                                        view.updateFavoriteButton(false);}
                                },
                                error -> {
                                    if (view != null) {
                                        view.showError(error.getMessage());
                                    }
                                }
                        )
        );
    }

    private void checkIfFavorite(String mealId) {
        disposables.add(
                repository.isFavorite(mealId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isFavorite -> {
                            if (view != null) {
                                        view.updateFavoriteButton(isFavorite);
                                    }},
                                error -> {
                                    view.showError(error.getMessage());
                                }));
    }

    @Override
    public void onAddToPlanClicked() {
        if (view != null) {
            view.showAddToPlanDialog();
        }
    }

    @Override
    public void addMealToPlan(int year, int month, int day) {
        if (currentMeal == null) return;
        long timestamp = getDateTimestamp(year, month, day);
        disposables.add(
                repository.addMealToPlan(currentMeal, timestamp)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            if (view != null) {
                                        view.showMealAddedToPlan();}
                                },
                                error -> {
                                    if (view != null) {
                                        view.showMealPlanError(error.getMessage());
                                    }
                                }));
    }


    private long getDateTimestamp(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 12, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


    @Override
    public void onDestroy() {
        disposables.clear();
        view = null;
    }
}