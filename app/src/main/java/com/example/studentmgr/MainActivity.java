package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.example.studentmgr.adpater.StudentAdapter;
import com.example.studentmgr.dao.StudentDao;
import com.example.studentmgr.dao.StudentDaoImpl;
import com.example.studentmgr.entity.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据
        init();
    }

    /**
     * 初始化显示学生数据
     */
    private void init() {
        StudentDao studentDao = new StudentDaoImpl(this);
        ArrayList<Student> studentArrayList = studentDao.selectAll();
        //获取数据库中学生信息，进行渲染
        ListView listView = (ListView) findViewById(R.id.listView);
        Collections.reverse(studentArrayList);
        //TODO check3：通过Adapter设置listView数据与样式
        listView.setAdapter(new StudentAdapter(this, studentArrayList));
    }

    /**
     * 点击了主界面的添加学生按钮，触发事件，跳转界面
     *
     * @param view
     */
    public void jumpBTNonclick(View view) {
        //创建intent,并附加消息
        Intent intent = new Intent(this, StudentActivity.class);
        //想Android系统发出链接请求，并跳转界面
        startActivity(intent);
    }
}
