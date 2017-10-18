package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * 作者：User on 2015/11/9 14:52
 * 读取手机通讯录
 */
public class MobileContactBean implements Serializable{

    private static final long serialVersionUID = -2164450130686205050L;

    public String contact_name; //手机联系人姓名
    public String contact_phone; //手机号码
    public MobileContactBean() {

    }
    public MobileContactBean(String contact_name, String contact_phone) {
        this.contact_name = contact_name;
        this.contact_phone = contact_phone;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    @Override
    public String toString() {
        return "ContactsBean{" +
                "contact_name='" + contact_name + '\'' +
                ", contact_phone='" + contact_phone + '\'' +
                '}';
    }
}
