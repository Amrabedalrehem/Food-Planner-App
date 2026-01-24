package com.example.foodplanner.data.datasource.remote.auth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
 import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import java.util.concurrent.Executors;

public class FirebaseAuthHelper {

    private final FirebaseAuth firebaseAuth;
    private final Context context;
    private final CredentialManager credentialManager;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private static  FirebaseAuthHelper instance;

    private FirebaseAuthHelper(Context context) {
        this.context = context.getApplicationContext();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.credentialManager = CredentialManager.create(this.context);
    }

    public static synchronized FirebaseAuthHelper getInstance(Context context) {
        if (instance == null) {
            instance = new FirebaseAuthHelper(context);
        }
        return instance;
    }


    @SuppressLint("NewApi")
    public void startGoogleSignIn(Activity activity, String webClientId, AuthCallback callback) {
        if (activity == null || callback == null) return;
        if (webClientId == null || webClientId.isEmpty()) {
            callback.onError("Web Client ID is missing");
            return;
        }

         GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(webClientId)
                .build();

         GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

         credentialManager.getCredentialAsync(
                activity,
                request,
                new CancellationSignal(),
                Executors.newSingleThreadExecutor(),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse response) {
                        if (response.getCredential() != null) {
                            handleGoogleResponse(response.getCredential(), callback);
                        } else {
                            mainHandler.post(() -> callback.onError("No credential returned"));
                        }
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        mainHandler.post(() -> callback.onError("Google Error: " + e.getMessage()));
                    }
                }
        );
    }


    private void handleGoogleResponse(androidx.credentials.Credential credential, AuthCallback callback) {
        if (credential instanceof androidx.credentials.CustomCredential &&
                credential.getType().equals(com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            try {
                com.google.android.libraries.identity.googleid.GoogleIdTokenCredential tokenCredential =
                        com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.createFrom(credential.getData());

                AuthCredential firebaseCredential =
                        GoogleAuthProvider.getCredential(tokenCredential.getIdToken(), null);

                loginWithGoogle(firebaseCredential, callback);

            } catch (Exception e) {
                mainHandler.post(() -> callback.onError("Token Error: " + e.getMessage()));
            }
        } else {
            mainHandler.post(() -> callback.onError("Invalid credential type"));
        }
    }

    public void loginWithGoogle(AuthCredential credential, AuthCallback callback) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(firebaseAuth.getCurrentUser());
                    } else {
                        callback.onError(task.getException() != null ? task.getException().getMessage() : "Unknown error");
                    }
                });
    }

    public void registerWithEmail(String email, String password, AuthCallback callback) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            callback.onError("Email or password cannot be empty");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(firebaseAuth.getCurrentUser());
                    } else {
                        callback.onError(task.getException() != null ?
                                task.getException().getLocalizedMessage() : "Unknown error");
                    }
                });
    }

    public void loginWithEmail(String email, String password, AuthCallback callback) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            callback.onError("Email or password cannot be empty");
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(firebaseAuth.getCurrentUser());
                    } else {
                        callback.onError(task.getException() != null ?
                                task.getException().getLocalizedMessage() : "Unknown error");
                    }
                });
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void signOut() {
        firebaseAuth.signOut();
    }
}
