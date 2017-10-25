package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/24
 * 用户填写资料信息 bean
 */
public class WriteInfoBean implements Serializable{

    private static final long serialVersionUID = -2374510754576205050L;

    private String user_data_status;// 个人资料完成状态 1：完成 0：未完成
    private String user_data_edit;  // 个人资料是否可编辑标识  1：是 0：否
    private String user_job_status; // 工作信息完成状态 1：完成 0：未完成
    private String user_job_edit;  // 工作信息是否可编辑标识   1：是 0：否
    private String user_contact_status; // 联系人资料完成状态 1：完成 0：未完成
    private String user_contact_edit; // 联系人资料是否可编辑标识   1：是 0：否
    private String user_bank_status; // 用户银行卡完成状态   1：完成 0：未完成
    private String user_bank_edit; // 用户银行卡是否可编辑标识   1：是 0：否


    public String getUser_data_status() {
        return user_data_status;
    }

    public void setUser_data_status(String user_data_status) {
        this.user_data_status = user_data_status;
    }

    public String getUser_data_edit() {
        return user_data_edit;
    }

    public void setUser_data_edit(String user_data_edit) {
        this.user_data_edit = user_data_edit;
    }

    public String getUser_job_status() {
        return user_job_status;
    }

    public void setUser_job_status(String user_job_status) {
        this.user_job_status = user_job_status;
    }

    public String getUser_job_edit() {
        return user_job_edit;
    }

    public void setUser_job_edit(String user_job_edit) {
        this.user_job_edit = user_job_edit;
    }

    public String getUser_contact_status() {
        return user_contact_status;
    }

    public void setUser_contact_status(String user_contact_status) {
        this.user_contact_status = user_contact_status;
    }

    public String getUser_contact_edit() {
        return user_contact_edit;
    }

    public void setUser_contact_edit(String user_contact_edit) {
        this.user_contact_edit = user_contact_edit;
    }

    public String getUser_bank_status() {
        return user_bank_status;
    }

    public void setUser_bank_status(String user_bank_status) {
        this.user_bank_status = user_bank_status;
    }

    public String getUser_bank_edit() {
        return user_bank_edit;
    }

    public void setUser_bank_edit(String user_bank_edit) {
        this.user_bank_edit = user_bank_edit;
    }

    @Override
    public String toString() {
        return "WriteInfoBean{" +
                "user_data_status='" + user_data_status + '\'' +
                ", user_data_edit='" + user_data_edit + '\'' +
                ", user_job_status='" + user_job_status + '\'' +
                ", user_job_edit='" + user_job_edit + '\'' +
                ", user_contact_status='" + user_contact_status + '\'' +
                ", user_contact_edit='" + user_contact_edit + '\'' +
                ", user_bank_status='" + user_bank_status + '\'' +
                ", user_bank_edit='" + user_bank_edit + '\'' +
                '}';
    }
}
