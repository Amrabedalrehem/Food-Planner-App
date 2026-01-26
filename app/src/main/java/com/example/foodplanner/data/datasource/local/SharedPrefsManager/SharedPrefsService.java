package com.example.foodplanner.data.datasource.local.SharedPrefsManager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsService {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public SharedPrefsService(Context context) {
        sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

     public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

     public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

     public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

     public void clear() {
          editor.clear();
          editor.apply();
    }
}