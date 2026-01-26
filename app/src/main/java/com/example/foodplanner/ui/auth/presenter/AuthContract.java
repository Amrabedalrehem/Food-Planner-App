package com.example.foodplanner.ui.auth.presenter;

import android.app.Activity;

public interface AuthContract {

    interface View {
        void showWelcomeMessage(String name);
        void showError(String message);
        void navigateToHome();
        void openSignUp();
        void openSignIn();
        void showLoading();
        void hideLoading();
    }

    interface Presenter {
        void signInWithGoogle(Activity activity, String webClientId);
        void registerWithEmailAndPassword(String email, String password);
        void loginWithEmailAndPassword(String email, String password);
         void loginAsGuest();
        void detachView();
    }
}
