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
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        initViews();
        setupNavigation();
        setupPresenter();
    }


    private void initViews() {
        noInternetLayout = findViewById(R.id.no_internet_layout);
        Button btnRetry = findViewById(R.id.btnRetry);

        btnRetry.setOnClickListener(v -> presenter.onRetryClicked(this));
    }

    private void setupNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

         navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination != null && presenter != null) {
                presenter.onFragmentChanged(destination.getId());
            }
        });

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
    }


    private void setupPresenter() {
        presenter = new HomePresenter(this);
        presenter.startCheckingConnection(this);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }


    @Override
    public void showNoInternet() {
        runOnUiThread(() -> {
            if (noInternetLayout != null) {
                noInternetLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideNoInternet() {
        runOnUiThread(() -> {
            if (noInternetLayout != null) {
                noInternetLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getCurrentFragmentId() {
        if (navController != null && navController.getCurrentDestination() != null) {
            return navController.getCurrentDestination().getId();
        }
        return -1;
    }

    @Override
    public void showLoading() {
     }
}