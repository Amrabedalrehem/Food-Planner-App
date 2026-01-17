package com.example.foodplanner.ui.auth;

import static android.widget.Toast.*;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.repository.auth.AuthRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthPresenter implements AuthContract.Presenter {
    private FirebaseAuth mAuth;
    private AuthContract.View view;
    private AuthRepository authRepository;

    public AuthPresenter(AuthContract.View view) {
        this.view = view;
        this.authRepository = new AuthRepository();
        this.mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void loginWithGoogle(String idToken) {
        AuthCredential credential =
                GoogleAuthProvider.getCredential(idToken, null);

        authRepository.loginWithGoogle(credential, new AuthRepository.Callback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                view.showWelcomeMessage(user.getDisplayName());
                view.navigateToHome();
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });
    }

    @Override
    public void registerWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                         view.navigateToHome();
                    } else {
                        view.showError(task.getException().getMessage());
                    }
                });

    }

    @Override
    public void loginWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                         view.navigateToHome();
                    } else {
                        view.showError(task.getException().getMessage());
                    }
                });

    }

}
