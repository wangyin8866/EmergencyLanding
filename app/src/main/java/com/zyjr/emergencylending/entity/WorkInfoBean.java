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
    private String unit_adr; // 单位地址 省市区
    private String unit_province; // 单位所在省
    private String unit_province_name; // 单位所在省名称
    private String unit_city; // 单位所在市
    private String unit_city_name; // 单位所在市名称
    private String unit_county; // 单位所在区
    private String unit_county_name; // 单位所在区名称
    private String unit_adr_detail; // 单位详细地址
    private String unit_phone; //单位电话
    private String unit_department; // 单位部门
    private String title; // 职位
    private String professional; // 职业
    private String month_pay; // 月薪
    private String id;
    private String create_date;
    private String del_flag;

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
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

    public String getUnit_province() {
        return unit_province;
    }

    public void setUnit_province(String unit_province) {
        this.unit_province = unit_province;
    }

    public String getUnit_province_name() {
        return unit_province_name;
    }

    public void setUnit_province_name(String unit_province_name) {
        this.unit_province_name = unit_province_name;
    }

    public String getUnit_city() {
        return unit_city;
    }

    public void setUnit_city(String unit_city) {
        this.unit_city = unit_city;
    }

    public String getUnit_city_name() {
        return unit_city_name;
    }

    public void setUnit_city_name(String unit_city_name) {
        this.unit_city_name = unit_city_name;
    }

    public String getUnit_county() {
        return unit_county;
    }

    public void setUnit_county(String unit_county) {
        this.unit_county = unit_county;
    }

    public String getUnit_county_name() {
        return unit_county_name;
    }

    public void setUnit_county_name(String unit_county_name) {
        this.unit_county_name = unit_county_name;
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

    public String getMonth_pay() {
        return month_pay;
    }

    public void setMonth_pay(String month_pay) {
        this.month_pay = month_pay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "WorkInfoBean{" +
                "unit_name='" + unit_name + '\'' +
                ", unit_nature='" + unit_nature + '\'' +
                ", unit_industry='" + unit_industry + '\'' +
                ", unit_adr='" + unit_adr + '\'' +
                ", unit_province='" + unit_province + '\'' +
                ", unit_province_name='" + unit_province_name + '\'' +
                ", unit_city='" + unit_city + '\'' +
                ", unit_city_name='" + unit_city_name + '\'' +
                ", unit_county='" + unit_county + '\'' +
                ", unit_county_name='" + unit_county_name + '\'' +
                ", unit_adr_detail='" + unit_adr_detail + '\'' +
                ", unit_phone='" + unit_phone + '\'' +
                ", unit_department='" + unit_department + '\'' +
                ", title='" + title + '\'' +
                ", professional='" + professional + '\'' +
                ", month_pay='" + month_pay + '\'' +
                ", id='" + id + '\'' +
                ", create_date='" + create_date + '\'' +
                ", del_flag='" + del_flag + '\'' +
                '}';
    }
}
