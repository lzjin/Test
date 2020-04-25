package com.danqiu.myapplication.javatest;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Description: 作用描述
 * Author: Lzj
 * CreateDate: 2020/4/21
 */
public class ReflexTest {

    public static void main(String[] args) {
        System.out.println("-----开始----------");
        Class<ReflexTest> classz=ReflexTest.class;
        Method[] methods=classz.getMethods();
        for (Method method: methods) {
              System.out.println("-----获取的方法-----------"+method.getName());
        }

    }

    private String name;
    private int age;
    public String city;
    protected Boolean isLogin;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }


}
