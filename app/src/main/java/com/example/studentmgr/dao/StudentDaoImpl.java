package com.example.studentmgr.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentmgr.util.DataBaseHelper;
import com.example.studentmgr.entity.Student;

import java.util.ArrayList;

public class StudentDaoImpl implements StudentDao {
    private final DataBaseHelper dataBaseHelper;

    public StudentDaoImpl(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    @Override
    public ArrayList<Student> selectAll() {
        //建议使用try，这样在用完之后，会自动释放连接
        try (SQLiteDatabase db = dataBaseHelper.getReadableDatabase()) {
            ArrayList result = new ArrayList<Student>();
            //读取数据库,获取游标
            System.out.println("//////////////////////////////////正在查询//////////////////////////////////");
            String sql = "select * from student;";
            Cursor cursor = db.query("student", new String[]{"id", "name", "sex", "college", "profession", "hobbies"}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                System.out.println(cursor.getString(0));
                result.add(new Student(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            }
            return result;
        } catch (Exception e) {
            System.out.println("//////////////////////////////////查询错误//////////////////////////////////");
        }
        return null;
    }

    @Override
    public boolean insertStudent(Student student) {
        try (SQLiteDatabase db = dataBaseHelper.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", student.getId());
            contentValues.put("name", student.getName());
            contentValues.put("sex", student.getSex());
            contentValues.put("college", student.getCollege());
            contentValues.put("profession", student.getProfession());
            contentValues.put("hobbies", student.getHobbies());
            db.insert("student", null, contentValues);
        } catch (Exception e) {
            System.out.println("//////////////////////////////////插入错误//////////////////////////////////");
        }
        return false;
    }
}
