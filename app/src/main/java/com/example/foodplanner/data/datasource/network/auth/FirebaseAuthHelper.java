package com.example.foodplanner.data.datasource.network.auth;

import androidx.annotation.NonNull;

import com.example.foodplanner.R;

public class firebase {

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
                        handleGoogleResponse(response.getCredential());
                    }

                    @Override
                    public void onError(@NonNull androidx.credentials.exceptions.GetCredentialException e) {
                        showError("Google Error: " + e.getMessage());
                    }
                });
    }

    private void handleGoogleResponse(androidx.credentials.Credential credential) {
        if (credential instanceof androidx.credentials.CustomCredential &&
                credential.getType().equals(com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            try {
                com.google.android.libraries.identity.googleid.GoogleIdTokenCredential tokenCredential =
                        com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.createFrom(credential.getData());
                presenter.loginWithGoogle(tokenCredential.getIdToken());
            } catch (Exception e) {
                showError("Token Error");
            }
        }
    }

}
