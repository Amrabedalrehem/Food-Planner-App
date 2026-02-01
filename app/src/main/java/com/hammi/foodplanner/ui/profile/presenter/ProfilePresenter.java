package com.hammi.foodplanner.ui.profile.presenter;

import com.hammi.foodplanner.data.repository.remote.auth.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View view;
    private AuthRepository repository;

    public ProfilePresenter(ProfileContract.View view, AuthRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadUserData() {
        FirebaseUser user = repository.getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName() != null ? user.getDisplayName() : "User";
            String email = user.getEmail();
            view.displayUserData(name, email);
        }
    }



    @Override
    public void logout() {
        if (view == null) return;
        repository.signOut()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.navigateToLogin(),
                        throwable -> {
                             view.showError(throwable.getMessage());
                        }
                );
    }

}