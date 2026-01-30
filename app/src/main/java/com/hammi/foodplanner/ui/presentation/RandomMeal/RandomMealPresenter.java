package com.hammi.foodplanner.ui.presentation.RandomMeal;

import com.hammi.foodplanner.data.repository.remote.meal.MealRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RandomMealPresenter implements RandomMealContract.Presenter {
    private final MealRepository mealRepository;
    private RandomMealContract.View view;
     private final CompositeDisposable disposables = new CompositeDisposable();

    public RandomMealPresenter(RandomMealContract.View view) {
        this.mealRepository = MealRepository.getInstance();
        this.view = view;
    }

    @Override
    public void getRandomMeal() {
        if (view == null) return;

        view.showLoading();

        disposables.add(
                mealRepository.getRandomMeal()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                mealList -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        if (mealList != null && !mealList.isEmpty()) {
                                            view.showRandomMeal(mealList.get(0));
                                        }
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

    @Override
    public void detachView() {
        disposables.clear();
        this.view = null;
    }
}