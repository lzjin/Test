package com.danqiu.myapplication.bean;

import java.util.List;

/**
 * Created by lzj on 2019/5/29
 * Describe ：注释
 */
public class BarChartVtDateValueBean {

    /**
     * code : 10000
     * message : SUCCESS
     * result : {"Shanghai":[{"rate":"-0.00034196","tradeDate":"20180502"},{"rate":"0.00604335","tradeDate":"20180503"},{"rate":"0.00285572","tradeDate":"20180504"},{"rate":"0.01765377","tradeDate":"20180507"},{"rate":"0.02571709","tradeDate":"20180508"},{"rate":"0.02495562","tradeDate":"20180509"},{"rate":"0.02990723","tradeDate":"20180510"},{"rate":"0.02629005","tradeDate":"20180511"},{"rate":"0.02978395","tradeDate":"20180514"},{"rate":"0.03565177","tradeDate":"20180515"},{"rate":"0.02833467","tradeDate":"20180516"},{"rate":"0.02337625","tradeDate":"20180517"},{"rate":"0.03603623","tradeDate":"20180518"},{"rate":"0.04269927","tradeDate":"20180521"},{"rate":"0.04286441","tradeDate":"20180522"},{"rate":"0.02813968","tradeDate":"20180523"},{"rate":"0.02349564","tradeDate":"20180524"},{"rate":"0.01916534","tradeDate":"20180525"}],"Shenzhen":[{"rate":"0.00178004","tradeDate":"20180502"},{"rate":"0.01299389","tradeDate":"20180503"},{"rate":"0.00985242","tradeDate":"20180504"},{"rate":"0.02925488","tradeDate":"20180507"},{"rate":"0.03712899","tradeDate":"20180508"},{"rate":"0.03531456","tradeDate":"20180509"},{"rate":"0.03925607","tradeDate":"20180510"},{"rate":"0.03000959","tradeDate":"20180511"},{"rate":"0.03360851","tradeDate":"20180514"},{"rate":"0.04102158","tradeDate":"20180515"},{"rate":"0.03650125","tradeDate":"20180516"},{"rate":"0.03012562","tradeDate":"20180517"},{"rate":"0.03371167","tradeDate":"20180518"},{"rate":"0.04270245","tradeDate":"20180521"},{"rate":"0.04274681","tradeDate":"20180522"},{"rate":"0.02970187","tradeDate":"20180523"},{"rate":"0.02321282","tradeDate":"20180524"},{"rate":"0.01198677","tradeDate":"20180525"}]}
     */

    private int code;
    private String message;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<ShanghaiBean> Shanghai;
        private List<ShenzhenBean> Shenzhen;

        public List<ShanghaiBean> getShanghai() {
            return Shanghai;
        }

        public void setShanghai(List<ShanghaiBean> Shanghai) {
            this.Shanghai = Shanghai;
        }

        public List<ShenzhenBean> getShenzhen() {
            return Shenzhen;
        }

        public void setShenzhen(List<ShenzhenBean> Shenzhen) {
            this.Shenzhen = Shenzhen;
        }

        public static class ShanghaiBean {
            /**
             * rate : -0.00034196
             * tradeDate : 20180502
             */

            private String rate;
            private String tradeDate;

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getTradeDate() {
                return tradeDate;
            }

            public void setTradeDate(String tradeDate) {
                this.tradeDate = tradeDate;
            }
        }

        public static class ShenzhenBean {
            /**
             * rate : 0.00178004
             * tradeDate : 20180502
             */

            private String rate;
            private String tradeDate;

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getTradeDate() {
                return tradeDate;
            }

            public void setTradeDate(String tradeDate) {
                this.tradeDate = tradeDate;
            }
        }
    }
}
