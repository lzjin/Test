package com.danqiu.myapplication.bean;

/**
 * Created by Administrator on 2018/9/27.
 */

public class TestBean {

    /**
     * data : {"token":"vRgLZ1h3us5qiwN+Vu/GReeiDoAEF40s"}
     * success : true
     * message : 操作成功
     */

    private DataBean data;
    private boolean success;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * token : vRgLZ1h3us5qiwN+Vu/GReeiDoAEF40s
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
