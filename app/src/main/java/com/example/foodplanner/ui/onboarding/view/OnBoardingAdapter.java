package com.example.foodplanner.ui.onboarding.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class OnBoardingAdapter extends FragmentStateAdapter {
    public OnBoardingAdapter(@NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case  0 :
                return  new OnboardingFragment1();
            case 1:
                return  new OnboardingFragment2();
            case 2:
                return new OnboardingFragment3();
            default:
                return  new OnboardingFragment4();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
