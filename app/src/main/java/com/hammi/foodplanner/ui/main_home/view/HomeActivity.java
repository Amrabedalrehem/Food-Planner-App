package com.hammi.foodplanner.ui.main_home.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.ui.main_home.presenter.HomeContract;
import com.hammi.foodplanner.ui.main_home.presenter.HomePresenter;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    private HomePresenter presenter;
    private View noInternetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

         BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setPopUpTo(navController.getGraph().getStartDestinationId(), false)
                    .build();

            try {
                navController.navigate(item.getItemId(), null, navOptions);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        });

         presenter = new HomePresenter(this);
        noInternetLayout = findViewById(R.id.no_internet_layout);
        Button btnRetry = noInternetLayout.findViewById(R.id.btnRetry);

         presenter.startCheckingConnection(this);

         btnRetry.setOnClickListener(v -> {
             presenter.startCheckingConnection(this);
        });
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showNoInternet() {
        runOnUiThread(() -> noInternetLayout.setVisibility(View.VISIBLE));
    }

    @Override
    public void hideNoInternet() {
        runOnUiThread(() -> noInternetLayout.setVisibility(View.GONE));
    }

    @Override
    public void showLoading() {
     }
}
