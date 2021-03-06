package com.zyjr.emergencylending.entity.account;

/**
 * Created by wangyin on 2017/10/24.
 */

public class RegisterBean {
    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"juid":"a80185da3c5d46ecb1cc94bf7829e5ae"}
     * lockerFlag : false
     */

    private String flag;
    private String msg;
    private String ext;
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

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
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
         * juid : a80185da3c5d46ecb1cc94bf7829e5ae
         */

        private String juid;

        public String getJuid() {
            return juid;
        }

        public void setJuid(String juid) {
            this.juid = juid;
        }
    }
}
