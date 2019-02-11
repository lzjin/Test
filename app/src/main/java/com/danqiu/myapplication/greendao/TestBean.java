package com.danqiu.myapplication.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2019/1/25.
 */
@Entity
public class TestBean {
    // 标明主键，括号里可以指定是否自增
    @Id(autoincrement = true)
    private Long id;
    // 非空
    @NotNull
    private String name;

    private String sex;

    @Generated(hash = 1679862122)
    public TestBean(Long id, @NotNull String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    @Generated(hash = 2087637710)
    public TestBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
