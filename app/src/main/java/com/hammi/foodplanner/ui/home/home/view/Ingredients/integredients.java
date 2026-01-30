package com.hammi.foodplanner.ui.home.home.view.Ingredients;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.remote.Ingredients;
import com.hammi.foodplanner.ui.home.home.presentation.Ingredients.IngredientsContract;
import com.hammi.foodplanner.ui.home.home.presentation.Ingredients.IngredientsPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

    public class integredients extends Fragment implements IngredientsContract.View {
        private IngredientsContract.Presenter presenter;
        private RecyclerView recyclerView;
        private Dialog loadingDialog;
        private ImageView imageViewBack;
        private IngredientsAdapter adapter;
        private List<Ingredients> ingredientsList = new ArrayList<>();
          public integredients() {
            // Required empty public constructor
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            recyclerView = view.findViewById(R.id.rvAllIngredients);
            imageViewBack = view.findViewById(R.id.allIngredientBack);
            adapter = new IngredientsAdapter(ingredientsList);



            GridLayoutManager layoutManager =
                    new GridLayoutManager(requireContext(), 3);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);


            presenter = new IngredientsPresenter(this);
            presenter.getIngredients();
            BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNavigationView);
            navBar.getMenu().setGroupCheckable(0, true, false);
            for (int i = 0; i < navBar.getMenu().size(); i++) {
                navBar.getMenu().getItem(i).setChecked(false);
            }
            imageViewBack.setOnClickListener(v -> {
                NavController navController = NavHostFragment.findNavController(this);
                navController.popBackStack();
            });



        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_integredients, container, false);    }
        @Override
        public void showAllIngredients(List<Ingredients> ingredients) {
            if (ingredients != null && !ingredients.isEmpty()) {
                ingredientsList.clear();
                ingredientsList.addAll(ingredients);
                 adapter.notifyDataSetChanged();
            }
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
