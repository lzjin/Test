package com.danqiu.myapplication.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/9/25.
 * 
 */

@Entity
public class UserBean {
   // 标明主键，括号里可以指定是否自增
    @Id(autoincrement = true)
    private Long id;
    // 非空
    @NotNull
    private String username;
    private int age;
    private String sex;
    private String address;
   @Generated(hash = 1753733449)
   public UserBean(Long id, @NotNull String username, int age, String sex,
           String address) {
       this.id = id;
       this.username = username;
       this.age = age;
       this.sex = sex;
       this.address = address;
   }
   @Generated(hash = 1203313951)
   public UserBean() {
   }
   public Long getId() {
       return this.id;
   }
   public void setId(Long id) {
       this.id = id;
   }
   public String getUsername() {
       return this.username;
   }
   public void setUsername(String username) {
       this.username = username;
   }
   public int getAge() {
       return this.age;
   }
   public void setAge(int age) {
       this.age = age;
   }
   public String getSex() {
       return this.sex;
   }
   public void setSex(String sex) {
       this.sex = sex;
   }
   public String getAddress() {
       return this.address;
   }
   public void setAddress(String address) {
       this.address = address;
   }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
