package com.hammi.foodplanner.ui.home.view.detials;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.data.repository.sharedprefs.SharedPrefsRepository;
import com.hammi.foodplanner.ui.auth.view.AuthenticationActivity;
import com.hammi.foodplanner.ui.home.presentation.Details.DetailsContract;
import com.hammi.foodplanner.ui.home.presentation.Details.DetailsPresenter;
import com.hammi.foodplanner.utility.SnackBar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class detialsFragment extends Fragment implements DetailsContract.View {
    private Dialog loadingDialog;
    private ImageView ivRecipeImage;
    private TextView tvRecipeTitle, tvCuisine, tvCategory;
    private RecyclerView rvIngredients, rvSteps;
    private DetailsPresenter presenter;
    private ImageButton btnBack, btnFavorite;
    private MaterialButton btnAddToPlan;
    private MaterialButton btnWatchVideo;
    private YouTubePlayerView youtubePlayerView;
    private View cardVideo;
    private View view;
    private SharedPrefsRepository sharedPrefsRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefsRepository = new SharedPrefsRepository(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detials, container, false);

        initViews(view);
        setupConfigs();
        presenter = new DetailsPresenter(this, getContext());
        String mealId = detialsFragmentArgs.fromBundle(getArguments()).getId();
        presenter.getDetails(mealId);
        setupClickListeners();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        getLifecycle().addObserver(youtubePlayerView);
    }

    private void initViews(View view) {
        ivRecipeImage = view.findViewById(R.id.ivRecipeImage);
        tvRecipeTitle = view.findViewById(R.id.tvRecipeTitle);
        tvCuisine = view.findViewById(R.id.tvCuisine);
        tvCategory = view.findViewById(R.id.tvCategory);
        rvIngredients = view.findViewById(R.id.rvIngredients);
        rvSteps = view.findViewById(R.id.rvSteps);
        btnBack = view.findViewById(R.id.btnBack);
        btnFavorite = view.findViewById(R.id.btnFavorite);
        btnAddToPlan = view.findViewById(R.id.btnAddWeeklyPlan);
        cardVideo = view.findViewById(R.id.cardVideo);
        youtubePlayerView = view.findViewById(R.id.youtubePlayerView);
        btnWatchVideo = view.findViewById(R.id.btnWatchVideo);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        btnFavorite.setOnClickListener(v -> {
            sharedPrefsRepository.getUserMode().subscribe(mode -> {
                if ("guest".equalsIgnoreCase(mode)) {
                    showLoginAlert();
                } else {
                    presenter.toggleFavorite();
                }
            });
        });

        btnAddToPlan.setOnClickListener(v -> {
            sharedPrefsRepository.getUserMode().subscribe(mode -> {
                if ("guest".equalsIgnoreCase(mode)) {
                    showLoginAlert();
                } else {
                    presenter.onAddToPlanClicked();
                }
            });
        });
    }

    private void setupConfigs() {
        rvIngredients.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void showDetails(Meal currentMeal) {
        tvRecipeTitle.setText(currentMeal.getStrMeal());
        tvCuisine.setText(currentMeal.getStrArea());
        tvCategory.setText(currentMeal.getStrCategory());
        Glide.with(this).load(currentMeal.getStrMealThumb()).into(ivRecipeImage);
        DetialsHAdapter hAdapter = new DetialsHAdapter(currentMeal.getFullIngredients());
        rvIngredients.setAdapter(hAdapter);
        String rawInstructions = currentMeal.getStrInstructions();
        if (rawInstructions != null) {
            List<String> stepsList = new ArrayList<>();
            for (String s : rawInstructions.split("\\r?\\n|\\.\\s*")) {
                String trim = s.trim();
                if (!trim.isEmpty()) {
                    stepsList.add(trim);
                }
            }

            DetailsVAdapter vAdapter = new DetailsVAdapter(stepsList);
            rvSteps.setAdapter(vAdapter);
        }

        btnWatchVideo.setOnClickListener(v -> {
            if (currentMeal.getStrYoutube() != null && !currentMeal.getStrYoutube().isEmpty()) {
                cardVideo.setVisibility(View.VISIBLE);
                String videoUrl = currentMeal.getStrYoutube();
                String videoId = videoUrl.substring(videoUrl.lastIndexOf("v=") + 2);

                youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        youTubePlayer.loadVideo(videoId, 0);
                    }
                });
            }
        });
    }

    @Override
    public void updateFavoriteButton(boolean isFavorite) {
        if (isFavorite) {
            btnFavorite.setImageResource(R.drawable.save);
            btnFavorite.setImageTintList(null);
        } else {
            btnFavorite.setImageResource(R.drawable.iconheart);
            btnFavorite.setImageTintList(null);
        }
    }

    @Override
    public void showFavoriteAdded() {
        SnackBar.showSuccess(view, "Meal added to  favorites ");
    }

    @Override
    public void showFavoriteRemoved() {
        SnackBar.showSuccess(view, "Meal removed from favorites! ");
    }

    @Override
    public void showAddToPlanDialog() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> presenter.addMealToPlan(year, month, dayOfMonth),
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();

    }

    @Override
    public void showMealAddedToPlan() {
        SnackBar.showSuccess(view, "Added to your Weekly Plan!");
    }

    @Override
    public void showMealPlanError(String message) {
        SnackBar.showSuccess(view, "Error adding to your Weekly Plan!");
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

    private void showLoginAlert() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Login Required")
                .setMessage("Please login first to use this feature.")
                .setCancelable(true)
                .setPositiveButton("Login", (dialog, which) -> {
                    startActivity(new Intent(requireContext(), AuthenticationActivity.class));
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youtubePlayerView.release();
        presenter.onDestroy();
    }
}