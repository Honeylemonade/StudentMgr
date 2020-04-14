package com.example.studentmgr.listener;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.Preference;

import com.example.studentmgr.ConfigActivity;
import com.example.studentmgr.MainActivity;
import com.example.studentmgr.PhonePlaceActivity;
import com.example.studentmgr.StudentActivity;

import static android.content.Context.MODE_PRIVATE;

public class ScreenListener implements Preference.OnPreferenceChangeListener {

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (ConfigActivity.instance.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            //改变为已有状态
            ConfigActivity.instance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            MainActivity.instance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            PhonePlaceActivity.instance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            StudentActivity.instance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        } else {
            ConfigActivity.instance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            MainActivity.instance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            PhonePlaceActivity.instance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            StudentActivity.instance.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        return true;
    }
}
