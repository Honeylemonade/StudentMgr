package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PhonePlaceActivity extends AppCompatActivity {

    EditText editText7;
    TextView textView26;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_place);
    }

    public void onFindClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    editText7 = findViewById(R.id.editText7);
                    String phoneNumber = editText7.getText().toString();
                    System.out.println(phoneNumber);
                    HttpURLConnection connection = null;
                    BufferedReader reader = null;
                    URL url = new URL("http://apis.juhe.cn/mobile/get");
                    connection = (HttpURLConnection) url.openConnection();
                    //设置请求方法
                    connection.setRequestMethod("POST");
                    //允许写出
                    connection.setDoOutput(true);
                    //允许读入
                    connection.setDoInput(true);
                    //不使用缓存
                    connection.setUseCaches(false);
                    //连接
                    connection.connect();
                    String body = "phone="+phoneNumber+"&dtype=json&key=f7b23b1c754516b26368521df06ff58e";
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();
                    //返回输入流
                    InputStream in = connection.getInputStream();

                    //读取输入流
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    textView26 = findViewById(R.id.textView26);
                    textView26.setText(result.toString());
                    reader.close();
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
