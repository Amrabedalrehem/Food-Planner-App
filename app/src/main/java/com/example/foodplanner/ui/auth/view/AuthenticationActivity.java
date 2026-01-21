package com.example.foodplanner.ui.auth.view;
 import android.os.Bundle;
 import android.widget.TextView;
 import android.widget.Toast;
 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;
import com.example.foodplanner.R;
 import com.example.foodplanner.ui.auth.presenter.AuthContract;
 import com.example.foodplanner.ui.auth.presenter.AuthPresenter;
 import com.example.foodplanner.data.repository.auth.AuthRepository;

 

public class AuthenticationActivity extends AppCompatActivity implements AuthContract.View {

    private TextView textTitle, textSubTitle;
    private AuthContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

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
    }

    @Override
    public void showWelcomeMessage(String name) {
        Toast.makeText(this, "Hello " + name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToHome() {
        Toast.makeText(this, "Go to HomeActivity", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void openSignUp() {
        if(textTitle != null && textSubTitle != null) {
            textTitle.setText("Create Account");
            textSubTitle.setText("Sign up to start planning meals");
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new SignupFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openSignIn() {
        if(textTitle != null && textSubTitle != null) {
            textTitle.setText("Welcome Back");
            textSubTitle.setText("Sign in to continue");
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new SignInFragment())
                .commit();
    }


    @Override
    public void showLoading() {
        // Optional: show progress bar
    }

    @Override
    public void hideLoading() {
        // Optional: hide progress bar
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
