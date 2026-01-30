package com.hammi.foodplanner.ui.planner.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.local.MealPlanEntity;

import java.util.List;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.ViewHolder> {

    private List<MealEntity> meals;
    private List<MealPlanEntity> planEntries;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MealEntity meal);
        void onDeleteClick(int planId, MealPlanEntity planEntity);
    }

    public MealPlanAdapter(List<MealEntity> meals,
                           List<MealPlanEntity> planEntries,
                           OnItemClickListener listener) {
        this.meals = meals;
        this.planEntries = planEntries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_planer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealEntity meal = meals.get(position);

         MealPlanEntity planEntry = findPlanEntryByMealId(meal.getMealId(), position);

        holder.bind(meal, planEntry, listener);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

     private MealPlanEntity findPlanEntryByMealId(String mealId, int position) {
         if (position < planEntries.size() &&
                planEntries.get(position).getMealId().equals(mealId)) {
            return planEntries.get(position);
        }

         for (MealPlanEntity entry : planEntries) {
            if (entry.getMealId().equals(mealId)) {
                return entry;
            }
        }

        return null;
    }

    public void updateMeals(List<MealEntity> newMeals) {
        this.meals = newMeals;
        notifyDataSetChanged();
    }

    public void updatePlanEntries(List<MealPlanEntity> newPlanEntries) {
        this.planEntries = newPlanEntries;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mealImage;
        TextView mealTitle;
        TextView mealType;
        ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.imgPlanMeal);
            mealTitle = itemView.findViewById(R.id.tvPlanMealTitle);
            mealType = itemView.findViewById(R.id.tvPlanMealType);
            deleteButton = itemView.findViewById(R.id.btnDeletePlan);
        }

        public void bind(MealEntity meal, MealPlanEntity planEntry, OnItemClickListener listener) {
            mealTitle.setText(meal.getName());
            mealType.setText(meal.getCategory());

            Glide.with(itemView.getContext())
                    .load(meal.getThumbnailUrl())
                    .transform(new RoundedCorners(30))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.errorplaceholder)
                    .into(mealImage);

            itemView.setOnClickListener(v -> listener.onItemClick(meal));
            deleteButton.setOnClickListener(v -> {
                if (planEntry != null) {
                    listener.onDeleteClick(planEntry.getId(), planEntry);
                }
            });
        }
    }
}