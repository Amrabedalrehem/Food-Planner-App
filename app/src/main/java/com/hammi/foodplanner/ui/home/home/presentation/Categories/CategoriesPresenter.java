package com.hammi.foodplanner.ui.home.home.presentation.Categories;

import com.hammi.foodplanner.data.repository.remote.home.categorie.CategoriesRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class CategoriesPresenter implements CategoriesContract.Presenter {
    private CategoriesContract.View view;
    private final CategoriesRepository categoriesRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public CategoriesPresenter(CategoriesContract.View view) {
        this.categoriesRepository = CategoriesRepository.getInstance();
        this.view = view;
    }
    @Override
    public void getCategories() {
        if (view == null) return;

        view.showLoading();

        disposable.add(
                categoriesRepository.getAllCategories()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                categoryList -> {
                                    view.hideLoading();
                                    view.showCategories(categoryList);
                                },
                                throwable -> {
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

     public void detachView() {
        disposable.clear();
        this.view = null;
    }
}