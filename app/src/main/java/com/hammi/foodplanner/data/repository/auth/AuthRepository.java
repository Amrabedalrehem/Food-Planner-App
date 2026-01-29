package com.hammi.foodplanner.data.repository.auth;
import android.app.Activity;
import android.content.Context;

import com.hammi.foodplanner.data.datasource.local.SharedPrefsManager.SharedPrefsDataSource;
import com.hammi.foodplanner.data.datasource.remote.auth.AuthCallback;
import com.hammi.foodplanner.data.datasource.remote.auth.FirebaseAuthHelper;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {
    private FirebaseAuthHelper firebaseAuthHelper;
    private SharedPrefsDataSource sharedPrefsDataSource;
    public AuthRepository(Context context) {
        firebaseAuthHelper = FirebaseAuthHelper.getInstance(context);
         sharedPrefsDataSource = new SharedPrefsDataSource(context);
    }
    public void signInWithGoogle(Activity activity, String webClientId, AuthCallback callback) {
        firebaseAuthHelper.startGoogleSignIn(activity, webClientId, new AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                 saveSession(user, "google");
                callback.onSuccess(user);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

    public void registerWithEmail(String email, String password, AuthCallback callback) {
        firebaseAuthHelper.registerWithEmail(email, password, new AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                saveSession(user, "email");
                callback.onSuccess(user);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

    public void loginWithEmail(String email, String password, AuthCallback callback) {
        firebaseAuthHelper.loginWithEmail(email, password, new AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                saveSession(user, "email");
                callback.onSuccess(user);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

     private void saveSession(FirebaseUser user, String mode) {
        sharedPrefsDataSource.setLoggedIn(true);
        sharedPrefsDataSource.setUserId(user.getUid());
        sharedPrefsDataSource.setUserMode(mode);
    }

    public void saveGuestSession() {
        sharedPrefsDataSource.setLoggedIn(false);
        sharedPrefsDataSource.setUserMode("guest");
    }

    public void signOut() {
        firebaseAuthHelper.signOut();
        sharedPrefsDataSource.clearAllData();
     }

    public FirebaseUser getCurrentUser() {
        return firebaseAuthHelper.getCurrentUser();
    }
}