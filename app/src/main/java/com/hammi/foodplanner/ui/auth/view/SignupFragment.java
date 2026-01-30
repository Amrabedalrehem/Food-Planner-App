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
import android.widget.TextView;

import com.hammi.foodplanner.R;
import com.hammi.foodplanner.ui.auth.presenter.AuthContract;

public class SignupFragment extends Fragment {

    private AuthContract.View authView;
    private AuthContract.Presenter presenter;

    private EditText fullNameEditText, emailEditText, passwordEditText;
    private Button btnSignUp;
    private TextView btnSwitchToSignIn;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AuthContract.View) {
            authView = (AuthContract.View) context;
            presenter = ((AuthenticationActivity) context).getPresenter();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AuthContract.View");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         fullNameEditText = view.findViewById(R.id.idFullnameSignUp);
        emailEditText = view.findViewById(R.id.idEmailSignUp);
        passwordEditText = view.findViewById(R.id.idPasswordSignUp);

         btnSignUp = view.findViewById(R.id.btnSwitchSignIn);

         btnSwitchToSignIn = view.findViewById(R.id.tvSignIn);

         btnSignUp.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (fullName.isEmpty()) {
                fullNameEditText.setError("Full name is required");
            } else if (email.isEmpty()) {
                emailEditText.setError("Email is required");
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Invalid email format");
            } else if (password.length() < 6) {
                passwordEditText.setError("Password must be at least 6 characters");
            } else {
                presenter.registerWithEmailAndPassword(email, password);
            }

        });

         btnSwitchToSignIn.setOnClickListener(v -> {
            if(authView != null) {
                authView.openSignIn();
            }
        });
    }
}
