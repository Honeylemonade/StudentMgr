package com.example.studentmgr.util;

import android.widget.Spinner;

/**
 * 根据给定字符串设定spinner当前状态
 */
public class SpinnerSelecter {
    public static boolean collegeSelect(Spinner spinner, String str) {
        switch (str) {
            case "计算机学院":
                spinner.setSelection(0);
                break;
            case "汽车学院":
                spinner.setSelection(1);
                break;
            case "电气学院":
                spinner.setSelection(2);
                break;
        }
        return true;
    }

    public static boolean professionSelect(Spinner spinner, String str) {
        switch (str) {
            case "软件工程专业":
            case "新能源专业":
            case "控制科学与工程专业":
                spinner.setSelection(0);
                break;
            case "计算机与科学专业":
            case "汽车电子技术专业":
            case "农业电气化与自动化专业":
                spinner.setSelection(1);
                break;
            case "信息安全专业":
            case "车辆工程专业":
            case "控制科学专业":
                spinner.setSelection(2);
                break;
        }
        return true;
    }
}
