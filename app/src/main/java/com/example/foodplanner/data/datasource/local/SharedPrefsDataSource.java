package com.example.foodplanner.data.datasource.local;

import android.content.Context;

import com.example.foodplanner.data.datasource.local.SharedPrefsManager.SharedPrefsService;

public class SharedPrefsDataSource {


    private SharedPrefsService service;

    public SharedPrefsDataSource(Context context) {
        this.service = new SharedPrefsService(context);
    }

     public void setFirstLaunch(boolean isFirstLaunch) {
        service.saveBoolean("isFirstLaunch", isFirstLaunch);
    }

    public boolean isFirstLaunch() {
        return service.getBoolean("isFirstLaunch", true);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        service.saveBoolean("isLoggedIn", isLoggedIn);
    }

    public boolean isLoggedIn() {
        return service.getBoolean("isLoggedIn", false);
    }

     public void setUserMode(String mode) {
        service.saveString("userMode", mode);
    }

    public String getUserMode() {
        return service.getString("userMode", "guest");
    }

     public void setUserId(String userId) {
        service.saveString("userId", userId);
    }

    public String getUserId() {
        return service.getString("userId", null);
    }

     public void clearAllData() {
         boolean wasFirstLaunch = isFirstLaunch();
        service.clear();
         setFirstLaunch(wasFirstLaunch);
    }
}