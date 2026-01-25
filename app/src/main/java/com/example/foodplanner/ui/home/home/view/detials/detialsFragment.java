package com.example.foodplanner.ui.home.home.view.detials;

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
import com.example.foodplanner.R;
import com.example.foodplanner.data.models.Meal;
import com.example.foodplanner.ui.home.home.presentation.Details.DetailsContract;
import com.example.foodplanner.ui.home.home.presentation.Details.DetailsPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class detialsFragment extends Fragment implements DetailsContract.View {

    private ImageView ivRecipeImage;
    private TextView tvRecipeTitle, tvCuisine, tvCategory;
    private RecyclerView rvIngredients, rvSteps;
    private DetailsPresenter presenter;
    private ImageButton btnBack, btnFavorite;
    private WebView webViewVideo;
    private View cardVideo;
    private TextView btnWatchVideo;

    private Meal currentMeal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detials, container, false);

        ivRecipeImage = view.findViewById(R.id.ivRecipeImage);
        tvRecipeTitle = view.findViewById(R.id.tvRecipeTitle);
        tvCuisine = view.findViewById(R.id.tvCuisine);
        tvCategory = view.findViewById(R.id.tvCategory);
        rvIngredients = view.findViewById(R.id.rvIngredients);
        rvSteps = view.findViewById(R.id.rvSteps);

        btnBack = view.findViewById(R.id.btnBack);
        btnFavorite = view.findViewById(R.id.btnFavorite);
        cardVideo = view.findViewById(R.id.cardVideo);
        webViewVideo = view.findViewById(R.id.webViewVideo);
        btnWatchVideo = view.findViewById(R.id.btnWatchVideo);

        rvIngredients.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));

         WebSettings webSettings = webViewVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webViewVideo.setWebViewClient(new WebViewClient());

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNavigationView);
        navBar.getMenu().setGroupCheckable(0, true, false);
        for (int i = 0; i < navBar.getMenu().size(); i++) {
            navBar.getMenu().getItem(i).setChecked(false);
        }
         btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        String mealId = detialsFragmentArgs.fromBundle(getArguments()).getId();
        presenter = new DetailsPresenter(mealId, this);
        presenter.getDetails();

        return view;
    }

    @Override
    public void showDetails(List<Meal> details) {
        if (details != null && !details.isEmpty()) {
            currentMeal = details.get(0);

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
                    String embedUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=1";
                    webViewVideo.loadUrl(embedUrl);
                } else {
                    Toast.makeText(getContext(), "No video available for this meal", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void showLoading() { }

    @Override
    public void hideLoading() { }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}
