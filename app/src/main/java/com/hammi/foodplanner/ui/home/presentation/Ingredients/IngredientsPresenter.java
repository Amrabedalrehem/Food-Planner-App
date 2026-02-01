package com.hammi.foodplanner.ui.home.presentation.Ingredients;

import com.hammi.foodplanner.data.repository.remote.home.ingredients.IngredientsRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class IngredientsPresenter implements IngredientsContract.Presenter {
    private IngredientsContract.View view;
    private final IngredientsRepository ingredientsRepository;
     private final CompositeDisposable disposables = new CompositeDisposable();

    public IngredientsPresenter(IngredientsContract.View view) {
        this.ingredientsRepository = IngredientsRepository.getInstance();
        this.view = view;
    }
    @Override
    public void getIngredients() {
        if (view == null) return;
        view.showLoading();
        disposables.add(
                ingredientsRepository.getAllIngredients()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ingredientsList -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showAllIngredients(ingredientsList);
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