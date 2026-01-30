package com.hammi.foodplanner.utility;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBar {

    private static final int DURATION = Snackbar.LENGTH_SHORT;
    private static final String ERROR_COLOR = "#F75200";
    private static final String SUCCESS_COLOR = "#FC6C00";

    private SnackBar() {
    }

    public static void showSuccess(View view, String msg) {
        show(view, msg, SUCCESS_COLOR);
    }

    public static void showError(View view, String msg) {
        show(view, msg, ERROR_COLOR);
    }

    public static void showGeneral(View view, String msg) {
        if (view == null) return;
        Snackbar.make(view, msg, DURATION).show();
    }

     private static void show(View view, String msg, String color) {
        if (view == null) return;

        Snackbar snackbar = Snackbar.make(view, msg, DURATION);
        snackbar.setBackgroundTint(Color.parseColor(color));
        snackbar.setTextColor(Color.WHITE);
        snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_FADE);

        snackbar.show();
    }
}
