package com.example.studentmgr.listener;

import android.app.ActivityOptions;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.studentmgr.MainActivity;
import com.example.studentmgr.StudentActivity;
import com.example.studentmgr.entity.Student;

public class StudentGestureListener implements GestureDetector.OnGestureListener {

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (velocityX > 500) {
//创建intent,并附加消息
            Intent intent = new Intent(StudentActivity.instance, MainActivity.class);
            //想Android系统发出链接请求，并跳转界面
            StudentActivity.instance.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(StudentActivity.instance).toBundle());
        }
        return false;
    }
}
