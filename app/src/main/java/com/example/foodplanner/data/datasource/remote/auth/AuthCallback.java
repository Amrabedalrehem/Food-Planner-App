package com.example.foodplanner.data.datasource.remote.auth;

import com.google.firebase.auth.FirebaseUser;

public interface AuthCallback {

    void onSuccess(FirebaseUser user);
    void onError(String message);
 }