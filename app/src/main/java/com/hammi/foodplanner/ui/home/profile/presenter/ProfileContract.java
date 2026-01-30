package com.hammi.foodplanner.ui.home.profile.presenter;

public interface ProfileContract {
    interface View {
        void displayUserData(String name, String email);
        void navigateToLogin();
       void showError(String message);
    }
    interface Presenter {
        void loadUserData();
        void logout();
    }
}