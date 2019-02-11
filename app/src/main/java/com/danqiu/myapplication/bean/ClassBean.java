package com.danqiu.myapplication.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/9/26.
 *
 */
@Entity
public class ClassBean {
    // 标明主键，括号里可以指定是否自增
    @Id(autoincrement = true)
    private Long id;
    // 非空
    @NotNull
    private String username;
    private String stclass;
    @Generated(hash = 1437719740)
    public ClassBean(Long id, @NotNull String username, String stclass) {
        this.id = id;
        this.username = username;
        this.stclass = stclass;
    }
    @Generated(hash = 1395092832)
    public ClassBean() {
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
    public String getStclass() {
        return this.stclass;
    }
    public void setStclass(String stclass) {
        this.stclass = stclass;
    }

    @Override
    public String toString() {
        return "ClassBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", stclass='" + stclass + '\'' +
                '}';
    }
}
