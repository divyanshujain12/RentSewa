package com.example.divyanshujain.rentsewa.CustomViews;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;

/**
 * Created by divyanshu.jain on 12/29/2016.
 */
public class CustomTopSnackbars {
    private TSnackbar customErrorSnackbar = null;
    private TSnackbar customSuccessSnackbar = null;
    private static CustomTopSnackbars ourInstance = new CustomTopSnackbars();

    public static CustomTopSnackbars getInstance() {
        return ourInstance;
    }

    private CustomTopSnackbars() {
    }

    public TSnackbar getErrorSnackbar(View view) {
        if (customErrorSnackbar == null)
            customErrorSnackbar = createErrorCustomSnackBar(view);
        return customErrorSnackbar;
    }

    public TSnackbar getErrorSnackbar(Activity activity) {
        if (customErrorSnackbar == null)
            customErrorSnackbar = createErrorCustomSnackBar(activity);
        return customErrorSnackbar;
    }

    private TSnackbar createErrorCustomSnackBar(Activity context) {
        TSnackbar snackbar = TSnackbar
                .make(context.findViewById(android.R.id.content), "", TSnackbar.LENGTH_LONG);
        setUpCustomSnackBar(snackbar, Color.parseColor("#FF0000"));

        return snackbar;
    }

    private TSnackbar createErrorCustomSnackBar(View view) {
        TSnackbar snackbar = TSnackbar
                .make(view, "", TSnackbar.LENGTH_LONG);
        setUpCustomSnackBar(snackbar, Color.parseColor("#FF0000"));

        return snackbar;
    }

    private void setUpCustomSnackBar(TSnackbar snackbar, int color) {
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(color);
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
    }
}
