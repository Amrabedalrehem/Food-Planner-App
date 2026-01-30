package com.hammi.foodplanner.data.repository.sharedprefs;

import android.content.Context;
import com.hammi.foodplanner.data.datasource.local.SharedPrefsManager.SharedPrefsDataSource;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class SharedPrefsRepository {

    private final SharedPrefsDataSource dataSource;

    public SharedPrefsRepository(Context context) {
        this.dataSource = new SharedPrefsDataSource(context);
    }

    public Single<Boolean> checkFirstLaunch() {
        return dataSource.isFirstLaunch();
    }

    public Completable markOnboardingComplete() {
        return dataSource.setFirstLaunch(false);
    }

    public Single<Boolean> checkLoggedIn() {
        return dataSource.isLoggedIn();
    }

     public Completable saveLoginSession(String userId, String mode) {
        return dataSource.setLoggedIn(true)
                .andThen(dataSource.setUserId(userId))
                .andThen(dataSource.setUserMode(mode));
    }

    public Completable logout() {
        return dataSource.clearAllData();
    }

    public Single<String> getUserMode() {
        return dataSource.getUserMode();
    }

    public Single<String> getUserId() {
        return dataSource.getUserId();
    }
}