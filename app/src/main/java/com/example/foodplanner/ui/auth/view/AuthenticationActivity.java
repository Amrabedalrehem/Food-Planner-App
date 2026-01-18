package com.example.foodplanner.ui.auth.view;
 import android.os.Bundle;
 import android.widget.TextView;
 import android.widget.Toast;
 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;
import com.example.foodplanner.R;
 import com.example.foodplanner.ui.auth.presenter.AuthContract;
 import com.example.foodplanner.ui.auth.presenter.AuthPresenter;

public class AuthenticationActivity extends AppCompatActivity implements AuthContract.View {
    TextView textTitle;
    TextView textSubTitle;
    private AuthContract.Presenter presenter;
    private androidx.credentials.CredentialManager credentialManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_authentication);
            if(savedInstanceState == null)
            {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainerView, new SignInFragment())
                        .commit();
            }
         presenter = new AuthPresenter(this);
         credentialManager = androidx.credentials.CredentialManager.create(this);
         findViewById(R.id.btnGoogle).setOnClickListener(v -> startGoogleSignIn());
         findViewById(R.id.btnGuest).setOnClickListener(v -> navigateToHome());
         textTitle = findViewById(R.id.textTitle);
         textSubTitle = findViewById(R.id.textSubTitle);


    }

    private void startGoogleSignIn() {
        com.google.android.libraries.identity.googleid.GetGoogleIdOption googleIdOption =
                new com.google.android.libraries.identity.googleid.GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(getString(R.string.default_web_client_id))
                        .build();
        androidx.credentials.GetCredentialRequest request =
                new androidx.credentials.GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .build();
        credentialManager.getCredentialAsync(this, request, new android.os.CancellationSignal(),
                java.util.concurrent.Executors.newSingleThreadExecutor(),
                new androidx.credentials.CredentialManagerCallback<androidx.credentials.GetCredentialResponse, androidx.credentials.exceptions.GetCredentialException>() {
                    @Override
                    public void onResult(androidx.credentials.GetCredentialResponse response) {
                        handleSignIn(response.getCredential());
                    }
                    @Override
                    public void onError(@NonNull androidx.credentials.exceptions.GetCredentialException e) {
                        showError("Google Error: " + e.getMessage());
                    }
                });
    }

    private void handleSignIn(androidx.credentials.Credential credential) {
        if (credential instanceof androidx.credentials.CustomCredential &&
                credential.getType().equals(com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            try {
                com.google.android.libraries.identity.googleid.GoogleIdTokenCredential tokenCredential =
                        com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.createFrom(credential.getData());
                runOnUiThread(() -> presenter.loginWithGoogle(tokenCredential.getIdToken()));
            } catch (Exception e) {
                showError("Token Error");
            }
        }
    }

    @Override
    public void showWelcomeMessage(String name) {
        runOnUiThread(() -> Toast.makeText(this, "Welcome " + name, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void showError(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void navigateToHome() {
        runOnUiThread(() -> {
            Toast.makeText(this, "Redirecting to Home...", Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent(this, HomeActivity.class);
            // startActivity(intent);
            // finish();
        });
    }

    @Override
    public void openSignUp() {
     getSupportFragmentManager()
             .beginTransaction()
             .replace(R.id.fragmentContainerView,new SignupFragment())
             .addToBackStack(null).commit();
     textSubTitle.setText("Sign up to start planning meals");
     textTitle.setText("Create Account");
     }

    @Override
    public void openSignIn() {
    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,new SignInFragment()).commit();
        textTitle.setText("Welcome Back");
        textSubTitle.setText("Sign in to continue");
    }
}