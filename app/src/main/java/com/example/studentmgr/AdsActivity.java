package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class AdsActivity extends AppCompatActivity {
    public static int remainTime = 4;
    public static Handler handler = new Handler();
    TextView textView;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        init();
    }

    void init() {
        textView = findViewById(R.id.textView25);
        myTimer();
        context = this;
    }

//                    textView.setText(String.valueOf(remainTime));
//                    remainTime--;

    public void myTimer() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                textView.setText("跳过|广告剩余" + String.valueOf(remainTime) + "s");
                remainTime--;
                if (remainTime < 1) {
                    handler.removeCallbacks(this);
                    //创建intent,并附加消息
                    Intent intent = new Intent(AdsActivity.context, MainActivity.class);
                    //想Android系统发出链接请求，并跳转界面
                    startActivity(intent);
                }
                if (remainTime > 0) {
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public void skip(View view) {
        remainTime = 0;
    }
}
