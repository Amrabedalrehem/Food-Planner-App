package com.example.foodplanner.data.repository.auth;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public interface Callback {
        void onSuccess(FirebaseUser user);
        void onError(String message);
    }
    public void loginWithGoogle(AuthCredential credential, Callback callback) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(firebaseAuth.getCurrentUser());
                    } else {
                        callback.onError("Firebase Auth Failed");
                    }
                });
    }
}
