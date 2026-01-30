package com.hammi.foodplanner.ui.profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.repository.remote.auth.AuthRepository;
import com.hammi.foodplanner.ui.auth.view.AuthenticationActivity;
import com.hammi.foodplanner.ui.profile.presenter.ProfileContract;
import com.hammi.foodplanner.ui.profile.presenter.ProfilePresenter;
import com.google.android.material.button.MaterialButton;

public class profile extends Fragment implements ProfileContract.View {

    private TextView tvName, tvEmail;
    private MaterialButton btnLogout;
    private ProfilePresenter presenter;

    public profile() {
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         tvName = view.findViewById(R.id.titleprofile);
        tvEmail = view.findViewById(R.id.gmailprofile);
        btnLogout = view.findViewById(R.id.btnLogout);

         presenter = new ProfilePresenter(this, new AuthRepository(requireContext()));
         presenter.loadUserData();
         btnLogout.setOnClickListener(v -> presenter.logout());
    }

    @Override
    public void displayUserData(String name, String email) {
        if (tvName != null) tvName.setText(name);
        if (tvEmail != null) tvEmail.setText(email);
    }

    @Override
    public void navigateToLogin() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void showError(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
     }
}