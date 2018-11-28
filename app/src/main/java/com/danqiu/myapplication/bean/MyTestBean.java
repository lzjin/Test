package com.danqiu.myapplication.bean;

/**
 * Created by Administrator on 2018/11/28.
 */

public class MyTestBean {
    private String xId;//唯一id
    private String sUrl;//网络地址


    public String getxId() {
        return xId;
    }

    public void setxId(String xId) {
        this.xId = xId;
    }

    public String getsUrl() {
        return sUrl;
    }

    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    }


    public MyTestBean(String sUrl) {
        this.sUrl = sUrl;
    }

    public MyTestBean(String xId, String sUrl) {
        this.xId = xId;
        this.sUrl = sUrl;
    }

}
