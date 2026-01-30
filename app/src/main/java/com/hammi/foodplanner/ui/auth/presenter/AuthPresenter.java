package com.hammi.foodplanner.ui.auth.presenter;

import android.app.Activity;
import com.hammi.foodplanner.data.repository.remote.auth.AuthRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AuthPresenter implements AuthContract.Presenter {

    private AuthContract.View view;
    private final AuthRepository authRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AuthPresenter(AuthContract.View view, AuthRepository authRepository) {
        this.view = view;
        this.authRepository = authRepository;
    }

     @Override
    public void signInWithGoogle(Activity activity, String webClientId) {
        if (view == null) return;
        view.showLoading();
        compositeDisposable.add(
                authRepository.signInWithGoogle(activity, webClientId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.navigateToHome();
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showError(throwable.getMessage());
                                    }
                                }
                        )
        );
    }

     @Override
    public void registerWithEmailAndPassword(String email, String password) {
        if (view == null) return;
        view.showLoading();
        compositeDisposable.add(
                authRepository.registerWithEmail(email, password)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.navigateToHome();
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showError(throwable.getMessage());
                                    }
                                }
                        )
        );
    }

     @Override
    public void loginWithEmailAndPassword(String email, String password) {
        if (view == null) return;
        view.showLoading();
        compositeDisposable.add(
                authRepository.loginWithEmail(email, password)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.navigateToHome();
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showError(throwable.getMessage());
                                    }
                                }
                        )
        );
    }

     @Override
    public void loginAsGuest() {
        if (view == null) return;

        view.showLoading();

        compositeDisposable.add(
                authRepository.saveGuestSessionRx()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.navigateToHome();
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showError(throwable.getMessage());
                                    }
                                }
                        )
        );
    }

     @Override
    public void detachView() {
        compositeDisposable.clear();
        view = null;
    }
}
