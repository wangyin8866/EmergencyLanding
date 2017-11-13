package com.zyjr.emergencylending.entity;

/**
 * @author wangyin
 * @date 2017/11/3.
 * @description :
 */

public class EffectiveOrderBean {

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"is_effective_order":"0"}
     * lockerFlag : false
     */
    private String promptMsg;
    public String getPromptMsg() {
        return promptMsg;
    }

    public void setPromptMsg(String promptMsg) {
        this.promptMsg = promptMsg;
    }
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
         * is_effective_order : 0
         */

        private String is_effective_order;

        public String getIs_effective_order() {
            return is_effective_order;
        }

        public void setIs_effective_order(String is_effective_order) {
            this.is_effective_order = is_effective_order;
        }
    }
}
