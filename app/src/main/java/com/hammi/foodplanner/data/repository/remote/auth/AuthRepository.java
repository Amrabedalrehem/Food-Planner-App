package com.hammi.foodplanner.data.repository.remote.auth;
import android.app.Activity;
import android.content.Context;
import com.hammi.foodplanner.data.datasource.local.SharedPrefsManager.SharedPrefsDataSource;
import com.hammi.foodplanner.data.datasource.remote.auth.FirebaseAuthHelper;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthRepository {
    private final FirebaseAuthHelper firebaseAuthHelper;
    private final SharedPrefsDataSource sharedPrefsDataSource;

    public AuthRepository(Context context) {
        firebaseAuthHelper = FirebaseAuthHelper.getInstance(context);
        sharedPrefsDataSource = new SharedPrefsDataSource(context);
    }

     public Single<FirebaseUser> signInWithGoogle(Activity activity, String webClientId) {
        return firebaseAuthHelper.startGoogleSignIn(activity, webClientId)
                .doOnSuccess(user -> saveSession(user, "google"));
    }

    public Single<FirebaseUser> registerWithEmail(String email, String password) {
        return firebaseAuthHelper.registerWithEmail(email, password)
                .doOnSuccess(user -> saveSession(user, "email"));
    }

    public Single<FirebaseUser> loginWithEmail(String email, String password) {
        return firebaseAuthHelper.loginWithEmail(email, password)
                .doOnSuccess(user -> saveSession(user, "email"));
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

     public Completable signOut() {
        return firebaseAuthHelper.signOut()
                .doOnComplete(sharedPrefsDataSource::clearAllData);
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuthHelper.getCurrentUser();
    }
}