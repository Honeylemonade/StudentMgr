package com.example.studentmgr.dao;

import com.example.studentmgr.entity.Admin;

import java.util.ArrayList;

public interface AdminDao {
    /**
     * 获取全部管理员信息
     *
     * @return
     */
    public ArrayList<Admin> selectAll();
}
