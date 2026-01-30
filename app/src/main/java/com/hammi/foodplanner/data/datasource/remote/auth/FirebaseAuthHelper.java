package com.hammi.foodplanner.data.datasource.remote.auth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.CancellationSignal;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class FirebaseAuthHelper {
    private final FirebaseAuth firebaseAuth;
    private final Context context;
    private final CredentialManager credentialManager;

    private static FirebaseAuthHelper instance;

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
    public Single<FirebaseUser> startGoogleSignIn(Activity activity, String webClientId) {
        return Single.<FirebaseUser>create(emitter -> {
            if (webClientId == null || webClientId.isEmpty()) {
                emitter.onError(new Exception("Web Client ID is missing"));
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
                    Runnable::run,
                    new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                        @Override
                        public void onResult(GetCredentialResponse response) {
                            handleGoogleResponse(response.getCredential(), emitter);
                        }

                        @Override
                        public void onError(GetCredentialException e) {
                            emitter.onError(e);
                        }
                    }
            );
        }).subscribeOn(AndroidSchedulers.mainThread());
     }

    private void handleGoogleResponse(androidx.credentials.Credential credential, SingleEmitter<FirebaseUser> emitter) {
        if (credential instanceof androidx.credentials.CustomCredential &&
                credential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            try {
                GoogleIdTokenCredential tokenCredential = GoogleIdTokenCredential.createFrom(credential.getData());
                AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(tokenCredential.getIdToken(), null);

                 loginWithGoogle(firebaseCredential, emitter);

            } catch (Exception e) {
                emitter.onError(e);
            }
        } else {
            emitter.onError(new Exception("Invalid credential type"));
        }
    }

    private void loginWithGoogle(AuthCredential credential, SingleEmitter<FirebaseUser> emitter) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && firebaseAuth.getCurrentUser() != null) {
                        emitter.onSuccess(firebaseAuth.getCurrentUser());
                    } else {
                        emitter.onError(task.getException() != null ? task.getException() : new Exception("Login failed"));
                    }
                });
    }


    public Single<FirebaseUser> registerWithEmail(String email, String password) {
        return Single.<FirebaseUser>create(emitter -> {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(firebaseAuth.getCurrentUser());
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        }).subscribeOn(Schedulers.io());
    }

    public Single<FirebaseUser> loginWithEmail(String email, String password) {
        return Single.<FirebaseUser>create(emitter -> {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(firebaseAuth.getCurrentUser());
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        }).subscribeOn(Schedulers.io());
    }


    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public Completable signOut() {
        return Completable.fromAction(firebaseAuth::signOut)
                .subscribeOn(Schedulers.io());
    }
}