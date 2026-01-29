package com.hammi.foodplanner.ui.home.favorites.view;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.local.MealEntity;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<MealEntity> meals;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MealEntity meal);
        void onRemoveClick(MealEntity meal);
    }

    public FavoritesAdapter(List<MealEntity> meals, OnItemClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealEntity meal = meals.get(position);
        holder.bind(meal, listener);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void updateData(List<MealEntity> newMeals) {
        this.meals = newMeals;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mealImage;
        TextView mealName;
        TextView mealCountry;
        TextView mealCategory;
        ImageButton removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(R.id.mealName);
            mealCountry = itemView.findViewById(R.id.mealCountry);
            mealCategory = itemView.findViewById(R.id.mealCategory);
            removeButton = itemView.findViewById(R.id.removeButton);
        }

        public void bind(MealEntity meal, OnItemClickListener listener) {
            mealName.setText(meal.getName());
            mealCountry.setText(meal.getArea());
            mealCategory.setText(meal.getCategory());

             Glide.with(itemView.getContext())
                    .load(meal.getThumbnailUrl())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.errorplaceholder)
                    .into(mealImage);

             itemView.setOnClickListener(v -> listener.onItemClick(meal));
            removeButton.setOnClickListener(v -> listener.onRemoveClick(meal));
        }
    }
}