package com.hammi.foodplanner.ui.home.home.view.categorries;

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

import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.remote.Category;
import com.hammi.foodplanner.ui.home.home.presentation.Categories.CategoriesContract;
import com.hammi.foodplanner.ui.home.home.presentation.Categories.CategoriesPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class allcategories extends Fragment implements CategoriesContract.View {
    private CategoriesPresenter presenter;
    private RecyclerView recyclerView;
    private ImageView imageViewBack;
    private AllCategoriesAdapter adapter;
    private List<Category> categoriesList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.rvAllCategories);
        imageViewBack = view.findViewById(R.id.allCategoriesBack);
        adapter = new AllCategoriesAdapter(categoriesList);

        GridLayoutManager layoutManager =
                new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        presenter = new CategoriesPresenter(this);
        presenter.getCategories();
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

        return inflater.inflate(R.layout.fragment_allcategories, container, false);
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoriesList.clear();
        categoriesList.addAll(categories);
        adapter.notifyDataSetChanged();
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