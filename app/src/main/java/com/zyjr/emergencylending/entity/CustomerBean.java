package com.zyjr.emergencylending.entity;

/**
 * @author wangyin
 * @date 2017/11/2.
 * @description :
 */

public class CustomerBean {

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"cust_num":2,"order_num":0,"lend_num":0,"lend_total_amount":0}
     * lockerFlag : false
     */

    private String flag;
    private String msg;
    private Object ext;
    private ResultBean result;
    private boolean lockerFlag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public boolean isLockerFlag() {
        return lockerFlag;
    }

    public void setLockerFlag(boolean lockerFlag) {
        this.lockerFlag = lockerFlag;
    }

    public static class ResultBean {
        /**
         * cust_num : 2
         * order_num : 0
         * lend_num : 0
         * lend_total_amount : 0.0
         */

        private int cust_num;
        private int order_num;
        private int lend_num;
        private double lend_total_amount;

        public int getCust_num() {
            return cust_num;
        }

        public void setCust_num(int cust_num) {
            this.cust_num = cust_num;
        }

        public int getOrder_num() {
            return order_num;
        }

        public void setOrder_num(int order_num) {
            this.order_num = order_num;
        }

        public int getLend_num() {
            return lend_num;
        }

        public void setLend_num(int lend_num) {
            this.lend_num = lend_num;
        }

        public double getLend_total_amount() {
            return lend_total_amount;
        }

        public void setLend_total_amount(double lend_total_amount) {
            this.lend_total_amount = lend_total_amount;
        }
    }
}
