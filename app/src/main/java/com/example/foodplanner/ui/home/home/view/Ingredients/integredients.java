package com.example.foodplanner.ui.home.home.view.Ingredients;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.remote.Ingredients;
import com.example.foodplanner.ui.home.home.presentation.Ingredients.IngredientsContract;
import com.example.foodplanner.ui.home.home.presentation.Ingredients.IngredientsPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

    public class integredients extends Fragment implements IngredientsContract.View {
        private IngredientsContract.Presenter presenter;
        private RecyclerView recyclerView;
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

        }

        @Override
        public void showLoading() {

        }

        @Override
        public void hideLoading() {

        }


    }
