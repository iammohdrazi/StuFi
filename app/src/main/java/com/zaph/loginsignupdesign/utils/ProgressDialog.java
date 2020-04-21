package com.zaph.loginsignupdesign.utils;


import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.zaph.loginsignupdesign.R;

/**
 * Class that holds ready to use dialogs
 */
public class ProgressDialog {

    private static final String TAG = ProgressDialog.class.getSimpleName();

    private static Activity activity;
    private static Dialog progressDialog;
    private static TextView tvProgress;
    private static ProgressBar progressBar;

    /**
     * Shows the progress dialog
     *
     * @param activity
     * @return the {@link TextView} on which progress has to be set
     */
    public static void show(Activity activity) {
        show(activity, "");
    }

    /**
     * Method to init the progress dialog with a message
     * @param context
     * @param message
     * @return
     */
    public static void show(final Activity context, final String message) {

        activity = context;

        try {
            /* Check if the last instance is alive */
            if (progressDialog != null)
                if (progressDialog.isShowing()) {
                    tvProgress.setText(message);
                    return;
                }
            /*  Ends Here   */

            progressDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
            progressDialog.setContentView(R.layout.dialog_progress);
            progressBar=progressDialog.findViewById(R.id.progress);
            progressBar.setIndeterminateDrawable(new CubeGrid());
            tvProgress = progressDialog.findViewById(R.id.tvProgress);
            tvProgress.setText(message);
            tvProgress.setVisibility(View.GONE);

            Window dialogWindow = progressDialog.getWindow();
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.dimAmount = 0.6f;
            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Dismisses the Progress Dialog
     */
    public static void dismiss() {
        // Check if activity lives
        if (activity != null)
            // Check if the Dialog is null
            if (progressDialog != null)
                // Check whether the progressDialog is visible
                if (progressDialog.isShowing()) {
                    try {
                        // Dismiss the Dialog
                        progressDialog.dismiss();
                        progressDialog = null;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
    }
}
