package com.example.foodplanner.ui.home.profile.presenter;

public interface ProfileContract {
    interface View {
        void displayUserData(String name, String email);
        void navigateToLogin();
    }
    interface Presenter {
        void loadUserData();
        void logout();
    }
}