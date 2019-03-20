package com.danqiu.myapplication.bean;

/**
 * Created by lzj on 2019/3/20
 * Describe ：注释
 */
public class RecycleBean {
    private String head;
    private String name;
    private String context;

    public RecycleBean(String head, String name, String context) {
        this.head = head;
        this.name = name;
        this.context = context;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
