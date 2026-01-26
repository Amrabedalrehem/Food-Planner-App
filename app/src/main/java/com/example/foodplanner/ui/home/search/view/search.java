package com.example.foodplanner.ui.home.search.view;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.models.remote.Category;
import com.example.foodplanner.data.models.remote.Countries;
import com.example.foodplanner.data.models.remote.Ingredients;
import com.example.foodplanner.data.models.remote.Meal;
import com.example.foodplanner.ui.home.search.presenter.SearchContract;
import com.example.foodplanner.ui.home.search.presenter.SearchPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;


public class search extends Fragment implements SearchContract.View {

    private static final String TAG = "SearchFragment";


    private EditText etSearch;
    private ImageView icClear, btnBack;
    private ChipGroup chipGroupFilter, chipGroupSubFilter;
    private View scrollViewSubFilter;
    private TextView tvResultsCount;
    private RecyclerView rvMeals;
     private SearchAdpter mealAdapter;
    private final List<Meal> mealList = new ArrayList<>();
    private SearchPresenter presenter;
    private final Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;
    private static final long SEARCH_DELAY = 500;
    private String currentFilter = "All";

    public search() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         presenter = new SearchPresenter(this);
        Log.d(TAG, "onCreate: Presenter initialized");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: Initializing views");

         initViews(view);
         setupRecyclerView();
         setupListeners();
    }


    private void initViews(View view) {
        etSearch = view.findViewById(R.id.et_search);
        icClear = view.findViewById(R.id.ic_clear);
        btnBack = view.findViewById(R.id.allCountriesBack);
        chipGroupFilter = view.findViewById(R.id.chipGroupFilter);
        chipGroupSubFilter = view.findViewById(R.id.chipGroupSubFilter);
        scrollViewSubFilter = view.findViewById(R.id.scrollViewSubFilter);
        tvResultsCount = view.findViewById(R.id.tvResultsCount);
        rvMeals = view.findViewById(R.id.rvAllCountries);
        hideAllStates();
    }


    private void setupRecyclerView() {
        rvMeals.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mealAdapter = new SearchAdpter(mealList, mealId -> {
            searchDirections.ActionSearch3ToDetialsFragment2 action =
                    searchDirections.actionSearch3ToDetialsFragment2(mealId);
            NavHostFragment.findNavController(this).navigate(action);
        });
        rvMeals.setAdapter(mealAdapter);
        Log.d(TAG, "setupRecyclerView: RecyclerView configured");
    }
    private void setupListeners() {
         btnBack.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

         etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 icClear.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
                 if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }

                 if (currentFilter.equals("All") && s.length() > 0) {
                    searchRunnable = () -> {
                        Log.d(TAG, "Searching for: " + s.toString().trim());
                        clearMeals();
                        presenter.searchMealsByName(s.toString().trim());
                    };
                    searchHandler.postDelayed(searchRunnable, SEARCH_DELAY);
                }

                 if (s.length() == 0) {
                    clearMeals();
                    hideAllStates();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

         icClear.setOnClickListener(v -> {
            etSearch.setText("");
            clearMeals();
            hideAllStates();
        });
         chipGroupFilter.setOnCheckedChangeListener((group, checkedId) -> {
             clearMeals();
            hideAllStates();
            hideSubFilterChips();

            if (checkedId == R.id.chip_all) {
                currentFilter = "All";
                etSearch.setEnabled(true);
                etSearch.setText("");
                  etSearch.setHint("Search for meals...");
                Log.d(TAG, "Filter changed to: All");
            } else if (checkedId == R.id.chip_category) {
                currentFilter = "Category";
                etSearch.setEnabled(false);
                etSearch.setHint("Select a category");
                Log.d(TAG, "Filter changed to: Category - Loading categories...");
                presenter.loadCategories();
            } else if (checkedId == R.id.chip_country) {
                currentFilter = "Country";
                etSearch.setEnabled(false);
                etSearch.setHint("Select a country");
                Log.d(TAG, "Filter changed to: Country - Loading countries...");
                presenter.loadCountries();
            } else if (checkedId == R.id.chip_ingredient) {
                currentFilter = "Ingredient";
                etSearch.setEnabled(false);
                etSearch.setHint("Select an ingredient");
                Log.d(TAG, "Filter changed to: Ingredient - Loading ingredients...");
                presenter.loadIngredients();
            }
        });
    }

    @Override
    public void showMeals(List<Meal> meals) {
        Log.d(TAG, "showMeals: Received " + (meals != null ? meals.size() : 0) + " meals");

        clearMeals();
        updateUIState(meals);

        if (meals == null || meals.isEmpty()) {
            Log.w(TAG, "showMeals: No meals to display");
            return;
        }

        mealList.addAll(meals);
        mealAdapter.notifyDataSetChanged();
        Log.d(TAG, "showMeals: Adapter updated with " + meals.size() + " meals");
    }

    @Override
    public void showCategories(List<Category> categories) {
        Log.d(TAG, "showCategories: Received " + (categories != null ? categories.size() : 0) + " categories");

        if (categories == null || categories.isEmpty()) {
            showError("No categories available");
            return;
        }

        showSubFilterChips();
        chipGroupSubFilter.removeAllViews();

        for (Category category : categories) {
            Chip chip = createSubFilterChip(category.getStrCategory());
            chip.setOnClickListener(v -> {
                Log.d(TAG, "Category selected: " + category.getStrCategory());
                clearMeals();
                presenter.searchByCategory(category.getStrCategory());
            });
            chipGroupSubFilter.addView(chip);
        }

        Log.d(TAG, "showCategories: Added " + categories.size() + " chips");
    }

    @Override
    public void showCountries(List<Countries> countries) {
        Log.d(TAG, "showCountries: Received " + (countries != null ? countries.size() : 0) + " countries");

        if (countries == null || countries.isEmpty()) {
            showError("No countries available");
            return;
        }

        showSubFilterChips();
        chipGroupSubFilter.removeAllViews();

        for (Countries country : countries) {
            Chip chip = createSubFilterChip(country.getStrArea());
            chip.setOnClickListener(v -> {
                Log.d(TAG, "Country selected: " + country.getStrArea());
                clearMeals();
                presenter.searchByCountry(country.getStrArea());
            });
            chipGroupSubFilter.addView(chip);
        }

        Log.d(TAG, "showCountries: Added " + countries.size() + " chips");
    }

    @Override
    public void showIngredients(List<Ingredients> ingredients) {
        Log.d(TAG, "showIngredients: Received " + (ingredients != null ? ingredients.size() : 0) + " ingredients");

        if (ingredients == null || ingredients.isEmpty()) {
            showError("No ingredients available");
            return;
        }

        showSubFilterChips();
        chipGroupSubFilter.removeAllViews();
        int count = Math.min(ingredients.size(), 20);
        for (int i = 0; i < count; i++) {
            Ingredients ingredient = ingredients.get(i);
            Chip chip = createSubFilterChip(ingredient.getStrIngredient());
            chip.setOnClickListener(v -> {
                Log.d(TAG, "Ingredient selected: " + ingredient.getStrIngredient());
                clearMeals();
                presenter.searchByIngredient(ingredient.getStrIngredient());
            });
            chipGroupSubFilter.addView(chip);
        }

        Log.d(TAG, "showIngredients: Added " + count + " chips");
    }

    @Override
    public void showError(String message) {
        Log.e(TAG, "showError: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        Log.d(TAG, "showLoading: Showing loading state");
     }

    @Override
    public void hideLoading() {
        Log.d(TAG, "hideLoading: Hiding loading state");
     }

    @Override
    public void showEmptyState() {
        Log.d(TAG, "showEmptyState: Showing empty state");
        updateUIState(null);
    }

    @Override
    public void hideSubFilterChips() {
        scrollViewSubFilter.setVisibility(View.GONE);
        chipGroupSubFilter.removeAllViews();
        Log.d(TAG, "hideSubFilterChips: Sub filters hidden");
    }

    @Override
    public void showSubFilterChips() {
        scrollViewSubFilter.setVisibility(View.VISIBLE);
        Log.d(TAG, "showSubFilterChips: Sub filters shown");
    }


    private void updateUIState(List<Meal> meals) {
        if (meals == null || meals.isEmpty()) {
            rvMeals.setVisibility(View.GONE);
            tvResultsCount.setVisibility(View.VISIBLE);
            tvResultsCount.setText("No meals found");
            Log.d(TAG, "updateUIState: Empty state");
        } else {
            rvMeals.setVisibility(View.VISIBLE);
            tvResultsCount.setVisibility(View.VISIBLE);
            tvResultsCount.setText("Found " + meals.size() + " meals");
            Log.d(TAG, "updateUIState: Showing " + meals.size() + " meals");
        }
    }

    private void hideAllStates() {
        rvMeals.setVisibility(View.GONE);
        tvResultsCount.setVisibility(View.GONE);
        Log.d(TAG, "hideAllStates: All UI elements hidden");
    }

    private void clearMeals() {
        mealList.clear();
        mealAdapter.notifyDataSetChanged();
        Log.d(TAG, "clearMeals: Meal list cleared");
    }

    private Chip createSubFilterChip(String text) {
        Chip chip = new Chip(getContext());
        chip.setText(text);
        chip.setCheckable(false);
        chip.setChipBackgroundColorResource(R.color.white);
        chip.setChipStrokeColorResource(R.color.black);
        chip.setChipStrokeWidth(2f);
        chip.setTextColor(getResources().getColor(R.color.black));
        return chip;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Cleaning up resources");

         if (searchHandler != null) {
            searchHandler.removeCallbacksAndMessages(null);
        }

         if (presenter != null) {
            presenter.onDestroy();
        }
    }
}