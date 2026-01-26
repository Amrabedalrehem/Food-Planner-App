package com.example.foodplanner.ui.onboarding.presenter;


public interface OnboardingContract {

    interface View {
        void navigateToAuthentication();
    }

    interface Presenter {
        void completeOnboarding();
        void onDestroy();
    }
}
