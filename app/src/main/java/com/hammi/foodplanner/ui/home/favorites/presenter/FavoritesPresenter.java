package com.hammi.foodplanner.ui.home.favorites.presenter;
import android.content.Context;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.repository.local.favorites.FavoritesRepository;

import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritesPresenter implements FavoritesContract.Presenter {

    private FavoritesContract.View view;
    private FavoritesRepository repository;
    private CompositeDisposable disposables;

    public FavoritesPresenter(FavoritesContract.View view, Context context) {
        this.view = view;
        this.repository = FavoritesRepository.getInstance(context);
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void loadFavorites() {
        if (view == null) return;
        view.showLoading();
        disposables.add(repository.getAllFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleFavoritesLoaded, this::handleError)
        );
    }
    private void handleFavoritesLoaded(List<MealEntity> favorites) {
        if (view != null) {
            view.hideLoading();
            if (favorites.isEmpty()) {
                view.showEmptyState();
            } else {
                view.showFavorites(favorites);
            }
        }
    }

    @Override
    public void removeFavorite(String mealId) {
        disposables.add(
                repository.removeFromFavorites(mealId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                                    if (view != null) {
                                        view.showMealRemovedSuccess();
                                    }
                                },
                        this::handleError
                        )
        );
    }

    private void handleError(Throwable error) {
        if (view != null) {
            view.hideLoading();
            view.showError(error.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        view = null;
    }
}
/*
* throwable -> {
            handleError(throwable);
        }
*
* */