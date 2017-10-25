package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * 认证信息
 * Created by neil on 2017/10/25
 */
public class AuthInfoBean implements Serializable {

    private static final long serialVersionUID = -2369550830576205050L;

    private String authType; // 认证类型

    private String authStatus; // 认证状态

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    @Override
    public String toString() {
        return "AuthInfoBean{" +
                "authType='" + authType + '\'' +
                ", authStatus='" + authStatus + '\'' +
                '}';
    }
}
