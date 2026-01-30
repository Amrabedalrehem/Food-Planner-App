package com.hammi.foodplanner.ui.main_home.presenter;

import android.content.Context;

public interface HomeContract {
    interface View {
        void showNoInternet();
        void hideNoInternet();
        void showLoading();
    }

    interface Presenter {
        void startCheckingConnection(Context context);
        void stopCheckingConnection();
        void onDestroy();
    }
}
