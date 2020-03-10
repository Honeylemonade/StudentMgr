package com.example.studentmgr.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    //设置数据库名称和版本号
    static final String DATABASE_NAME = "studentmgr.db";
    static final int VERSION_CODE = 1;

    //参数分别为数据库名称和版本
    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库时的回调
        Log.d("DataBaseHelper", "创建数据库......");
        String sql = "create table student(id varchar,name varchar,sex integer,college varchar,profession varchar,hobbies varchar);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级数据库时候的回调
        Log.d("DataBaseHelper", "升级数据库......");
    }
}
