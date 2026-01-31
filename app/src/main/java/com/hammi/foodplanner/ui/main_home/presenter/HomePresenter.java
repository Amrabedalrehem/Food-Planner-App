package com.hammi.foodplanner.ui.main_home.presenter;

import android.content.Context;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.utility.NetworkUtils;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private boolean isInternetAvailable = true;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void startCheckingConnection(Context context) {
        NetworkUtils.startRealtimeNetworkMonitoring(context, new NetworkUtils.NetworkListener() {
            @Override
            public void onConnected() {
                isInternetAvailable = true;
                if (view != null) {
                    view.hideNoInternet();
                }
            }

            @Override
            public void onDisconnected() {
                isInternetAvailable = false;
                if (view != null) {
                     int currentFragmentId = view.getCurrentFragmentId();
                    if (isFragmentRequiresInternetCheck(currentFragmentId)) {
                        view.showNoInternet();
                    }
                }
            }
        });
    }

    @Override
    public void onFragmentChanged(int fragmentId) {
        if (view == null) return;

         if (!isInternetAvailable && isFragmentRequiresInternetCheck(fragmentId)) {
            view.showNoInternet();
        } else {
            view.hideNoInternet();
        }
    }

    @Override
    public void onRetryClicked(Context context) {
         startCheckingConnection(context);
    }


    private boolean isFragmentRequiresInternetCheck(int fragmentId) {
        return fragmentId == R.id.home_Ffragment || fragmentId == R.id.search3;
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