package com.example.noteshub.managers;

import android.app.Activity;
import android.content.Intent;

import com.example.noteshub.R;

public class ActivitySwitchManager {

    Activity activity;
    Class NewActivity;
    Intent mMenuIntent;

    public ActivitySwitchManager(Activity activity, Class newActivity) {
        this.activity = activity;
        NewActivity = newActivity;
        this.mMenuIntent = new Intent(activity, newActivity);
    }

    public void openActivity(){


        mMenuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(mMenuIntent);
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void openActivityWithoutSlide(){
        mMenuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(mMenuIntent);
        activity.finish();
    }
    public void openActivityWithoutFinish(){

        activity.startActivity(mMenuIntent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void openActivityWithoutFinish(String key, String value){
        mMenuIntent.putExtra(key, value);
        openActivityWithoutFinish();
    }

    public void openActivityWithoutFinish(String key, String value, String key2, String value2){
        mMenuIntent.putExtra(key, value);
        mMenuIntent.putExtra(key2, value2);
        openActivityWithoutFinish();
    }

}
