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

import com.example.foodplanner.R;
import com.example.foodplanner.ui.auth.presenter.AuthContract;
import com.example.foodplanner.ui.auth.presenter.AuthPresenter;


public class SignupFragment extends Fragment {
    AuthContract.View authContract;
    private AuthPresenter presenter;
    Button switchToSignInBtn;
    private EditText emailEditText;
    private EditText passwordEditText;
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEditText = view.findViewById(R.id.idEmailSignUp);
        passwordEditText = view.findViewById(R.id.idPasswordSignUp);
        presenter = new AuthPresenter((AuthContract.View) getActivity());

        switchToSignInBtn = view.findViewById(R.id.btnSwitchSignIn);
        switchToSignInBtn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()) {
                authContract.showError("Please enter email and password");
            } else {
                presenter.registerWithEmailAndPassword(email, password);
            }
        });

    view.findViewById(R.id.tvSignUp).setOnClickListener(v ->
    {
        authContract.openSignIn();
    });

    }
}