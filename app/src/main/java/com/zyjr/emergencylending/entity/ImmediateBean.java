package com.zyjr.emergencylending.entity;

/**
 * @author wangyin
 * @date 2017/11/3.
 * @description :
 */

public class ImmediateBean {

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"is_exists":"0","is_lz":"1","cust_id":"97e123b56d2e4f0fa5eb9fc4368d9d08"}
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
         * is_exists : 0
         * is_lz : 1
         * cust_id : 97e123b56d2e4f0fa5eb9fc4368d9d08
         */

        private String is_exists;
        private String is_lz;
        private String cust_juid;

        public String getIs_exists() {
            return is_exists;
        }

        public void setIs_exists(String is_exists) {
            this.is_exists = is_exists;
        }

        public String getIs_lz() {
            return is_lz;
        }

        public void setIs_lz(String is_lz) {
            this.is_lz = is_lz;
        }

        public String getCust_juid() {
            return cust_juid;
        }

        public void setCust_juid(String cust_juid) {
            this.cust_juid = cust_juid;
        }
    }
}
