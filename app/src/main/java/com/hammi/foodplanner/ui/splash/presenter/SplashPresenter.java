package com.hammi.foodplanner.ui.splash.presenter;

import android.content.Context;
import com.hammi.foodplanner.data.repository.sharedprefs.SharedPrefsRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View view;
    private SharedPrefsRepository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SplashPresenter(SplashContract.View view, Context context) {
        this.view = view;
        this.repository = new SharedPrefsRepository(context.getApplicationContext());
    }

    @Override
    public void checkUserStatus() {
        if (view == null) return;

         disposables.add(
                repository.checkFirstLaunch()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isFirstLaunch -> {
                            if (view == null) return;

                            if (isFirstLaunch) {
                                view.navigateToOnboarding();
                            } else {
                                 checkLoginStatus();
                            }
                        }, throwable -> {
                            if (view != null) view.navigateToAuthentication();
                        })
        );
    }

    private void checkLoginStatus() {
        disposables.add(
                repository.checkLoggedIn()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isLoggedIn -> {
                            if (view == null) return;

                            if (isLoggedIn) {
                                view.navigateToHome();
                            } else {
                                view.navigateToAuthentication();
                            }
                        }, throwable -> {
                            if (view != null) view.navigateToAuthentication();
                        })
        );
    }

    @Override
    public void onAnimationComplete() {
        checkUserStatus();
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        view = null;
        repository = null;
    }
}