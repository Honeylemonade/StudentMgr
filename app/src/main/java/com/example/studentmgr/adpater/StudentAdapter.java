package com.example.studentmgr.adpater;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studentmgr.MainActivity;
import com.example.studentmgr.R;
import com.example.studentmgr.entity.Student;

import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Student> arrayList;
    private LayoutInflater layoutInflater;

    //构造函数需要传入两个必要的参数：上下文对象和数据源
    public StudentAdapter(Context context, ArrayList<Student> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    public StudentAdapter() {
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder {
        public ImageView imageView;
        public TextView textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView16;
    }

    //列表每个Item的样式
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {

            //引入外部布局文件
            convertView = layoutInflater.inflate(R.layout.listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
            viewHolder.textView3 = (TextView) convertView.findViewById(R.id.textView3);
            viewHolder.textView4 = (TextView) convertView.findViewById(R.id.textView4);
            viewHolder.textView5 = (TextView) convertView.findViewById(R.id.textView5);
            viewHolder.textView6 = (TextView) convertView.findViewById(R.id.textView6);
            viewHolder.textView7 = (TextView) convertView.findViewById(R.id.textView7);
            viewHolder.textView8 = (TextView) convertView.findViewById(R.id.textView8);
            viewHolder.textView16 = (TextView) convertView.findViewById(R.id.textView16);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //为控件赋值

        Student student = (Student) getItem(position);
        viewHolder.textView2.setText(student.getId());
        viewHolder.textView4.setText(student.getName());
        String sex;
        if (student.getSex() == 0) {
            sex = "男";
        } else {
            sex = "女";
        }
        viewHolder.textView5.setText(sex);
        viewHolder.textView8.setText(student.getCollege());
        viewHolder.textView16.setText("NO." + String.valueOf(position + 1));
        //设置字体大小
        //读取字体大小
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.instance);
        String size = sharedPreferences.getString("list_preference_1", "中");
        if (size.equals("中")) {
            viewHolder.textView .setTextSize(15);
            viewHolder.textView2.setTextSize(15);
            viewHolder.textView3.setTextSize(15);
            viewHolder.textView4.setTextSize(15);
            viewHolder.textView5.setTextSize(15);
            viewHolder.textView6.setTextSize(15);
            viewHolder.textView7.setTextSize(15);
            viewHolder.textView8.setTextSize(15);
            viewHolder.textView16.setTextSize(15);

        }else if (size.equals("小")){
            viewHolder.textView .setTextSize(12);
            viewHolder.textView2.setTextSize(12);
            viewHolder.textView3.setTextSize(12);
            viewHolder.textView4.setTextSize(12);
            viewHolder.textView5.setTextSize(12);
            viewHolder.textView6.setTextSize(12);
            viewHolder.textView7.setTextSize(12);
            viewHolder.textView8.setTextSize(12);
            viewHolder.textView16.setTextSize(12);
        }else if (size.equals("大")){
            viewHolder.textView .setTextSize(18);
            viewHolder.textView2.setTextSize(18);
            viewHolder.textView3.setTextSize(18);
            viewHolder.textView4.setTextSize(18);
            viewHolder.textView5.setTextSize(18);
            viewHolder.textView6.setTextSize(18);
            viewHolder.textView7.setTextSize(18);
            viewHolder.textView8.setTextSize(18);
            viewHolder.textView16.setTextSize(18);
        }
        return convertView;
    }

}
