package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * @author neil
 * @date 2017/11/1
 */
public class ZhimaAuthBean implements Serializable {

    private static final long serialVersionUID = -2369550830576444550L;

    private String code; //

    private String message; //

    private String data; //

    private String state; //

    private boolean succ; //

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }

    @Override
    public String toString() {
        return "ZhimaAuthBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", state='" + state + '\'' +
                ", succ=" + succ +
                '}';
    }
}
