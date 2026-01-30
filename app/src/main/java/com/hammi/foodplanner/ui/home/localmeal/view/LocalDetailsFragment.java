package com.hammi.foodplanner.ui.home.localmeal.view;

import android.app.Dialog;
import android.graphics.Color;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.local.MealEntity;
import com.hammi.foodplanner.data.models.remote.IngredientItem;
import com.hammi.foodplanner.ui.home.home.view.detials.DetialsHAdapter;
import com.hammi.foodplanner.ui.home.home.view.detials.DetailsVAdapter;
import com.hammi.foodplanner.ui.home.localmeal.presenter.LocalDetailsContract;
import com.hammi.foodplanner.ui.home.localmeal.presenter.LocalDetailsPresenter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class LocalDetailsFragment extends Fragment implements LocalDetailsContract.View {

    private ImageView ivRecipeImage;
    private TextView tvRecipeTitle, tvCuisine, tvCategory;
    private RecyclerView rvIngredients, rvSteps;
    private LocalDetailsPresenter presenter;
    private ImageButton btnBack;
    private WebView webViewVideo;
    private View cardVideo;
    private TextView btnWatchVideo;
    private Dialog loadingDialog;
    private MealEntity currentMeal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_details, container, false);
        ivRecipeImage = view.findViewById(R.id.ivRecipeImage);
        tvRecipeTitle = view.findViewById(R.id.tvRecipeTitle);
        tvCuisine = view.findViewById(R.id.tvCuisine);
        tvCategory = view.findViewById(R.id.tvCategory);
        rvIngredients = view.findViewById(R.id.rvIngredients);
        rvSteps = view.findViewById(R.id.rvSteps);
        btnBack = view.findViewById(R.id.btnBack);
        cardVideo = view.findViewById(R.id.cardVideo);
        webViewVideo = view.findViewById(R.id.webViewVideo);
        btnWatchVideo = view.findViewById(R.id.btnWatchVideo);

        rvIngredients.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));


        WebSettings webSettings = webViewVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webViewVideo.setWebViewClient(new WebViewClient());

         btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

         presenter = new LocalDetailsPresenter(this, getContext());

         String mealId = LocalDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        presenter.loadMealDetails(mealId);



        return view;
    }

    @Override
    public void showDetails(MealEntity meal) {
        if (meal == null) return;

        currentMeal = meal;
        tvRecipeTitle.setText(meal.getName());
        tvCuisine.setText(meal.getArea());
        tvCategory.setText(meal.getCategory());
        Glide.with(this).load(meal.getThumbnailUrl()).into(ivRecipeImage);

         List<IngredientItem> ingredients = parseIngredientsFromJson(meal.getIngredientsJson());
        DetialsHAdapter hAdapter = new DetialsHAdapter(ingredients);
        rvIngredients.setAdapter(hAdapter);

         String rawInstructions = meal.getInstructions();
        if (rawInstructions != null) {
            List<String> stepsList = Arrays.asList(rawInstructions.split("\\r\\n|\\n|\\. "));
            DetailsVAdapter vAdapter = new DetailsVAdapter(stepsList);
            rvSteps.setAdapter(vAdapter);
        }

         btnWatchVideo.setOnClickListener(v -> {
            if (meal.getYoutubeUrl() != null && !meal.getYoutubeUrl().isEmpty()) {
                cardVideo.setVisibility(View.VISIBLE);

                String videoUrl = meal.getYoutubeUrl();
                String videoId = videoUrl.substring(videoUrl.lastIndexOf("v=") + 2);
                String embedUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=1";
                webViewVideo.loadUrl(embedUrl);
            } else {
                Toast.makeText(getContext(), "No video available for this meal", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private List<IngredientItem> parseIngredientsFromJson(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<List<IngredientItem>>(){}.getType();
        return gson.fromJson(json, listType);
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