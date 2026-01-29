package com.hammi.foodplanner.ui.splash.presenter;

import android.content.Context;
import com.hammi.foodplanner.data.repository.sharedprefs.SharedPrefsRepository;

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View view;
    private SharedPrefsRepository repository;

    public SplashPresenter(SplashContract.View view, Context context) {
        this.view = view;
         this.repository = new SharedPrefsRepository(context.getApplicationContext());
    }

    @Override
    public void checkUserStatus() {
         SplashContract.View currentView = view;
        if (currentView == null) return;
        if (repository.checkFirstLaunch()) {
            currentView.navigateToOnboarding();
        }
        else if (!repository.checkLoggedIn()) {
            currentView.navigateToAuthentication();
        }
        else
        {
            currentView.navigateToHome();
        }
    }

    @Override
    public void onAnimationComplete() {
        checkUserStatus();
        onDestroy();
    }

    @Override
    public void onDestroy() {
        view = null;
        repository = null;
    }
}