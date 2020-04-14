package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.Fade;
import android.transition.Slide;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmgr.adpater.StudentAdapter;
import com.example.studentmgr.dao.StudentDao;
import com.example.studentmgr.dao.StudentDaoImpl;
import com.example.studentmgr.entity.Student;
import com.example.studentmgr.listener.CollegeSpinnerListener;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class MainActivity extends AppCompatActivity {
    public ListView listView;
    public static MainActivity instance;
    //查询dialog中的控件
    EditText editText5;
    Spinner spinner3;
    Spinner spinner4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一次进入时使用
        getWindow().setEnterTransition(new Slide(Gravity.LEFT));
        //再次进入时使用
        getWindow().setReenterTransition(new Slide(Gravity.LEFT));
        setContentView(R.layout.activity_main);
        instance = this;
        //初始化数据
        init();
        //初始化应用栏
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //读取屏幕状态
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean s = sharedPreferences.getBoolean("switch_preference_1", false);
        if (s) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    /**
     * 初始化显示学生数据
     */
    private void init() {
        StudentDao studentDao = new StudentDaoImpl(this);
        ArrayList<Student> studentArrayList = studentDao.selectAll();
        //获取数据库中学生信息，进行渲染
        listView = (ListView) findViewById(R.id.listView);
        this.registerForContextMenu(listView);
        Collections.reverse(studentArrayList);
        listView.setAdapter(new StudentAdapter(this, studentArrayList));

    }


    //工具栏菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_find).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //显示搜索窗口
            case R.id.action_find:
                showSearchDialog();
                return true;

            case R.id.action_insert:
                jumpToStudentActivity();
                return true;

            case R.id.action_flash:
                StudentDao studentDao = new StudentDaoImpl(MainActivity.instance);
                ArrayList<Student> studentArrayList = studentDao.selectAll();
                listView.setAdapter(new StudentAdapter(MainActivity.instance, studentArrayList));
                Toast.makeText(this, "刷新成功", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_findPhonePlace:
                jumpToPhonePlaceActivity();
                return true;

            case R.id.action_config:
                jumpToconfigActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //显示搜索窗口
    private void showSearchDialog() {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("条件查询");
        LayoutInflater layoutInflater = getLayoutInflater();
        View childView = layoutInflater.inflate(R.layout.searchview_main, null);
        normalDialog.setView(childView);
        editText5 = childView.findViewById(R.id.editText5);
        spinner3 = childView.findViewById(R.id.spinner3);
        spinner4 = childView.findViewById(R.id.spinner4);
        spinner3.setOnItemSelectedListener(new CollegeSpinnerListener(spinner4, this));
        normalDialog.setPositiveButton("查询", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editText5.getText().toString();
                String college = spinner3.getSelectedItem().toString();
                String profession = spinner4.getSelectedItem().toString();
                //刷新listview
                StudentDao studentDao = new StudentDaoImpl(MainActivity.instance);
                ArrayList<Student> studentArrayList = studentDao.selectStudentByMultyConditions(name, college, profession);
                listView.setAdapter(new StudentAdapter(MainActivity.instance, studentArrayList));
                if (studentArrayList.size() == 0) {
                    Toast.makeText(MainActivity.instance, "结果为空", Toast.LENGTH_SHORT).show();
                }

            }
        });
        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
            }
        });
        // 显示
        normalDialog.show();
    }

    //listview上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //根据View生成对应的菜单
        if (v == listView) {
            //添加菜单项
            menu.add(0, 0, 0, "编辑");
            menu.add(0, 1, 0, "删除");
        }
    }

    /**
     * 长按操作content
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //关键代码
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView textView = menuInfo.targetView.findViewById(R.id.textView2);
        switch (item.getItemId()) {
            //跳转到编辑界面
            case 0:
                //输出当前点击的item的学号
                updateItem(textView.getText().toString());
                break;
            //删除该学生信息
            case 1:
                deleteItem(textView.getText().toString(), this);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * 长按操作1：编辑学生信息
     *
     * @param id
     */
    public void updateItem(String id) {
        Toast.makeText(this, "学号：" + id, Toast.LENGTH_SHORT).show();
        jumpToStudentActivityForUpdate(id);
    }

    /**
     * 长按操作2：删除学生信息
     *
     * @param id
     * @param context
     */
    public void deleteItem(final String id, final Context context) {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
        normalDialog.setMessage("确定要删除么?");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
                StudentDao studentDao = new StudentDaoImpl(context);
                studentDao.deleteStudentById(id);
                //定制化toast
                Toast toast = Toast.makeText(MainActivity.this, "删除成功 ", Toast.LENGTH_SHORT);
                LinearLayout toastView = (LinearLayout) toast.getView();
                toastView.setOrientation(LinearLayout.HORIZONTAL);
                ImageView toastImage = new ImageView(getApplicationContext());
                toastImage.setImageResource(R.drawable.duigou);
                toastView.addView(toastImage);
                toast.show();
                ArrayList<Student> studentArrayList = studentDao.selectAll();
                listView.setAdapter(new StudentAdapter(MainActivity.instance, studentArrayList));
            }
        });
        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
            }
        });
        // 显示
        normalDialog.show();
    }

    /**
     * 跳转页面到StudentActivity
     */
    public void jumpToStudentActivity() {
        //创建intent,并附加消息
        Intent intent = new Intent(this, StudentActivity.class);
        //想Android系统发出链接请求，并跳转界面
        intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);

        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * 编辑学生信息的页面跳转
     *
     * @param id
     */
    public void jumpToStudentActivityForUpdate(String id) {
        //创建intent,并附加消息
        Intent intent = new Intent(this, StudentActivity.class);
        intent.putExtra("id", id);
        intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        //想Android系统发出链接请求，并跳转界面
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * 跳转到手机归属地查询界面
     */
    private void jumpToPhonePlaceActivity() {
        Intent intent = new Intent(this, PhonePlaceActivity.class);
        //想Android系统发出链接请求，并跳转界面
        startActivity(intent);
    }

    /**
     * 跳转到设置界面
     */
    private void jumpToconfigActivity() {
        Intent intent = new Intent(this, ConfigActivity.class);
        //想Android系统发出链接请求，并跳转界面
        startActivity(intent);
    }

}
