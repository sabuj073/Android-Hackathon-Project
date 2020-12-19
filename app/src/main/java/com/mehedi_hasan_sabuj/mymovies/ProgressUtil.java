package com.mehedi_hasan_sabuj.mymovies;

import android.app.ProgressDialog;

public class ProgressUtil {

    public static void showProgressDialog(ProgressDialog pDialog) {
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public static void hideProgressDialog(ProgressDialog pDialog) {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
