package com.zyjr.emergencylending.entity;

/**
 * @author wangyin
 * @date 2017/10/28.
 * @description :
 */

public class H5Bean {

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"help_h5_url":"www.baidu.com"}
     * lockerFlag : false
     */

    private String flag;
    private String msg;
    private Object ext;
    private ResultBean result;
    private boolean lockerFlag;
    private String promptMsg;
    public String getPromptMsg() {
        return promptMsg;
    }

    public void setPromptMsg(String promptMsg) {
        this.promptMsg = promptMsg;
    }
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
         * help_h5_url : www.baidu.com
         */

        private String h5_url;

        public String getH5_url() {
            return h5_url;
        }
    }
}
