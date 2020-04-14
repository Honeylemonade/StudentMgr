package com.example.studentmgr;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import com.example.studentmgr.adpater.StudentAdapter;
import com.example.studentmgr.dao.StudentDao;
import com.example.studentmgr.dao.StudentDaoImpl;
import com.example.studentmgr.entity.Student;
import com.example.studentmgr.listener.ScreenListener;

import java.util.ArrayList;


public class ConfigActivity extends PreferenceActivity {
    SwitchPreference switch_preference_1;
    ListPreference list_preference_1;
    public static ConfigActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        addPreferencesFromResource(R.xml.preferences);
        switch_preference_1 = (SwitchPreference) findPreference("switch_preference_1");
        switch_preference_1.setOnPreferenceChangeListener(new ScreenListener());
        //读取屏幕状态
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean s = sharedPreferences.getBoolean("switch_preference_1", false);
        if (s) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onDestroy() {
        StudentDao studentDao = new StudentDaoImpl(MainActivity.instance);
        ArrayList<Student> studentArrayList = studentDao.selectAll();
        MainActivity.instance.listView.setAdapter(new StudentAdapter(MainActivity.instance, studentArrayList));
        super.onDestroy();
    }
}
