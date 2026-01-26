package com.example.foodplanner.data.repository.sharedprefs;

import android.content.Context;

import com.example.foodplanner.data.datasource.local.SharedPrefsDataSource;


public class SharedPrefsRepository {

    private SharedPrefsDataSource dataSource;

    public SharedPrefsRepository(Context context) {
        this.dataSource = new SharedPrefsDataSource(context);
    }

    public boolean checkFirstLaunch() {
        return dataSource.isFirstLaunch();
    }

    public void markOnboardingComplete() {
        dataSource.setFirstLaunch(false);
    }

    public boolean checkLoggedIn() {
        return dataSource.isLoggedIn();
    }

    public void saveLoginSession(String userId, String mode) {
        dataSource.setLoggedIn(true);
        dataSource.setUserId(userId);
        dataSource.setUserMode(mode);
    }

    public void logout() {
        dataSource.clearAllData();
    }

    public String getUserMode() {
        return dataSource.getUserMode();
    }

    public String getUserId() {
        return dataSource.getUserId();
    }
}