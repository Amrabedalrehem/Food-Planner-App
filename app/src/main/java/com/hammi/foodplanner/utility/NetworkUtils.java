package com.hammi.foodplanner.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

public class NetworkUtils {

     public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) return false;
        Network network = connectivityManager.getActiveNetwork();
        if (network == null) return false;
        NetworkCapabilities capabilities =
                connectivityManager.getNetworkCapabilities(network);
        if (capabilities == null) return false;

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }

     public interface NetworkListener {
        void onConnected();
        void onDisconnected();
    }

    private static ConnectivityManager.NetworkCallback networkCallback;

     public static void startRealtimeNetworkMonitoring(Context context, NetworkListener listener) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) return;

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        networkCallback = new ConnectivityManager.NetworkCallback() {

            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                if (listener != null) listener.onConnected();
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
                if (listener != null) listener.onDisconnected();
            }
        };

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
    }

     public static void stopRealtimeNetworkMonitoring(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }
}
