package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmgr.dao.AdminDao;
import com.example.studentmgr.dao.AdminDaoImpl;
import com.example.studentmgr.entity.Admin;
import com.example.studentmgr.util.AdminChecker;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginBTNOnClick(View view) throws InterruptedException {
        //获取填写信息
        EditText editText = findViewById(R.id.editText3);
        String userName = editText.getText().toString();
        EditText editText2 = findViewById(R.id.editText4);
        String password = editText2.getText().toString();
        final Admin admin = new Admin(userName, password);
        //显示进度条
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        final InheritableThreadLocal<Context> inheritableThreadLocal = new InheritableThreadLocal<>();
        final InheritableThreadLocal<Admin> inheritableThreadLocal2 = new InheritableThreadLocal<>();
        inheritableThreadLocal.set(this);
        inheritableThreadLocal2.set(admin);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //要执行的任务
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.INVISIBLE);
                if (AdminChecker.check(inheritableThreadLocal.get(), inheritableThreadLocal2.get())) {
                    Toast toast = Toast.makeText(inheritableThreadLocal.get(), "登入成功", Toast.LENGTH_SHORT);
                    toast.show();
                    //跳转到学生列表
                    //创建intent,并附加消息
                    Intent intent = new Intent(inheritableThreadLocal.get(), MainActivity.class);
                    //想Android系统发出链接请求，并跳转界面
                    startActivity(intent);
                } else {
                    //验证失败
                    Toast toast = Toast.makeText(inheritableThreadLocal.get(), "登入失败", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        }, 3000);

    }

    /**
     * 延迟3s，取消进度条并作出
     */
    public void ProgressBar() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

    }
}