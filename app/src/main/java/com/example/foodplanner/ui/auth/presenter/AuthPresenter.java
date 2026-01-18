package com.example.foodplanner.ui.auth.presenter;

import com.example.foodplanner.data.repository.auth.AuthRepository;
import com.google.firebase.auth.AuthCredential;
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
