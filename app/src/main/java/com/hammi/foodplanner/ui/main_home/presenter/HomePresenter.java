package com.hammi.foodplanner.ui.main_home.presenter;

import android.content.Context;
import com.hammi.foodplanner.utility.NetworkUtils;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void startCheckingConnection(Context context) {
         NetworkUtils.startRealtimeNetworkMonitoring(context, new NetworkUtils.NetworkListener() {
            @Override
            public void onConnected() {
                if (view != null) view.hideNoInternet();
            }

            @Override
            public void onDisconnected() {
                if (view != null) view.showNoInternet();
            }
        });
    }

    @Override
    public void stopCheckingConnection() {
         if (view != null) {
            NetworkUtils.stopRealtimeNetworkMonitoring((Context) view);
        }
    }

    @Override
    public void onDestroy() {
        stopCheckingConnection();
        this.view = null;
    }
}
