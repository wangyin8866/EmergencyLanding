package com.zyjr.emergencylending.entity.account;

/**
 * Created by wangyin on 2017/10/24.
 */

public class RegisterBean {

    /**
     * flag : 0000
     * msg : 请求成功
     * result : {"juid":"aaaaa"}
     */

    private String flag;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * juid : aaaaa
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
