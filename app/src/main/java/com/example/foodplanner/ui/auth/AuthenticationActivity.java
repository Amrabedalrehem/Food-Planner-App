package com.example.foodplanner.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.example.foodplanner.R;
// استبدل HomeActivity باسم النشاط الرئيسي في تطبيقك
// import com.example.foodplanner.ui.home.HomeActivity;

import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

public class AuthenticationActivity extends AppCompatActivity {

    private static final String TAG = "GoogleAuth";
    private FirebaseAuth mAuth;
    private CredentialManager credentialManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentication);

        mAuth = FirebaseAuth.getInstance();
        credentialManager = CredentialManager.create(this);

         FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            navigateToHome();
            return;
        }

        ImageButton btnGoogle = findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(v -> startGoogleSignIn());

        findViewById(R.id.btnGuest).setOnClickListener(v -> {
            navigateToHome();
        });
    }

    private void startGoogleSignIn() {
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(getString(R.string.default_web_client_id))
                .setAutoSelectEnabled(true)
                .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        CancellationSignal cancellationSignal = new CancellationSignal();

        credentialManager.getCredentialAsync(
                this,
                request,
                cancellationSignal,
                getMainExecutor(),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse response) {
                        handleSignIn(response);
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        Log.e(TAG, "Error getting credential: " + e.getMessage(), e);
                        Toast.makeText(AuthenticationActivity.this,
                                "فشل تسجيل الدخول: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleSignIn(GetCredentialResponse response) {
        Credential credential = response.getCredential();

        if (credential instanceof CustomCredential) {
            if (GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(credential.getType())) {
                try {
                    GoogleIdTokenCredential googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(((CustomCredential) credential).getData());

                    String idToken = googleIdTokenCredential.getIdToken();
                    firebaseAuthWithGoogle(idToken);
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing Google ID token", e);
                    Toast.makeText(this, "فشل معالجة بيانات Google", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e(TAG, "Unexpected credential type: " + credential.getType());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        String welcomeMessage = "مرحباً ";
                        if (user != null && user.getDisplayName() != null) {
                            welcomeMessage += user.getDisplayName();
                        }

                        Toast.makeText(AuthenticationActivity.this,
                                welcomeMessage,
                                Toast.LENGTH_SHORT).show();

                        navigateToHome();
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(AuthenticationActivity.this,
                                "فشل المصادقة مع Firebase",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToHome() {
        // قم بإلغاء التعليق وتعديل اسم الـ Activity
        // Intent intent = new Intent(AuthenticationActivity.this, HomeActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // startActivity(intent);
        // finish();

        Toast.makeText(this, "سيتم الانتقال للصفحة الرئيسية", Toast.LENGTH_SHORT).show();
    }
}