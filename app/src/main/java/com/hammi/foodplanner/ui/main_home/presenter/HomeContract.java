package com.hammi.foodplanner.ui.main_home.presenter;

import android.content.Context;

public interface HomeContract {
    interface View {
        void showNoInternet();
        void hideNoInternet();
        void showLoading();
        int getCurrentFragmentId();
    }

    interface Presenter {
        void startCheckingConnection(Context context);
        void stopCheckingConnection();
        void onDestroy();
        void onFragmentChanged(int fragmentId);
        void onRetryClicked(Context context);
    }
}