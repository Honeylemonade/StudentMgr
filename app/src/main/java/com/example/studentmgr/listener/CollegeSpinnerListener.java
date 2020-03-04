package com.example.studentmgr.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmgr.R;

/**
 * 监听学院spinner事件
 */
public class CollegeSpinnerListener implements AdapterView.OnItemSelectedListener {
    private ArrayAdapter<String> adapter;
    private Spinner professionSpinner;
    private Context parentContext;

    public CollegeSpinnerListener(Spinner professionSpinner, Context parentContext) {
        this.professionSpinner = professionSpinner;
        this.parentContext = parentContext;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        adapter = new ArrayAdapter<String>(parentContext, R.layout.support_simple_spinner_dropdown_item);
        professionSpinner.setAdapter(adapter);
        switch (selected) {
            case "计算机学院":
                System.out.println("/////////////////计算机学院");
                adapter.add("软件工程专业");
                adapter.add("计算机与科学专业");
                adapter.add("信息安全专业");
                break;
            case "汽车学院":
                System.out.println("/////////////////汽车学院");
                adapter.add("新能源专业");
                adapter.add("汽车电子技术专业");
                adapter.add("车辆工程专业");
                break;
            case "电气学院":
                System.out.println("/////////////////电气学院");
                adapter.add("控制科学与工程专业");
                adapter.add("农业电气化与自动化专业");
                adapter.add("控制科学专业");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
