package com.hammi.foodplanner.data.datasource.local.SharedPrefsManager;

import android.content.Context;
import android.content.SharedPreferences;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class SharedPrefsService {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPrefsService(Context context) {
        sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

     public Completable saveString(String key, String value) {
        return Completable.fromAction(() -> {
            editor.putString(key, value);
            editor.apply();
        });
    }

     public Single<String> getString(String key, String defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getString(key, defaultValue));
    }

    public Completable saveBoolean(String key, boolean value) {
        return Completable.fromAction(() -> {
            editor.putBoolean(key, value);
            editor.apply();
        });
    }

    public Single<Boolean> getBoolean(String key, boolean defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getBoolean(key, defaultValue));
    }

    public Completable clear() {
        return Completable.fromAction(() -> {
            editor.clear();
            editor.apply();
        });
    }
}