package com.hammi.foodplanner.ui.home.home.view.Countries;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.remote.Countries;
import com.hammi.foodplanner.ui.home.home.presentation.Countries.CountriesContract;
import com.hammi.foodplanner.ui.home.home.presentation.Countries.CountriesPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class countriesFragment extends Fragment implements CountriesContract.View {
    CountriesContract.Presenter presenter;
    RecyclerView recyclerView;
    CountriesAdapter countriesAdapter;
    List<Countries> countriesList = new ArrayList<>();
    ImageView imageView;
   Dialog loadingDialog;
    public countriesFragment() {
     }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         imageView = view.findViewById(R.id.allCountriesBack);
        presenter = new CountriesPresenter(this);
        presenter.getCountries();


        countriesAdapter = new CountriesAdapter(countriesList);
        recyclerView = view.findViewById(R.id.rvAllCountries);
        recyclerView.setAdapter(countriesAdapter);
        GridLayoutManager layoutManager =
                new GridLayoutManager(requireContext(), 1);
        recyclerView.setLayoutManager(layoutManager);

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNavigationView);
        navBar.getMenu().setGroupCheckable(0, true, false);
        for (int i = 0; i < navBar.getMenu().size(); i++) {
            navBar.getMenu().getItem(i).setChecked(false);
        }
        imageView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries, container, false);
    }

    @Override
    public void showCountries(List<Countries> countries) {
        countriesList.clear();
        countriesList.addAll(countries);
        countriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        View rootView = requireActivity().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor("#FF6D00"));
        snackbar.setTextColor(Color.WHITE);
        snackbar.setAction("OK", v -> snackbar.dismiss());
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();

    }

    @Override
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new Dialog(requireContext());
            loadingDialog.setContentView(R.layout.dialog_loading);
            loadingDialog.setCancelable(false);
            loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        loadingDialog.show();
    }
    @Override
    public void hideLoading() {

        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}