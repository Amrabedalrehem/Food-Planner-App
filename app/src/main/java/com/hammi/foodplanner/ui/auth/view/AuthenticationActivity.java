package com.hammi.foodplanner.ui.auth.view;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.ui.auth.presenter.AuthContract;
import com.hammi.foodplanner.ui.auth.presenter.AuthPresenter;
import com.hammi.foodplanner.data.repository.remote.auth.AuthRepository;
import com.hammi.foodplanner.ui.home.HomeActivity;


public class AuthenticationActivity extends AppCompatActivity implements AuthContract.View {
     private ImageButton btmface;
     private ImageButton btmtwiter;
    private TextView textTitle, textSubTitle;
    private AuthContract.Presenter presenter;
    private LottieAnimationView lottieLoading;
    private View loadingOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        btmface = findViewById(R.id.btmface);
        btmtwiter = findViewById(R.id.btmTwiter);

        loadingOverlay = findViewById(R.id.loadingOverlay);
        lottieLoading = findViewById(R.id.lottieLoading);
        textTitle = findViewById(R.id.textTitle);
        textSubTitle = findViewById(R.id.textSubTitle);

        presenter = new AuthPresenter(this, new AuthRepository(this));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new SignInFragment())
                    .commit();
        }

        findViewById(R.id.btnGoogle).setOnClickListener(v ->
                presenter.signInWithGoogle(this, getString(R.string.default_web_client_id))
        );

        findViewById(R.id.btnGuest).setOnClickListener(v -> navigateToHome());

        btmface.setOnClickListener(v -> showComingSoon("Facebook"));
        btmtwiter.setOnClickListener(v -> showComingSoon("Twitter"));



    }
    private void showComingSoon(String platform) {
        com.google.android.material.snackbar.Snackbar snackbar =
                com.google.android.material.snackbar.Snackbar.make(
                        findViewById(android.R.id.content),
                        platform + " login is coming soon! 🚀",
                        com.google.android.material.snackbar.Snackbar.LENGTH_LONG
                );

        snackbar.setBackgroundTint(android.graphics.Color.parseColor("#FF6D00"));
        snackbar.setTextColor(android.graphics.Color.WHITE);
        snackbar.show();
    }

    @Override
    public void showError(String message) {

            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#FF6D00"))
                    .show();

    }

    @Override
    public void showWelcomeMessage(String name) {
         com.google.android.material.snackbar.Snackbar.make(
                        findViewById(android.R.id.content),
                        "Welcome back, " + name + "! 😊",
                        com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
                ).setBackgroundTint(android.graphics.Color.parseColor("#4CAF50"))
                .show();
    }
    @Override
    public void navigateToHome() {
        Intent intent = new Intent(AuthenticationActivity.this, HomeActivity.class);
        startActivity(intent);
         finish();    }
    @Override
    public void openSignUp() {
        if(textTitle != null && textSubTitle != null) {
            textTitle.setText("Create Account");
            textSubTitle.setText("Sign up to start planning meals");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new SignupFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void openSignIn() {
        if(textTitle != null && textSubTitle != null) {
            textTitle.setText("Welcome Back");
            textSubTitle.setText("Sign in to continue");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new SignInFragment())
                    .commit();
        }
    }

    @Override
    public void showLoading() {
        if (lottieLoading != null && loadingOverlay != null) {
            loadingOverlay.setVisibility(View.VISIBLE);
            lottieLoading.setVisibility(View.VISIBLE);
            lottieLoading.playAnimation();
        }
    }

    @Override
    public void hideLoading() {
        if (lottieLoading != null && loadingOverlay != null) {
            lottieLoading.pauseAnimation();
            lottieLoading.setVisibility(View.GONE);
            loadingOverlay.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    public void onLoginClicked(String email, String password) {
        presenter.loginWithEmailAndPassword(email, password);
    }

    public void onRegisterClicked(String email, String password) {
        presenter.registerWithEmailAndPassword(email, password);
    }

    public AuthContract.Presenter getPresenter() {
        return presenter;
    }
}