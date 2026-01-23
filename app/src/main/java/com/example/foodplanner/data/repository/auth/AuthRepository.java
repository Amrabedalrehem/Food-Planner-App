package com.example.foodplanner.data.repository.auth;
import android.app.Activity;
import android.content.Context;

import com.example.foodplanner.data.datasource.remote.auth.AuthCallback;
import com.example.foodplanner.data.datasource.remote.auth.FirebaseAuthHelper;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {
    FirebaseAuthHelper firebaseAuthHelper;
     public AuthRepository(Context context) {
        firebaseAuthHelper = FirebaseAuthHelper.getInstance(context);
        }


    public void signInWithGoogle(Activity activity, String webClientId, AuthCallback callback) {
        firebaseAuthHelper.startGoogleSignIn(activity,webClientId, callback);
    }

    public  void  registerWithEmail(String email, String password, AuthCallback callback)
    {
        firebaseAuthHelper.registerWithEmail(email,password,callback);
    }

 public  void  loginWithEmail(String email, String password, AuthCallback callback)
 {
     firebaseAuthHelper.loginWithEmail(email,password,callback);
 }

 public  FirebaseUser  getCurrentUser()
 {
     return firebaseAuthHelper.getCurrentUser();
 }

 public  void signOut()
 {
     firebaseAuthHelper.signOut();
 }


}