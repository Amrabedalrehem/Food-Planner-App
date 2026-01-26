package com.example.foodplanner.ui.auth.presenter;

import android.app.Activity;

import com.example.foodplanner.data.datasource.remote.auth.AuthCallback;
import com.example.foodplanner.data.repository.auth.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthPresenter implements AuthContract.Presenter {

    private AuthContract.View view;
    private final AuthRepository authRepository;

    public AuthPresenter(AuthContract.View view, AuthRepository authRepository) {
        this.view = view;
        this.authRepository = authRepository;
    }

    @Override
    public void signInWithGoogle(Activity activity, String webClientId) {
        if (view == null) return;
        view.showLoading();

        authRepository.signInWithGoogle(activity, webClientId, new AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                if (view != null) {
                    view.hideLoading();
                    view.navigateToHome();
                }
            }

            @Override
            public void onError(String message) {
                if (view != null) {
                    view.hideLoading();
                    view.showError(message);
                }
            }


        });
    }

    @Override
    public void registerWithEmailAndPassword(String email, String password) {
        if(view == null) return;
        view.showLoading();

        authRepository.registerWithEmail(email, password, new AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                if (view != null) {
                    view.hideLoading();
                    view.navigateToHome();
                }
            }

            @Override
            public void onError(String message) {
                if (view != null) {
                    view.hideLoading();
                    view.showError(message);
                }
            }


        });
    }

    @Override
    public void loginWithEmailAndPassword(String email, String password) {
        if(view == null) return;
        view.showLoading();

        authRepository.loginWithEmail(email, password, new AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                if (view != null) {
                    view.hideLoading();
                    view.navigateToHome();
                }
            }

            @Override
            public void onError(String message) {
                if (view != null) {
                    view.hideLoading();
                    view.showError(message);
                }
            }


        });
    }

    @Override
    public void loginAsGuest() {
        if (view == null) return;
        authRepository.saveGuestSession();
        view.navigateToHome();
    }

    @Override
    public void detachView() {
        view = null;
    }
}