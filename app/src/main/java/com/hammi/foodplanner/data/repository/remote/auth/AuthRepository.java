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
                .flatMap(user -> saveSessionRx(user, "google")
                        .andThen(Single.just(user))
                );
    }

     public Single<FirebaseUser> registerWithEmail(String email, String password) {
        return firebaseAuthHelper.registerWithEmail(email, password)
                .flatMap(user -> saveSessionRx(user, "email")
                        .andThen(Single.just(user))
                );
    }

     public Single<FirebaseUser> loginWithEmail(String email, String password) {
        return firebaseAuthHelper.loginWithEmail(email, password)
                .flatMap(user -> saveSessionRx(user, "email")
                        .andThen(Single.just(user))
                );
    }

     private Completable saveSessionRx(FirebaseUser user, String mode) {
        return sharedPrefsDataSource.setLoggedIn(true)
                .andThen(sharedPrefsDataSource.setUserId(user.getUid()))
                .andThen(sharedPrefsDataSource.setUserMode(mode));
      }

     public Completable saveGuestSessionRx() {
        return sharedPrefsDataSource.setLoggedIn(false)
                .andThen(sharedPrefsDataSource.setUserMode("guest"));
    }

     public Completable signOut() {
        return firebaseAuthHelper.signOut()
                .andThen(sharedPrefsDataSource.clearAllData());
    }

     public FirebaseUser getCurrentUser() {
        return firebaseAuthHelper.getCurrentUser();
    }
}
