package com.example.studentmgr.dao;

import com.example.studentmgr.entity.Student;

import java.util.ArrayList;

public interface StudentDao {

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

    /**
     * 根据学生学号查询学生信息
     *
     * @param id
     * @return
     */
    public Student selectStudentById(String id);

    /**
     * 根据学生的id，更新学生信息
     *
     * @param student
     */
    public void updateStudent(Student student);

    /**
     * 根据学生id删除学生
     *
     * @param id
     */
    public void deleteStudentById(String id);
}
