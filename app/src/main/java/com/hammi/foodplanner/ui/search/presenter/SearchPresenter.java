package com.hammi.foodplanner.ui.search.presenter;

import com.hammi.foodplanner.data.repository.remote.search.SearchRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View view;
    private final SearchRepository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
        this.repository = SearchRepository.getInstance();
    }

    @Override
    public void searchMealsByName(String query) {
        if (query == null || query.trim().isEmpty()) {
            view.showError("Please enter a search term");
            return;
        }
        view.showLoading();
        view.hideSubFilterChips();

        disposables.add(
                repository.searchMealsByName(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    view.hideLoading();
                                    if (meals != null && !meals.isEmpty()) {
                                        view.showMeals(meals);
                                    } else {
                                        view.showEmptyState();
                                    }
                                },
                                throwable -> {
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void loadCategories() {
        view.showLoading();
        disposables.add(
                repository.getAllCategories()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                categories -> {
                                    view.hideLoading();
                                    if (categories != null && !categories.isEmpty()) {
                                        view.showCategories(categories);
                                        view.showSubFilterChips();
                                    } else {
                                        view.showError("No categories found");
                                    }
                                },
                                throwable -> {
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void searchByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            view.showError("Please select a category");
            return;
        }
        view.showLoading();
        disposables.add(
                repository.searchByCategory(category)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    view.hideLoading();
                                    if (meals != null && !meals.isEmpty()) {
                                        view.showMeals(meals);
                                    } else {
                                        view.showEmptyState();
                                    }
                                },
                                throwable -> {
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void loadCountries() {
        view.showLoading();
        disposables.add(
                repository.getAllCountries()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                countries -> {
                                    view.hideLoading();
                                    if (countries != null && !countries.isEmpty()) {
                                        view.showCountries(countries);
                                        view.showSubFilterChips();
                                    } else {
                                        view.showError("No countries found");
                                    }
                                },
                                throwable -> {
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void searchByCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            view.showError("Please select a country");
            return;
        }
        view.showLoading();
        disposables.add(
                repository.searchByCountry(country)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    view.hideLoading();
                                    if (meals != null && !meals.isEmpty()) {
                                        view.showMeals(meals);
                                    } else {
                                        view.showEmptyState();
                                    }
                                },
                                throwable -> {
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void loadIngredients() {
        view.showLoading();
        disposables.add(
                repository.getAllIngredients()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ingredients -> {
                                    view.hideLoading();
                                    if (ingredients != null && !ingredients.isEmpty()) {
                                        view.showIngredients(ingredients);
                                        view.showSubFilterChips();
                                    } else {
                                        view.showError("No ingredients found");
                                    }
                                },
                                throwable -> {
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void searchByIngredient(String ingredient) {
        if (ingredient == null || ingredient.trim().isEmpty()) {
            view.showError("Please select an ingredient");
            return;
        }
        view.showLoading();
        disposables.add(
                repository.searchByIngredient(ingredient)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    view.hideLoading();
                                    if (meals != null && !meals.isEmpty()) {
                                        view.showMeals(meals);
                                    } else {
                                        view.showEmptyState();
                                    }
                                },
                                throwable -> {
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        this.view = null;
    }
}