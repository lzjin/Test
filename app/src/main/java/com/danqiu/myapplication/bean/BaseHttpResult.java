package com.danqiu.myapplication.bean;

/**
 * Created by Administrator on 2018/9/27.
 */

public class BaseHttpResult<T> {
    public int Code;
    public String Message;
    public T Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "Code=" + Code +
                ", Message='" + Message + '\'' +
                ", Data=" + Data +
                '}';
    }
}
