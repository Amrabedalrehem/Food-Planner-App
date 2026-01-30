package com.hammi.foodplanner.ui.planner.view;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.local.MealPlanEntity;
 import com.hammi.foodplanner.ui.planner.presenter.MealPlanContract;
import com.hammi.foodplanner.ui.planner.presenter.MealPlanPresenter;
import com.hammi.foodplanner.utility.SnackBar;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class planner extends Fragment implements MealPlanContract.View {

     private CalendarView calendarView;
    private RecyclerView recyclerView;
    private TextView noDataTextView;
     private MealPlanPresenter presenter;
    private MealPlanAdapter adapter;
     private int selectedYear;
    private int selectedMonth;
    private int selectedDay;
    private  Dialog loadingDialog;
    private View view;
    ImageView ivSearchInP;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_planner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    this.view = view;
         initViews(view);
         presenter = new MealPlanPresenter(this, getContext());

        setupCalendar();
        setupRecyclerView();
        loadTodayMeals();
        ivSearchInP.setOnClickListener(
                v -> {
                    NavDirections action = plannerDirections.actionPlannerToSearch3();
                    Navigation.findNavController(view).navigate(action);

                }
        );

    }

    private void initViews(View view) {
        calendarView = view.findViewById(R.id.calendarView);
        ivSearchInP = view.findViewById(R.id.ivSearchInP);
        recyclerView = view.findViewById(R.id.rvPlannedMeals);
        noDataTextView = view.findViewById(R.id.tvNoData);
    }

    private void setupCalendar() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedYear = year;
            selectedMonth = month;
            selectedDay = dayOfMonth;
            presenter.loadMealsForDate(year, month, dayOfMonth);
            presenter.loadMealPlanEntriesForDate(year, month, dayOfMonth);
        });
    }

    private void setupRecyclerView() {
        adapter = new MealPlanAdapter(
                new ArrayList<>(),
                new ArrayList<>(),
                new MealPlanAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(MealEntity meal) {
                        plannerDirections.ActionPlannerToLocalDetailsFragment action =
                                plannerDirections.actionPlannerToLocalDetailsFragment(meal.getMealId());
                        Navigation.findNavController(getView()).navigate(action);
                    }

                    @Override
                    public void onDeleteClick(int planId, MealPlanEntity planEntity) {
                        presenter.removeMealFromPlan(planId, planEntity);
                        presenter.loadMealsForDate(selectedYear, selectedMonth, selectedDay);
                        presenter.loadMealPlanEntriesForDate(selectedYear, selectedMonth, selectedDay);
                    }
                }
        );


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void loadTodayMeals() {
        Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendarView.setDate(calendar.getTimeInMillis(), true, true);
        presenter.loadMealsForDate(selectedYear, selectedMonth, selectedDay);
        presenter.loadMealPlanEntriesForDate(selectedYear, selectedMonth, selectedDay);
    }

    @Override
    public void showMealsForDate(List<MealEntity> meals) {
        recyclerView.setVisibility(View.VISIBLE);
        noDataTextView.setVisibility(View.GONE);
        adapter.updateMeals(meals);
    }

    @Override
    public void showMealPlanEntries(List<MealPlanEntity> planEntries) {
        adapter.updatePlanEntries(planEntries);
    }

    @Override
    public void showEmptyState() {
        recyclerView.setVisibility(View.GONE);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showWeekMealsCount(int count) {

    }

    @Override
    public void showMealAddedSuccess() {
         SnackBar.showSuccess(view, "Meal added to plan!");

    }

    @Override
    public void showMealRemovedSuccess() {

        SnackBar.showSuccess(view, "Meal removed from plan!");
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
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}