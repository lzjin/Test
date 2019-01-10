package com.danqiu.myapplication.bean;

/**
 * Created by Administrator on 2019/1/10.
 */

public class Mp3Bean {
    private String id;
    private String url;
    private String name;

    public Mp3Bean(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
