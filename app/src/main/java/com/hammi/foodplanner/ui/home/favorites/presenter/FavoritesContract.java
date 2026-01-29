package com.hammi.foodplanner.ui.home.favorites.presenter;
import com.hammi.foodplanner.data.models.local.MealEntity;
import java.util.List;

public interface FavoritesContract {

    interface View {
        void showFavorites(List<MealEntity> favorites);
        void showEmptyState();
        void showError(String message);
        void showLoading();
        void hideLoading();
        void showMealRemovedSuccess();
    }

    interface Presenter {
        void loadFavorites();
        void removeFavorite(MealEntity meal);
        void onDestroy();
    }
}