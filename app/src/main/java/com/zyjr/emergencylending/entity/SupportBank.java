package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/23
 * 支持银行
 */
public class SupportBank implements Serializable {

    private static final long serialVersionUID = -2368450754576205050L;

    /**
     * id_ : 24821464997822544
     * name_ : 银行编码
     * key_ : BANK_CODE_
     * code_ : 203
     * desc_ : 中国农业发展银行
     * is_enable_ : 1
     * group_ : 2
     * sort_no_ : 8
     */
    private String id_;
    private String name_;   // 名称
    private String key_;  // 对照吗
    private String remark_;  // 备注
    private String code_;
    private String desc_;
    private String is_enable_;
    private String group_;
    private int sort_no_;

    public String getId_() {
        return id_;
    }

    public void setId_(String id_) {
        this.id_ = id_;
    }

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    public String getKey_() {
        return key_;
    }

    public void setKey_(String key_) {
        this.key_ = key_;
    }

    public String getCode_() {
        return code_;
    }

    public void setCode_(String code_) {
        this.code_ = code_;
    }

    public String getDesc_() {
        return desc_;
    }

    public void setDesc_(String desc_) {
        this.desc_ = desc_;
    }

    public String getIs_enable_() {
        return is_enable_;
    }

    public void setIs_enable_(String is_enable_) {
        this.is_enable_ = is_enable_;
    }

    public String getGroup_() {
        return group_;
    }

    public void setGroup_(String group_) {
        this.group_ = group_;
    }

    public int getSort_no_() {
        return sort_no_;
    }

    public void setSort_no_(int sort_no_) {
        this.sort_no_ = sort_no_;
    }

    public String getRemark_() {
        return remark_;
    }

    public void setRemark_(String remark_) {
        this.remark_ = remark_;
    }

    @Override
    public String toString() {
        return "SupportBank{" +
                "id_='" + id_ + '\'' +
                ", name_='" + name_ + '\'' +
                ", key_='" + key_ + '\'' +
                ", remark_='" + remark_ + '\'' +
                ", code_='" + code_ + '\'' +
                ", desc_='" + desc_ + '\'' +
                ", is_enable_='" + is_enable_ + '\'' +
                ", group_='" + group_ + '\'' +
                ", sort_no_=" + sort_no_ +
                '}';
    }
}
