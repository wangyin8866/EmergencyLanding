package com.zyjr.emergencylending.base;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/19
 * 备注: 通用结果返回
 */
public class ApiResult<T> implements Serializable{

    private static final long serialVersionUID = -2164450630686205050L;

    private String flag;

    private String msg;

    private T result;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
