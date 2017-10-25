package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * 通讯录 bean
 * @author neil
 * Created on 2017/10/24
 */
public class ContactsBean implements Serializable{

    private static final long serialVersionUID = -1234450754576205050L;

    private String contact_phone;
    private String contact_name;

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }
}
