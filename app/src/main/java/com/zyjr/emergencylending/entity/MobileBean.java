package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * 运营商 认证结果集 bean
 * @author neil
 * @date 2017/11/2
 */
public class MobileBean implements Serializable {

    private static final long serialVersionUID = -2377450930686205050L;

    private String status ; //  有效性 1有效 0无效

    private String msg; //

    private String flag; //

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MobileBean{" +
                "status='" + status + '\'' +
                '}';
    }
}
