package com.hammi.foodplanner.ui.auth.view;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.ui.auth.presenter.AuthContract;

import android.widget.TextView;


public class SignInFragment extends Fragment  {

    private EditText emailEditText, passwordEditText;
    private Button btnSignIn;
    private TextView btnSwitchToSignUp;

    private AuthContract.View authView;
    private AuthContract.Presenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AuthContract.View) {
            authView = (AuthContract.View) context;
            presenter = ((AuthenticationActivity) context).getPresenter();
        } else {
            throw new RuntimeException("Parent activity must implement AuthContract.View");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         emailEditText = view.findViewById(R.id.signInemail);
        passwordEditText = view.findViewById(R.id.signInpassword);

         btnSignIn = view.findViewById(R.id.btnSwitchSignUp);
         btnSwitchToSignUp = view.findViewById(R.id.textSignUp);

         btnSignIn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            if (email.isEmpty()) {
                emailEditText.setError("Email is required");
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Invalid email format");
            } else if (password.isEmpty()) {
                passwordEditText.setError("Password is required");
            } else if (password.length() < 6) {
                passwordEditText.setError("Password must be at least 6 characters");
            } else {
                presenter.loginWithEmailAndPassword(email, password);
            }
        });

         btnSwitchToSignUp.setOnClickListener(v -> {
            if(authView != null) {
                authView.openSignUp();
            }
        });
    }
}
