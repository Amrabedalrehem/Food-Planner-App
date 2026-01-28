package com.hammi.foodplanner.ui.home.planner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.local.MealPlanEntity;
import com.hammi.foodplanner.ui.home.planner.presenter.MealPlanContract;
import com.hammi.foodplanner.ui.home.planner.presenter.MealPlanPresenter;


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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_planner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         initViews(view);
         presenter = new MealPlanPresenter(this, getContext());

        setupCalendar();
        setupRecyclerView();

        loadTodayMeals();
    }

    private void initViews(View view) {
        calendarView = view.findViewById(R.id.calendarView);
        recyclerView = view.findViewById(R.id.rvPlannedMeals);
        noDataTextView = view.findViewById(R.id.tvNoData);
    }

    private void setupCalendar() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedYear = year;
            selectedMonth = month;
            selectedDay = dayOfMonth;

            // Load meals for selected date
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
                         Toast.makeText(getContext(),
                                "Clicked: " + meal.getName(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDeleteClick(int planId) {
                         presenter.removeMealFromPlan(planId);
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
    public void showWeekMealsCount(int count) {}

    @Override
    public void showMealAddedSuccess() {
        Toast.makeText(getContext(), "Meal added to plan!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMealRemovedSuccess() {
        Toast.makeText(getContext(), "Meal removed from plan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}