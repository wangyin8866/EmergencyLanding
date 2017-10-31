package com.zyjr.emergencylending.base;

import java.io.Serializable;

/**
 * 定义Api结果返回
 *
 * @author neil
 * @date 2017/10/31
 */
public class CustomApiResult<T, Q> implements Serializable {

    private static final long serialVersionUID = -2164450631686205050L;

    /**
     * 处理结果
     */
    private String flag;

    /**
     * 处理消息
     */
    private String msg;

    /**
     * 额外信息返回
     */
    private Q ext;

    private boolean lockerFlag;

    /**
     * 正常信息返回
     */
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

    public Q getExt() {
        return ext;
    }

    public void setExt(Q ext) {
        this.ext = ext;
    }

    public boolean isLockerFlag() {
        return lockerFlag;
    }

    public void setLockerFlag(boolean lockerFlag) {
        this.lockerFlag = lockerFlag;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
