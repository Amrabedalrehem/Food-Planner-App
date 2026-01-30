package com.hammi.foodplanner.ui.search.view;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
 import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.remote.Category;
import com.hammi.foodplanner.data.models.remote.Countries;
import com.hammi.foodplanner.data.models.remote.Ingredients;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.ui.search.presenter.SearchContract;
import com.hammi.foodplanner.ui.search.presenter.SearchPresenter;


import java.util.ArrayList;
import java.util.List;
public class filterby extends Fragment implements SearchContract.View {

    private ImageView btnBack;
    private TextView tvFindByTitle, tvFoundItem, tvEmptyState;
    private RecyclerView rvMeals;
    private SearchPresenter presenter;
    private List<Meal> mealList = new ArrayList<>();
    private SearchAdpter mealAdapter;
    private Handler searchHandler = new Handler(Looper.getMainLooper());

    public filterby() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filterby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBack = view.findViewById(R.id.backFalterBy);
        tvFindByTitle = view.findViewById(R.id.tvFindByTitle);
        tvFoundItem = view.findViewById(R.id.foundItem);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);
        rvMeals = view.findViewById(R.id.rvFilterBy);
        rvMeals.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mealAdapter = new SearchAdpter(mealList, mealId -> {
            filterbyDirections.ActionFilterbyToDetialsFragment2 action =
                    filterbyDirections.actionFilterbyToDetialsFragment2(mealId);
            NavHostFragment.findNavController(this).navigate(action);
        });

        rvMeals.setAdapter(mealAdapter);

        btnBack.setOnClickListener(v -> {
            if (getActivity() != null) getActivity().onBackPressed();
        });

        if (getArguments() != null) {
            // SafeArgs
            filterbyArgs args = filterbyArgs.fromBundle(getArguments());
            String titleFilter = args.getTitleFilter();
            String typeFilter = args.getTypeFilter();
            String valueFilter = args.getValueFilter();

            tvFindByTitle.setText(titleFilter);
            tvFoundItem.setText("Showing results for \"" + valueFilter + "\"");

            loadFilteredData(typeFilter, valueFilter);
        }
    }

    private void loadFilteredData(String typeFilter, String valueFilter) {
        if (typeFilter == null || valueFilter == null) return;

        switch (typeFilter) {
            case "Category":
                presenter.searchByCategory(valueFilter);
                break;
            case "Country":
                presenter.searchByCountry(valueFilter);
                break;
            case "Ingredient":
                presenter.searchByIngredient(valueFilter);
                break;
            default:
                break;
        }
    }

    @Override
    public void showMeals(List<Meal> meals) {
        mealList.clear();
        if (meals == null || meals.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            rvMeals.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            rvMeals.setVisibility(View.VISIBLE);
            mealList.addAll(meals);
        }
        mealAdapter.notifyDataSetChanged();
    }

    @Override public void showCategories(List<Category> categories) {}
    @Override public void showCountries(List<Countries> countries) {}
    @Override public void showIngredients(List<Ingredients> ingredients) {}
    @Override public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override public void showLoading() {}
    @Override public void hideLoading() {}
    @Override public void showEmptyState() {
        tvEmptyState.setVisibility(View.VISIBLE);
        rvMeals.setVisibility(View.GONE);
    }
    @Override public void hideSubFilterChips() {}
    @Override public void showSubFilterChips() {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (searchHandler != null) searchHandler.removeCallbacksAndMessages(null);
        if (presenter != null) presenter.onDestroy();
    }
}
