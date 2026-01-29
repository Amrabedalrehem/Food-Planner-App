package com.hammi.foodplanner.ui.home.planner.presenter;

import android.content.Context;

import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.repository.local.mealplan.MealPlanRepository;

import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPlanPresenter implements MealPlanContract.Presenter {

    private MealPlanContract.View view;
    private MealPlanRepository repository;
    private CompositeDisposable disposables;

    public MealPlanPresenter(MealPlanContract.View view, Context context) {
        this.view = view;
        this.repository = MealPlanRepository.getInstance(context);
        this.disposables = new CompositeDisposable();
    }



    @Override
    public void loadMealsForDate(int year, int month, int day) {
        if (view == null) return;

        view.showLoading();
        long[] dayRange = getDayRange(year, month, day);
        long startOfDay = dayRange[0];
        long endOfDay = dayRange[1];

        disposables.add(repository.getMealsForDate(startOfDay, endOfDay)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                                this::handleMealsLoaded,
                                this::handleError
                        )
        );
    }

    private void handleMealsLoaded(List<MealEntity> meals) {
        if (view != null) {
            view.hideLoading();
            if (meals.isEmpty()) {
                view.showEmptyState();
            } else {
                view.showMealsForDate(meals);
            }
        }
    }

    @Override
    public void loadMealPlanEntriesForDate(int year, int month, int day) {
        if (view == null) return;
        long[] dayRange = getDayRange(year, month, day);
        long startOfDay = dayRange[0];
        long endOfDay = dayRange[1];

        disposables.add(
                repository.getMealPlanEntriesForDate(startOfDay, endOfDay)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                entries -> {
                                    if (view != null) {
                                        view.showMealPlanEntries(entries);
                                    }
                                },
                                this::handleError
                        )
        );
    }

    @Override
    public void addMealToPlan(MealEntity meal, int year, int month, int day) {
         long dateTimestamp = getDateTimestamp(year, month, day);

        disposables.add(
                repository.addMealToPlan(meal, dateTimestamp)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    if (view != null) {
                                        view.showMealAddedSuccess();
                                         loadMealsForDate(year, month, day);
                                    }
                                },
                                this::handleError
                        )
        );
    }

    @Override
    public void removeMealFromPlan(int planId) {
        disposables.add(
                repository.removeMealFromPlan(planId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    if (view != null) {
                                        view.showMealRemovedSuccess();
                                    }
                                },
                                this::handleError
                        )
        );
    }


    @Override
    public void loadWeekMealsCount(long startOfWeek, long endOfWeek) {
        disposables.add(
                repository.getWeekMealsCount(startOfWeek, endOfWeek)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                count -> {
                                    if (view != null) {
                                        view.showWeekMealsCount(count);
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



    private long getDateTimestamp(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 12, 0, 0);  // 12:00 PM
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private long[] getDayRange(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();

         calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startOfDay = calendar.getTimeInMillis();
        calendar.set(year, month, day, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long endOfDay = calendar.getTimeInMillis();

        return new long[]{startOfDay, endOfDay};
    }
}