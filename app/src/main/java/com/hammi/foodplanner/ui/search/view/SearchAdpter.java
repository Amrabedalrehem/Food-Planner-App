package com.hammi.foodplanner.ui.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.remote.Meal;

import java.util.List;

public class SearchAdpter extends RecyclerView.Adapter<SearchAdpter.ViewHolder> {

     public interface OnMealClickListener {
        void onMealClick(String mealId);
    }
    private List<Meal> meals;
    private OnMealClickListener listener;
    public SearchAdpter(List<Meal> meals, OnMealClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_search, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal currentMeal = meals.get(position);

        holder.tvMealName.setText(currentMeal.getStrMeal());

        String category = currentMeal.getStrArea() != null
                ? currentMeal.getStrArea()
                : currentMeal.getStrCategory();
        holder.tvMealCategory.setText(category);
        Glide.with(holder.itemView.getContext())
                .load(currentMeal.getStrMealThumb())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorplaceholder)
                .into(holder.ivMealImage);
        holder.layout.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMealClick(currentMeal.getIdMeal());
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMealImage;
        TextView tvMealName;
        TextView tvMealCategory;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            ivMealImage = v.findViewById(R.id.iv_meal_image);
            tvMealName = v.findViewById(R.id.tv_meal_name);
            tvMealCategory = v.findViewById(R.id.tv_meal_category);
        }
    }
}
