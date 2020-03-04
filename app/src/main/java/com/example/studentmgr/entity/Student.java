package com.example.studentmgr.entity;

public class Student {
    private String id;
    private String name;
    private Integer sex;
    private String college;
    private String profession;
    private String hobbies;

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", college='" + college + '\'' +
                ", profession='" + profession + '\'' +
                ", hobbies='" + hobbies + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public Student() {
    }

    public Student(String id, String name, Integer sex, String college, String profession, String hobbies) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.college = college;
        this.profession = profession;
        this.hobbies = hobbies;
    }
}
