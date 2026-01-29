package com.hammi.foodplanner.ui.onboarding.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.hammi.foodplanner.R;
import com.hammi.foodplanner.ui.auth.view.AuthenticationActivity;
import com.hammi.foodplanner.ui.onboarding.presenter.OnboardingContract;
import com.hammi.foodplanner.ui.onboarding.presenter.OnboardingPresenter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
public class OnboardingActivity extends AppCompatActivity implements OnboardingContract.View {

    private OnboardingPresenter presenter;
    private ViewPager2 viewPager2;
    private Button btnNext, btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);

        presenter = new OnboardingPresenter(this, this);

         viewPager2 = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

         OnBoardingAdapter onBoardingAdapter = new OnBoardingAdapter(this);
        viewPager2.setAdapter(onBoardingAdapter);

         new TabLayoutMediator(tabLayout, viewPager2, (tab, i) -> {}).attach();

         btnSkip.setOnClickListener(v -> {
            presenter.completeOnboarding();
        });

         btnNext.setOnClickListener(v -> {
            if (viewPager2.getCurrentItem() + 1 < onBoardingAdapter.getItemCount()) {
                 viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            } else {
                 presenter.completeOnboarding();
            }
        });

         viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == onBoardingAdapter.getItemCount() - 1) {
                    btnNext.setText("Finish");
                    btnSkip.setVisibility(View.GONE);
                } else {
                    btnNext.setText("Next");
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void navigateToAuthentication() {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}