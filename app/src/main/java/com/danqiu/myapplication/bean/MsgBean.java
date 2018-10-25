package com.danqiu.myapplication.bean;

/**
 * Created by Administrator on 2018/9/27.
 */

public class MsgBean {

    /**
     * success : false
     * message : 用户验证失败
     */

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
