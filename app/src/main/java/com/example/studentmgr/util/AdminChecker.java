package com.example.studentmgr.util;

import android.content.Context;

import com.example.studentmgr.dao.AdminDao;
import com.example.studentmgr.dao.AdminDaoImpl;
import com.example.studentmgr.entity.Admin;

import java.util.ArrayList;

public class AdminChecker {
    public static boolean check(Context context, Admin admin) {
        AdminDao adminDao = new AdminDaoImpl(context);

        ArrayList<Admin> admins = adminDao.selectAll();
        for (Admin a : admins) {
            System.out.println(a);
            if (a.getUserName().equals(admin.getUserName()) && a.getPassword().equals(admin.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
