package com.example.foodplanner.ui.splash.presenter;

public interface SplashContract {

    interface View {
            void navigateToOnboarding();
            void navigateToAuthentication();
            void navigateToHome();
        }

        interface Presenter {
            void checkUserStatus();
            void onAnimationComplete();
            void  onDestroy();
         }
    }