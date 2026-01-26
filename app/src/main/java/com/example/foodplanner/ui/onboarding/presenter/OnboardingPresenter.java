package com.example.foodplanner.ui.onboarding.presenter;

import android.content.Context;
import com.example.foodplanner.data.repository.sharedprefs.SharedPrefsRepository;

public class OnboardingPresenter implements OnboardingContract.Presenter {

    private OnboardingContract.View view;
    private SharedPrefsRepository repository;

    public OnboardingPresenter(OnboardingContract.View view, Context context) {
        this.view = view;
         this.repository = new SharedPrefsRepository(context.getApplicationContext());
    }

    @Override
    public void completeOnboarding() {
         OnboardingContract.View currentView = view;
        if (currentView == null) return;
         repository.markOnboardingComplete();
         currentView.navigateToAuthentication();
    }

    @Override
    public void onDestroy() {
        view = null;
        repository = null;
    }
}