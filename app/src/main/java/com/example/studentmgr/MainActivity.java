package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmgr.adpater.StudentAdapter;
import com.example.studentmgr.dao.StudentDao;
import com.example.studentmgr.dao.StudentDaoImpl;
import com.example.studentmgr.entity.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据
        init();
        //初始化应用栏
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
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

    public void updateItem(String id) {
        Toast.makeText(this, "学号：" + id, Toast.LENGTH_SHORT).show();
        //创建intent,并附加消息
        Intent intent = new Intent(this, StudentActivity.class);
        intent.putExtra("id", id);
        //想Android系统发出链接请求，并跳转界面
        startActivity(intent);

    }

    public void deleteItem(final String id, final Context context) {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
        normalDialog.setMessage("确定要删除么?");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
                StudentDao studentDao = new StudentDaoImpl(context);
                studentDao.deleteStudentById(id);
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(context, MainActivity.class);
                //想Android系统发出链接请求，并跳转界面
                startActivity(intent2);
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
}
