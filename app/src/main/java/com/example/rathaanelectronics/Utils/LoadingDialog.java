package com.example.rathaanelectronics.Utils;

/*
 *Created by Adithya T Raj on 26-04-2021
 */

import android.app.ProgressDialog;
import android.content.Context;

import com.example.rathaanelectronics.R;


public class LoadingDialog {

    static ProgressDialog progressDialog;

    public static void showLoadingDialog(Context context, String message) {

        message = context.getString(R.string.loading_title_text);
        if (!(progressDialog != null && progressDialog.isShowing())) {
            progressDialog = new ProgressDialog(context,R.style.MyAlertDialogStyle);
            progressDialog.setMessage("");

            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            try {
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void cancelLoading() {
        if (progressDialog != null && progressDialog.isShowing())
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}