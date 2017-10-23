package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/13.
 * 工作信息bean
 */
public class WorkInfoBean implements Serializable {

    private static final long serialVersionUID = -2368450930576365050L;

    private String unit_name; // 单位名称
    private String unit_nature; // 单位性质
    private String unit_industry; // 单位行业
    private String unit_province; // 单位所在省
    private String unit_city; // 单位所在市
    private String unit_county; // 单位所在区
    private String unit_adr_detail; // 单位详细地址
    private String unit_phone; //单位电话
    private String unit_department; // 单位部门
    private String entry_date; // 入职日期
    private String title; // 职位
    private String professional; // 职业
    private String month_payday; // 月发薪日
    private String month_pay; // 月薪
    private String pay_channel; // 发薪渠道
    private String month_repay_amount_max; // 每月最多还款额
    private String other_income; // 其他收入

    private String id;
    private String user_id;
    private String unit_adr;
    private String create_date;
    private String del_flag;

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUnit_nature() {
        return unit_nature;
    }

    public void setUnit_nature(String unit_nature) {
        this.unit_nature = unit_nature;
    }

    public String getUnit_industry() {
        return unit_industry;
    }

    public void setUnit_industry(String unit_industry) {
        this.unit_industry = unit_industry;
    }

    public String getUnit_adr() {
        return unit_adr;
    }

    public void setUnit_adr(String unit_adr) {
        this.unit_adr = unit_adr;
    }

    public String getUnit_adr_detail() {
        return unit_adr_detail;
    }

    public void setUnit_adr_detail(String unit_adr_detail) {
        this.unit_adr_detail = unit_adr_detail;
    }

    public String getUnit_phone() {
        return unit_phone;
    }

    public void setUnit_phone(String unit_phone) {
        this.unit_phone = unit_phone;
    }

    public String getUnit_department() {
        return unit_department;
    }

    public void setUnit_department(String unit_department) {
        this.unit_department = unit_department;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getMonth_payday() {
        return month_payday;
    }

    public void setMonth_payday(String month_payday) {
        this.month_payday = month_payday;
    }

    public String getMonth_pay() {
        return month_pay;
    }

    public void setMonth_pay(String month_pay) {
        this.month_pay = month_pay;
    }

    public String getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel;
    }

    public String getOther_income() {
        return other_income;
    }

    public void setOther_income(String other_income) {
        this.other_income = other_income;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public String getUnit_province() {
        return unit_province;
    }

    public void setUnit_province(String unit_province) {
        this.unit_province = unit_province;
    }

    public String getUnit_city() {
        return unit_city;
    }

    public void setUnit_city(String unit_city) {
        this.unit_city = unit_city;
    }

    public String getUnit_county() {
        return unit_county;
    }

    public void setUnit_county(String unit_county) {
        this.unit_county = unit_county;
    }

    public String getMonth_repay_amount_max() {
        return month_repay_amount_max;
    }

    public void setMonth_repay_amount_max(String month_repay_amount_max) {
        this.month_repay_amount_max = month_repay_amount_max;
    }

    @Override
    public String toString() {
        return "WorkInfoBean{" +
                "unit_name='" + unit_name + '\'' +
                ", unit_nature='" + unit_nature + '\'' +
                ", unit_industry='" + unit_industry + '\'' +
                ", unit_province='" + unit_province + '\'' +
                ", unit_city='" + unit_city + '\'' +
                ", unit_county='" + unit_county + '\'' +
                ", unit_adr_detail='" + unit_adr_detail + '\'' +
                ", unit_phone='" + unit_phone + '\'' +
                ", unit_department='" + unit_department + '\'' +
                ", entry_date='" + entry_date + '\'' +
                ", title='" + title + '\'' +
                ", professional='" + professional + '\'' +
                ", month_payday='" + month_payday + '\'' +
                ", month_pay='" + month_pay + '\'' +
                ", pay_channel='" + pay_channel + '\'' +
                ", month_repay_amount_max='" + month_repay_amount_max + '\'' +
                ", other_income='" + other_income + '\'' +
                ", id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", unit_adr='" + unit_adr + '\'' +
                ", create_date='" + create_date + '\'' +
                ", del_flag='" + del_flag + '\'' +
                '}';
    }
}
