package com.hammi.foodplanner.ui.home.favorites.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hammi.foodplanner.R;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.ui.home.favorites.presenter.FavoritesContract;
import com.hammi.foodplanner.ui.home.favorites.presenter.FavoritesPresenter;

import java.util.ArrayList;
import java.util.List;

public class favorites extends Fragment implements FavoritesContract.View {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyStateText;
    private FavoritesPresenter presenter;
    private FavoritesAdapter adapter;

     @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

     @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        presenter = new FavoritesPresenter(this, getContext());
        setupRecyclerView();
        presenter.loadFavorites();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        emptyStateText = view.findViewById(R.id.emptyStateText);
    }

    private void setupRecyclerView() {
         adapter = new FavoritesAdapter(new ArrayList<>(), new FavoritesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MealEntity meal) {
                Toast.makeText(getContext(), "Clicked: " + meal.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRemoveClick(String mealId) {
                presenter.removeFavorite(mealId);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showFavorites(List<MealEntity> favorites) {
        recyclerView.setVisibility(View.VISIBLE);
        emptyStateText.setVisibility(View.GONE);
        adapter.updateData(favorites);
    }

    @Override
    public void showEmptyState() {
        recyclerView.setVisibility(View.GONE);
        emptyStateText.setVisibility(View.VISIBLE);
        emptyStateText.setText("No favorites yet!\nStart adding meals to your favorites.");
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyStateText.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMealRemovedSuccess() {
        Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) presenter.onDestroy();
    }
}