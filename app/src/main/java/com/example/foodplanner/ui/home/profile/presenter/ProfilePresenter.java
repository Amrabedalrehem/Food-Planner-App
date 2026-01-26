package com.example.foodplanner.ui.home.profile.presenter;

import com.example.foodplanner.data.repository.auth.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

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
        repository.signOut();
        view.navigateToLogin();
    }
}