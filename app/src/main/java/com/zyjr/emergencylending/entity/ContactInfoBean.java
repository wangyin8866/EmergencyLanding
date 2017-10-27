package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/13.
 * 联系人信息 bean
 */
public class ContactInfoBean implements Serializable {

    private static final long serialVersionUID = -2345450930576365050L;

    private String contact_name; //联系人姓名
    private String relation; // 关系
    private String contact_phone; // 联系电话
    private String fill_location; // 填写位置

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getFill_location() {
        return fill_location;
    }

    public void setFill_location(String fill_location) {
        this.fill_location = fill_location;
    }

    @Override
    public String toString() {
        return "ContactInfoBean{" +
                "contact_name='" + contact_name + '\'' +
                ", relation='" + relation + '\'' +
                ", contact_phone='" + contact_phone + '\'' +
                ", fill_location='" + fill_location + '\'' +
                '}';
    }
}
