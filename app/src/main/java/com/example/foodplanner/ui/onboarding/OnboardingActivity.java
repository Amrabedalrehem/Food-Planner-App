package com.example.foodplanner.ui.onboarding;

import android.os.Bundle;
import android.widget.TableLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodplanner.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        OnBoardingAdapter onBoardingAdapter = new OnBoardingAdapter(this);
        viewPager2.setAdapter(onBoardingAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout,viewPager2,(tab, i) -> {}).attach();

    }
}