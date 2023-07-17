package com.example.rathaanelectronics;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.example.rathaanelectronics.Managers.ConnectivityReceiver;

public class MainApplication extends Application {
    public static final String TAG = MainApplication.class.getSimpleName();


    private static MainApplication mInstance;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MainApplication getInstance() {
        return mInstance;
    }



//    public RequestQueue getRequestQueue() {
//        if (mRequestQueue == null) {
//            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
//        }
//        return mRequestQueue;
//    }

//    public <T> void addToRequestQueue(Request<T> req, String tag) {
//        // set the bg_splash_screen tag if tag is empty
//        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//        getRequestQueue().add(req);
//    }
//
//    public <T> void addToRequestQueue(Request<T> req) {
//        req.setTag(TAG);
//        getRequestQueue().add(req);
//    }
//
//    public void cancelPendingRequests(Object tag) {
//        if (mRequestQueue != null) {
//            mRequestQueue.cancelAll(tag);
//        }
    //}

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("Config changed","bllllllllllllaaaaaaaaaaaaaaaa");
    }
}


