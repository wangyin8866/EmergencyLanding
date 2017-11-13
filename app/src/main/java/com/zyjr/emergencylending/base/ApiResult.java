package com.zyjr.emergencylending.base;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/19
 * 备注: 通用结果返回
 */
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = -2164450630686205050L;

    private String flag;

    private String msg; // 异常描述

    private String ext;

    private boolean lockerFlag;

    private String errorMsg; // 错误信息,一般为程序端异常

    private String promptMsg; // 外部展示

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

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public boolean isLockerFlag() {
        return lockerFlag;
    }

    public void setLockerFlag(boolean lockerFlag) {
        this.lockerFlag = lockerFlag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getPromptMsg() {
        return promptMsg;
    }

    public void setPromptMsg(String promptMsg) {
        this.promptMsg = promptMsg;
    }
}
