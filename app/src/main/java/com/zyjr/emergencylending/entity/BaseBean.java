package com.zyjr.emergencylending.entity;

/**
 * Created by wangyin on 2017/10/16.
 */

public class BaseBean {

    /**
     * flag : 0000
     * msg : 请求成功
     */

    private String flag;
    private String msg;


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

}
