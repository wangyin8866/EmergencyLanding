package com.zyjr.emergencylending.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author neil
 * @date 2017/11/1
 */

public class AuthResult implements Serializable {

    private static final long serialVersionUID = -2369550830576205050L;

    private String userName;  // 用户姓名

    private String idcard;  // 身份证

    private List<AuthInfoBean> auth_result;  // 认证结果集

    public List<AuthInfoBean> getAuth_result() {
        return auth_result;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setAuth_result(List<AuthInfoBean> auth_result) {
        this.auth_result = auth_result;
    }
}
