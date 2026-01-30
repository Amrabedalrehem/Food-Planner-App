package com.hammi.foodplanner.data.datasource.local.SharedPrefsManager;

import android.content.Context;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class SharedPrefsDataSource {

    private final SharedPrefsService service;

    public SharedPrefsDataSource(Context context) {
        this.service = new SharedPrefsService(context);
    }

    public Completable setFirstLaunch(boolean isFirstLaunch) {
        return service.saveBoolean("isFirstLaunch", isFirstLaunch);
    }

    public Single<Boolean> isFirstLaunch() {
        return service.getBoolean("isFirstLaunch", true);
    }

    public Completable setLoggedIn(boolean isLoggedIn) {
        return service.saveBoolean("isLoggedIn", isLoggedIn);
    }

    public Single<Boolean> isLoggedIn() {
        return service.getBoolean("isLoggedIn", false);
    }

    public Completable setUserMode(String mode) {
        return service.saveString("userMode", mode);
    }

    public Single<String> getUserMode() {
        return service.getString("userMode", "guest");
    }

    public Completable setUserId(String userId) {
        return service.saveString("userId", userId);
    }

    public Single<String> getUserId() {
        return service.getString("userId", null);
    }

     public Completable clearAllData() {
        return isFirstLaunch()
                .flatMapCompletable(wasFirstLaunch ->
                        service.clear()
                                .andThen(setFirstLaunch(wasFirstLaunch))
                );
    }
}