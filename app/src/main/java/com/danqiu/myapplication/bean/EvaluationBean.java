package com.danqiu.myapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/9/27.
 */

public class EvaluationBean {

    /**
     * data : [{"project":{"id":"1037284479279824896","title":"春熙"},"id":"1037285337212125184","content":"我对老板很满意!","status":0,"uName":"刘建科","oName":"玉林","pId":"1037284479279824896","uId":"1025655468824985600","createDate":1536143071000,"star":5,"plId":"1037284479279824897","cType":0,"oId":"1019515031856873472"},{"project":{"id":"1037260932918542336","title":"内部测试8"},"id":"1037283070652186624","content":"我对老板很满意!","status":0,"uName":"超哥","oName":"玉林","pId":"1037260932918542336","uId":"1019514803397328896","createDate":1536142530000,"star":5,"plId":"1037260932918542337","cType":0,"oId":"1019515031856873472"},{"project":{"id":"1037259120060661760","title":"内部测试7"},"id":"1037260632304386048","content":"我对老板很满意!","status":0,"uName":"超哥","oName":"玉林","pId":"1037259120060661760","uId":"1019514803397328896","createDate":1536137181000,"star":5,"plId":"1037259120060661761","cType":0,"oId":"1019515031856873472"},{"project":{"id":"1037246539598135296","title":"内部测试7"},"id":"1037258427409104896","content":"我对老板很满意!","status":0,"uName":"超哥","oName":"玉林","pId":"1037246539598135296","uId":"1019514803397328896","createDate":1536136655000,"star":5,"plId":"1037246539598135297","cType":0,"oId":"1019515031856873472"},{"project":{"id":"1037236427219271680","title":"内部测试6"},"id":"1037240839484473344","content":"我对老板很满意!","status":0,"uName":"超哥","oName":"玉林","pId":"1037236427219271680","uId":"1019514803397328896","createDate":1536132462000,"star":5,"plId":"1037236427219271681","cType":0,"oId":"1019515031856873472"},{"project":{"id":"1037231970448310272","title":"内部测试5"},"id":"1037236717871955968","content":"我对老板很满意!","status":0,"uName":"超哥","oName":"玉林","pId":"1037231970448310272","uId":"1019514803397328896","createDate":1536131479000,"star":5,"plId":"1037231970448310273","cType":0,"oId":"1019515031856873472"}]
     * success : true
     * message : 操作成功
     */

    private boolean success;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * project : {"id":"1037284479279824896","title":"春熙"}
         * id : 1037285337212125184
         * content : 我对老板很满意!
         * status : 0
         * uName : 刘建科
         * oName : 玉林
         * pId : 1037284479279824896
         * uId : 1025655468824985600
         * createDate : 1536143071000
         * star : 5
         * plId : 1037284479279824897
         * cType : 0
         * oId : 1019515031856873472
         */

        private ProjectBean project;
        private String id;
        private String content;
        private int status;
        private String uName;
        private String oName;
        private String pId;
        private String uId;
        private long createDate;
        private int star;
        private String plId;
        private int cType;
        private String oId;

        public ProjectBean getProject() {
            return project;
        }

        public void setProject(ProjectBean project) {
            this.project = project;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUName() {
            return uName;
        }

        public void setUName(String uName) {
            this.uName = uName;
        }

        public String getOName() {
            return oName;
        }

        public void setOName(String oName) {
            this.oName = oName;
        }

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public String getUId() {
            return uId;
        }

        public void setUId(String uId) {
            this.uId = uId;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getPlId() {
            return plId;
        }

        public void setPlId(String plId) {
            this.plId = plId;
        }

        public int getCType() {
            return cType;
        }

        public void setCType(int cType) {
            this.cType = cType;
        }

        public String getOId() {
            return oId;
        }

        public void setOId(String oId) {
            this.oId = oId;
        }

        public static class ProjectBean {
            /**
             * id : 1037284479279824896
             * title : 春熙
             */

            private String id;
            private String title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
