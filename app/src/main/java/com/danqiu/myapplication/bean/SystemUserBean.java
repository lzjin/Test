package com.danqiu.myapplication.bean;

/**
 * Created by lzj on 2020/3/19
 * Describe ：注释
 */
public class SystemUserBean {
    private String id;
    private String name;
    private int age;

    public SystemUserBean(String name) {
        this.name = name;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
