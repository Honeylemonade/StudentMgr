package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentmgr.dao.StudentDao;
import com.example.studentmgr.dao.StudentDaoImpl;
import com.example.studentmgr.entity.Student;
import com.example.studentmgr.listener.CollegeSpinnerListener;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        //TODO check5:注册了一级Spinner事件，动态改变二级spinner内容
        //注册spinner事件
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new CollegeSpinnerListener((Spinner) findViewById(R.id.spinner2), this));
    }

    /**
     * 用户点击提交按钮，检验学生信息，插入数据库，并跳转至主页面跳转至主页面
     *
     * @param view
     */
    public void submitHadler(View view) {
        EditText editText = findViewById(R.id.editText);
        String name = editText.getText().toString();
        EditText editText2 = findViewById(R.id.editText2);
        String id = editText2.getText().toString();
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

        Integer sex;
        if (radioButton.getText().toString().equals("男")) {
            System.out.println("/////////////////////////:选择的性别："+radioButton.getText().toString());
            sex = 0;
        } else {
            sex = 1;
        }
        Spinner spinner = findViewById(R.id.spinner);
        String college = spinner.getSelectedItem().toString();
        Spinner spinner2 = findViewById(R.id.spinner2);
        String profession = spinner2.getSelectedItem().toString();
        CheckBox checkBox = findViewById(R.id.checkBox);
        CheckBox checkBox2 = findViewById(R.id.checkBox2);
        CheckBox checkBox3 = findViewById(R.id.checkBox3);
        CheckBox checkBox4 = findViewById(R.id.checkBox4);
        ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
        checkBoxArrayList.add(checkBox);
        checkBoxArrayList.add(checkBox2);
        checkBoxArrayList.add(checkBox3);
        checkBoxArrayList.add(checkBox4);
        String hobbies = getCheckBoxResult(checkBoxArrayList);
        Student student = new Student(id, name, sex, college, profession, hobbies);
        System.out.println(student);
        StudentDao studentDao = new StudentDaoImpl(this);
        studentDao.insertStudent(student);
        //TODO check：toast通知
        Toast toast=Toast.makeText(this,"插入成功", Toast.LENGTH_SHORT);
        toast.show();
        //TODO check：页面跳转
        //创建intent,并附加消息
        Intent intent = new Intent(this, MainActivity.class);
        //想Android系统发出链接请求，并跳转界面
        startActivity(intent);
    }


    /**
     * 获取checkbox组的选择结果
     *
     * @param arrayList
     * @return
     */
    private String getCheckBoxResult(ArrayList<CheckBox> arrayList) {
        StringBuffer result = new StringBuffer();
        for (CheckBox checkBox : arrayList) {
            if (checkBox.isChecked()) {
                result.append(checkBox.getText() + ";");
            }
        }
        return result.toString();
    }
}
