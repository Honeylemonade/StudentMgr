package com.example.studentmgr.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentmgr.entity.Admin;
import com.example.studentmgr.entity.Student;
import com.example.studentmgr.util.DataBaseHelper;

import java.util.ArrayList;

public class AdminDaoImpl implements AdminDao {
    private final DataBaseHelper dataBaseHelper;

    public AdminDaoImpl(Context context) {
        this.dataBaseHelper = new DataBaseHelper(context);
    }

    @Override
    public ArrayList<Admin> selectAll() {
        //建议使用try，这样在用完之后，会自动释放连接
        try (SQLiteDatabase db = dataBaseHelper.getReadableDatabase()) {
            ArrayList result = new ArrayList<Admin>();
            //读取数据库,获取游标
            System.out.println("//////////////////////////////////正在查询//////////////////////////////////");
            String sql = "select * from admin;";
            Cursor cursor = db.query("admin", new String[]{"userName", "password"}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                result.add(new Admin(cursor.getString(0), cursor.getString(1)));
            }
            return result;
        } catch (Exception e) {
            System.out.println("//////////////////////////////////查询错误//////////////////////////////////");
        }
        return null;
    }
}
