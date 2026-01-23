package com.example.foodplanner.ui.home.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.Category;
import com.example.foodplanner.ui.home.home.presentation.Categories.CategoriesContract;
import com.example.foodplanner.ui.home.home.presentation.Categories.CategoriesPresenter;
import com.example.foodplanner.ui.home.home.view.categorries.homeCategoriesAdapter;

import java.util.ArrayList;
import java.util.List;


public class homeFragment extends Fragment implements CategoriesContract.View {

    private RecyclerView recyclerView;
    private homeCategoriesAdapter adapter;
    private CategoriesPresenter presenter;
    private List<Category> categories = new ArrayList<>();
    private TextView textViewSeeAll;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home__ffragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         textViewSeeAll = view.findViewById(R.id.tvSeeAll);
         recyclerView = view.findViewById(R.id.rvCategories);
        adapter = new homeCategoriesAdapter  (categories,true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        presenter = new CategoriesPresenter(this);
        presenter.getCategories();

        textViewSeeAll.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_home_Ffragment_to_allcategories);
        });

    }

    @Override
    public void showCategories(List<Category> categoryList) {
         if (categoryList != null) {
            categories.clear();
            categories.addAll(categoryList);
            adapter.notifyDataSetChanged();
            Log.i("cat99", "good: " + categoryList.size());
        }
    }

    @Override
    public void showError(String message) {
        Log.e("cat99", ": bad " + message);
        Toast.makeText(requireContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

 }