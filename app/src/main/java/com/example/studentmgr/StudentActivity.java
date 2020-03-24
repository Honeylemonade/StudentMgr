package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Slide;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentmgr.dao.StudentDao;
import com.example.studentmgr.dao.StudentDaoImpl;
import com.example.studentmgr.entity.Student;
import com.example.studentmgr.listener.CollegeSpinnerListener;
import com.example.studentmgr.listener.StudentGestureListener;
import com.example.studentmgr.util.DatePickerFragment;
import com.example.studentmgr.util.SpinnerSelecter;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    EditText editText;
    EditText editText2;
    EditText editText6;
    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton radioButton2;
    Spinner spinner;
    Spinner spinner2;
    CheckBox checkBox;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    public static StudentActivity instance;
    //定义手势监听器
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一次进入时使用
        getWindow().setEnterTransition(new Slide(Gravity.RIGHT));
        //再次进入时使用
        getWindow().setReenterTransition(new Slide(Gravity.RIGHT));
        //退出时使用
        getWindow().setExitTransition(new Fade(Fade.OUT));
        setContentView(R.layout.activity_student);
        //注册spinner事件
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new CollegeSpinnerListener((Spinner) findViewById(R.id.spinner2), this));
        init();
        //如果是从修改item跳转过来的，则初始化选项，并且修改按钮
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (id != null) {
            updateInit(id);
        }
        //手势监听器
        gestureDetector = new GestureDetector(this, new StudentGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void updateInit(String id) {
        StudentDao studentDao = new StudentDaoImpl(this);
        Student student = studentDao.selectStudentById(id);
        editText.setText(student.getName());
        editText2.setText(student.getId());
        editText6.setText(student.getBirthday());
        if (student.getSex() == 0) {
            radioGroup.check(radioButton.getId());
        } else {
            radioGroup.check(radioButton2.getId());
        }
        SpinnerSelecter.collegeSelect(spinner, student.getCollege());
        final InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        inheritableThreadLocal.set(student.getProfession());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //要执行的任务
                //SpinnerSelecter.professionSelect(spinner2, student.getProfession());
                SpinnerSelecter.professionSelect(spinner2, inheritableThreadLocal.get());
            }
        }, 100);
        if (student.getHobbies().contains("文学")) {
            checkBox.setChecked(true);
        }
        if (student.getHobbies().contains("体育")) {
            checkBox2.setChecked(true);
        }
        if (student.getHobbies().contains("音乐")) {
            checkBox3.setChecked(true);
        }
        if (student.getHobbies().contains("美术")) {
            checkBox4.setChecked(true);
        }
        Button button = findViewById(R.id.button);
        button.setText("确认更新");
        //学号editText禁止修改
        editText2.setFocusable(false);
        editText2.setFocusableInTouchMode(false);


    }

    public void init() {
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText6 = findViewById(R.id.editText6);
        editText6.setFocusable(false);
        editText6.setFocusableInTouchMode(false);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        instance = this;

    }

    /**
     * 用户点击提交按钮，检验学生信息，插入数据库，并跳转至主页面跳转至主页面
     *
     * @param view
     */
    public void submitHadler(View view) {

        String name = editText.getText().toString();
        String id = editText2.getText().toString();
        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        Integer sex;
        if (radioButton.getText().toString().equals("男")) {
            sex = 0;
        } else {
            sex = 1;
        }
        String college = spinner.getSelectedItem().toString();
        String profession = spinner2.getSelectedItem().toString();
        ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
        checkBoxArrayList.add(checkBox);
        checkBoxArrayList.add(checkBox2);
        checkBoxArrayList.add(checkBox3);
        checkBoxArrayList.add(checkBox4);
        String hobbies = getCheckBoxResult(checkBoxArrayList);
        String birthday = editText6.getText().toString();
        Student student = new Student(id, name, sex, college, profession, hobbies, birthday);
        System.out.println(student);
        StudentDao studentDao = new StudentDaoImpl(this);
        //如果是从修改item跳转过来的，则进行更新操作，否则进行插入操作
        Intent preIntent = getIntent();
        String intentFlag = preIntent.getStringExtra("id");
        if (intentFlag != null) {
            studentDao.updateStudent(student);
            //定制化toast
            Toast toast = Toast.makeText(this, "修改成功 ", Toast.LENGTH_SHORT);
            LinearLayout toastView = (LinearLayout) toast.getView();
            toastView.setOrientation(LinearLayout.HORIZONTAL);
            ImageView toastImage = new ImageView(getApplicationContext());
            toastImage.setImageResource(R.drawable.duigou);
            toastView.addView(toastImage);
            toast.show();
            jumpToMainActivity();
        } else {
            studentDao.insertStudent(student);
            //定制化toast
            Toast toast = Toast.makeText(this, "添加成功 ", Toast.LENGTH_SHORT);
            LinearLayout toastView = (LinearLayout) toast.getView();
            toastView.setOrientation(LinearLayout.HORIZONTAL);
            ImageView toastImage = new ImageView(getApplicationContext());
            toastImage.setImageResource(R.drawable.duigou);
            toastView.addView(toastImage);
            toast.show();
            jumpToMainActivity();
        }
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

    /**
     * 点击出现datepicker
     */
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void jumpToMainActivity() {
        //创建intent,并附加消息
        Intent intent = new Intent(this, MainActivity.class);
        //想Android系统发出链接请求，并跳转界面
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
