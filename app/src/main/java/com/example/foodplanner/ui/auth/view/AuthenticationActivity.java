package com.example.foodplanner.data.datasource.network.auth;

import android.content.Context;
import androidx.annotation.NonNull;

public class FirebaseAuthHelper {

    private Context context;
    private androidx.credentials.CredentialManager credentialManager;

    public FirebaseAuthHelper(Context context) {
        this.context = context;
        this.credentialManager = androidx.credentials.CredentialManager.create(context);
    }

    public interface GoogleSignInCallback {
        void onSuccess(String idToken);
        void onError(String message);
    }

    public void startGoogleSignIn(String webClientId, GoogleSignInCallback callback) {
        com.google.android.libraries.identity.googleid.GetGoogleIdOption googleIdOption =
                new com.google.android.libraries.identity.googleid.GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(webClientId)
                        .build();

        androidx.credentials.GetCredentialRequest request =
                new androidx.credentials.GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .build();

        if (context instanceof android.app.Activity) {
            credentialManager.getCredentialAsync(
                    (android.app.Activity) context,
                    request,
                    new android.os.CancellationSignal(),
                    java.util.concurrent.Executors.newSingleThreadExecutor(),
                    new androidx.credentials.CredentialManagerCallback<androidx.credentials.GetCredentialResponse, androidx.credentials.exceptions.GetCredentialException>() {
                        @Override
                        public void onResult(androidx.credentials.GetCredentialResponse response) {
                            handleGoogleResponse(response.getCredential(), callback);
                        }

                        @Override
                        public void onError(@NonNull androidx.credentials.exceptions.GetCredentialException e) {
                            callback.onError("Google Error: " + e.getMessage());
                        }
                    });
        }
    }

    private void handleGoogleResponse(androidx.credentials.Credential credential, GoogleSignInCallback callback) {
        if (credential instanceof androidx.credentials.CustomCredential &&
                credential.getType().equals(com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            try {
                com.google.android.libraries.identity.googleid.GoogleIdTokenCredential tokenCredential =
                        com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.createFrom(credential.getData());
                callback.onSuccess(tokenCredential.getIdToken());
            } catch (Exception e) {
                callback.onError("Token Error: " + e.getMessage());
            }
        } else {
            callback.onError("Invalid credential type");
        }
    }
}