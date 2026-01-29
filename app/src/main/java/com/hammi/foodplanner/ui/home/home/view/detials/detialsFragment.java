package com.hammi.foodplanner.ui.home.home.view.detials;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.remote.Meal;
import com.hammi.foodplanner.ui.home.home.presentation.Details.DetailsContract;
import com.hammi.foodplanner.ui.home.home.presentation.Details.DetailsPresenter;
import com.google.android.material.button.MaterialButton;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class detialsFragment extends Fragment implements DetailsContract.View {

    private ImageView ivRecipeImage;
    private TextView tvRecipeTitle, tvCuisine, tvCategory;
    private RecyclerView rvIngredients, rvSteps;
    private DetailsPresenter presenter;
    private ImageButton btnBack, btnFavorite;
    private MaterialButton btnAddToPlan;
    private MaterialButton btnWatchVideo;
    private WebView webViewVideo;
    private View cardVideo;

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
        webViewVideo = view.findViewById(R.id.webViewVideo);
        btnWatchVideo = view.findViewById(R.id.btnWatchVideo);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        btnFavorite.setOnClickListener(v -> presenter.toggleFavorite());

        btnAddToPlan.setOnClickListener(v -> presenter.onAddToPlanClicked());
    }

    private void setupConfigs() {
        rvIngredients.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));

        WebSettings webSettings = webViewVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webViewVideo.setWebViewClient(new WebViewClient());
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
            List<String> stepsList = Arrays.asList(rawInstructions.split("\\r\\n|\\n|\\. "));
            DetailsVAdapter vAdapter = new DetailsVAdapter(stepsList);
            rvSteps.setAdapter(vAdapter);
        }

        btnWatchVideo.setOnClickListener(v -> {
            if (currentMeal.getStrYoutube() != null && !currentMeal.getStrYoutube().isEmpty()) {
                cardVideo.setVisibility(View.VISIBLE);
                String videoUrl = currentMeal.getStrYoutube();
                String videoId = videoUrl.substring(videoUrl.lastIndexOf("v=") + 2);
                String embedUrl = "https://www.youtube.com/embed/" + videoId;
                webViewVideo.loadUrl(embedUrl);
            }
        });
    }

    @Override
    public void updateFavoriteButton(boolean isFavorite) {
        if (isFavorite) {
             btnFavorite.setImageResource(R.drawable.iconheart);
             btnFavorite.setImageTintList(null);
        } else {
             btnFavorite.setImageResource(R.drawable.save);
             btnFavorite.setImageTintList(null);
        }
    }

    @Override
    public void showFavoriteAdded() {
        Toast.makeText(getContext(), "Added to Favorites!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFavoriteRemoved() {
        Toast.makeText(getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddToPlanDialog() {
        final Calendar c = Calendar.getInstance();
        new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            presenter.addMealToPlan(year, month, dayOfMonth);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void showMealAddedToPlan() {
        Toast.makeText(getContext(), "Added to your Weekly Plan!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMealPlanError(String message) {

    }

    @Override
    public void showLoading() {   }

    @Override
    public void hideLoading() {  }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}