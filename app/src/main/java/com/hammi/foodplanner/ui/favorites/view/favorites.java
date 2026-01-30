package com.hammi.foodplanner.ui.favorites.view;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.hammi.foodplanner.R;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.ui.favorites.presenter.FavoritesContract;
import com.hammi.foodplanner.ui.favorites.presenter.FavoritesPresenter;
import com.hammi.foodplanner.utility.SnackBar;

import java.util.ArrayList;
import java.util.List;


public class favorites extends Fragment implements FavoritesContract.View {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyStateText;
    private FavoritesPresenter presenter;
    private FavoritesAdapter adapter;
    private Dialog loadingDialog;
    View view;
    ImageView ivSearchInF;
     @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

     @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         this.view = view;
         ivSearchInF = view.findViewById(R.id.ivSearchInF);
         recyclerView = view.findViewById(R.id.favoritesRecyclerView);
         progressBar = view.findViewById(R.id.progressBar);
         emptyStateText = view.findViewById(R.id.emptyStateText);
         presenter = new FavoritesPresenter(this, getContext());
         adapter = new FavoritesAdapter(new ArrayList<>(), new FavoritesAdapter.OnItemClickListener() {
             @Override
             public void onItemClick(MealEntity meal) {

                 favoritesDirections.ActionFavoritesToLocalDetailsFragment actionFavoritesToLocalDetailsFragment  =
                         favoritesDirections.actionFavoritesToLocalDetailsFragment(meal.getMealId());
                 Navigation.findNavController(view).navigate(actionFavoritesToLocalDetailsFragment);
             }

             @Override
             public void onRemoveClick(MealEntity  meal) {
                 presenter.removeFavorite(meal);
             }
         });

         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         recyclerView.setAdapter(adapter);
         presenter.loadFavorites();
         ivSearchInF.setOnClickListener(v->{
             NavDirections action = favoritesDirections.actionFavoritesToSearch3();
             Navigation.findNavController(view).navigate(action);

         });
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
    public void showMealRemovedSuccess() {
         SnackBar.showSuccess(view, "Meal removed from favorites! ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) presenter.onDestroy();
    }
}