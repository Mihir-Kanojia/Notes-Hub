package com.example.noteshub;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class CoreApp extends Application {

    private static CoreApp mInstance;
    private SharedPreferences sharedPreferences;
    private Activity activity;

    public static CoreApp getInstance() {
        return mInstance;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mInstance != null) {
            mInstance = null;
        }
    }
}
