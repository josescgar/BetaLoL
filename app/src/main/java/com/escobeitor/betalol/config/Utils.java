package com.escobeitor.betalol.config;

import android.app.Application;
import android.app.ProgressDialog;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

/**
 * Configuration constants
 * Created by escobeitor on 1/21/15.
 */
@EBean(scope = Scope.Singleton)
public class Utils {

    public static final String LOL_API_KEY = "XXXXXX";

    public static final String LOG_TAG = "BETALOL";

    @RootContext
    Application appContext;

    @UiThread
    public void showToast(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_LONG).show();
    }

}
