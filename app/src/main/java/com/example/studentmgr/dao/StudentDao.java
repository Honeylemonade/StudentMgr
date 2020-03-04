package com.example.studentmgr.dao;

import com.example.studentmgr.entity.Student;

import java.util.ArrayList;

public interface StudentDao {
    //TODO check2：学生CRUD的两个接口
    /**
     * 查询所有学生信息
     *
     * @return
     */
    public ArrayList<Student> selectAll();

    /**
     * 插入学生信息
     *
     * @param student
     * @return
     */
    public boolean insertStudent(Student student);
}
