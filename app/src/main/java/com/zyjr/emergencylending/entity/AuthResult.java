package com.zyjr.emergencylending.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author neil
 * @date 2017/11/1
 */

public class AuthResult implements Serializable {

    private static final long serialVersionUID = -2369550830576205050L;

    private List<AuthInfoBean> auth_result;

    public List<AuthInfoBean> getAuth_result() {
        return auth_result;
    }

    public void setAuth_result(List<AuthInfoBean> auth_result) {
        this.auth_result = auth_result;
    }
}
