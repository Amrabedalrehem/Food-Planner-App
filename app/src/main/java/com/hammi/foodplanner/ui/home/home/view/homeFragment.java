package com.hammi.foodplanner.ui.home.home.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.remote.Category;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.data.repository.remote.auth.AuthRepository;
import com.hammi.foodplanner.ui.home.home.presentation.Categories.CategoriesContract;
import com.hammi.foodplanner.ui.home.home.presentation.Categories.CategoriesPresenter;
import com.hammi.foodplanner.ui.home.home.presentation.RandomMeal.RandomMealContract;
import com.hammi.foodplanner.ui.home.home.presentation.RandomMeal.RandomMealPresenter;
import com.hammi.foodplanner.ui.home.home.view.categorries.homeCategoriesAdapter;
import com.hammi.foodplanner.ui.home.profile.presenter.ProfileContract;
import com.hammi.foodplanner.ui.home.profile.presenter.ProfilePresenter;
import com.hammi.foodplanner.utility.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class homeFragment extends Fragment implements CategoriesContract.View, RandomMealContract.View , ProfileContract.View  {
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
    private  TextView tvUser;
    private CardView search_card;
    private LinearLayout card_Details;
    private String Id;
    private ProfilePresenter presenter;
    private Dialog loadingDialog;
    private Context context;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home__ffragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        textViewSeeAll = view.findViewById(R.id.tvSeeAll);
        recyclerView = view.findViewById(R.id.rvCategories);
        tvUser = view.findViewById(R.id.tvUser);
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
        if(NetworkUtils.isInternetAvailable(context))
        {
            card_Details.setOnClickListener(v -> {

                homeFragmentDirections.ActionHomeFfragmentToDetialsFragment action =
                        homeFragmentDirections.actionHomeFfragmentToDetialsFragment(Id);
                Navigation.findNavController(v).navigate(action);

            });
        }
        else {

        }

        presenter = new ProfilePresenter(this, new AuthRepository(requireContext()));
        presenter.loadUserData();

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
            Id = meal.getIdMeal();
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

    @Override
    public void displayUserData(String name, String email) {
        tvUser.setText(name);


    }

    @Override
    public void navigateToLogin() {

    }
}