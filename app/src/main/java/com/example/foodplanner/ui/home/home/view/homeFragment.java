package com.example.foodplanner.ui.home.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.models.Category;
import com.example.foodplanner.data.models.Meal;
import com.example.foodplanner.ui.home.home.presentation.Categories.CategoriesContract;
import com.example.foodplanner.ui.home.home.presentation.Categories.CategoriesPresenter;
import com.example.foodplanner.ui.home.home.presentation.RandomMeal.RandomMealContract;
import com.example.foodplanner.ui.home.home.presentation.RandomMeal.RandomMealPresenter;
import com.example.foodplanner.ui.home.home.view.categorries.homeCategoriesAdapter;

import java.util.ArrayList;
import java.util.List;

public class homeFragment extends Fragment implements CategoriesContract.View, RandomMealContract.View {
    private RecyclerView recyclerView;
    private homeCategoriesAdapter adapter;
    private CategoriesPresenter categoriesPresenter;
    private RandomMealContract.Presenter randomMealpresenter;
    private List<Category> categories = new ArrayList<>();
    private TextView textViewSeeAll;
    private TextView tvMealDescription;
    private TextView tvMealCountery;
    private TextView tvMealTitle;
    private ImageView imageRandom;
    private CardView  rvQAIngredients ;
    private  CardView  rvQACountries;
    private CardView  rvِQACategories;
    private CardView search_card;
    private LinearLayout card_Details;
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
        tvMealDescription = view.findViewById(R.id.tvMealDescription);
        tvMealCountery = view.findViewById(R.id.tvMealCountery);
        tvMealTitle = view.findViewById(R.id.tvMealTitle);
        imageRandom = view.findViewById(R.id.imageRandom);
        rvQAIngredients =view.findViewById(R.id.rvQAIngredients);
        rvQACountries = view.findViewById(R.id.rvQACountries);
        rvِQACategories = view.findViewById(R.id.rvِQACategories);
        search_card = view.findViewById(R.id.search_card);
        card_Details = view.findViewById(R.id.card_Details);


        adapter = new homeCategoriesAdapter(categories, true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        categoriesPresenter = new CategoriesPresenter(this);
        categoriesPresenter.getCategories();
        randomMealpresenter = new RandomMealPresenter(this);
        randomMealpresenter.getRandomMeal();

        rvQACountries.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_home_Ffragment_to_countriesFragment);
        });

        rvِQACategories.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_home_Ffragment_to_allcategories);
        });
        rvQAIngredients.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_home_Ffragment_to_integredients);
        });
        textViewSeeAll.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_home_Ffragment_to_allcategories);
        });
        search_card.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_home_Ffragment_to_search3);
        });
        card_Details.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_home_Ffragment_to_detialsFragment);
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
    public void showRandomMeal(Meal meal) {

        if (meal != null) {
            Log.i("TAGamr", "showRandomMeal: " + meal);
            tvMealTitle.setText(meal.getStrMeal());
            tvMealCountery.setText(meal.getStrArea());
            tvMealDescription.setText(meal.getStrInstructions());

            Glide.with(this).load(meal.getStrMealThumb()).into(imageRandom);
            Glide.with(this)
                    .load(meal.getStrMealThumb())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.errorplaceholder)
                    .into(imageRandom);


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