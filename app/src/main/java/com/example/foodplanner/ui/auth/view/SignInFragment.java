package com.example.foodplanner.ui.auth.view;

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

import com.example.foodplanner.R;
import com.example.foodplanner.ui.auth.presenter.AuthContract;
import com.example.foodplanner.ui.auth.presenter.AuthPresenter;

public class SignInFragment extends Fragment  {
    AuthContract.View authContract;
    private AuthPresenter presenter;
    Button btnSignIn,switchToSignUpBtn;
    private EditText emailEditText;
    private EditText passwordEditText;
    TextView textViewSignIn;
      public SignInFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        authContract = (AuthContract.View) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewSignIn = view.findViewById(R.id.tvSignUp);
        emailEditText = view.findViewById(R.id.signInemail);
        passwordEditText = view.findViewById(R.id.signInpassword);
        presenter = new AuthPresenter((AuthContract.View) getActivity());
        btnSignIn = view.findViewById(R.id.btnSwitchSignUp);
        btnSignIn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()) {
                authContract.showError("Please enter email and password");
            } else {
                presenter.loginWithEmailAndPassword(email, password);
            }
        });


        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authContract.openSignUp();
            }
        });

    }
}