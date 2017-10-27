package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/26
 */

public class WorkAddressBean implements Serializable {

    private static final long serialVersionUID = -2334450830576650550L;

    private String unit_adr; // 单位地址 省市区
    private String unit_province; // 单位所在省
    private String unit_province_name; // 单位所在省名称
    private String unit_city; // 单位所在市
    private String unit_city_name; // 单位所在市名称
    private String unit_county; // 单位所在区
    private String unit_county_name; // 单位所在区名称
    private String unit_adr_detail; // 单位详细地址

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

    @Override
    public String toString() {
        return "WorkAddressBean{" +
                "unit_adr='" + unit_adr + '\'' +
                ", unit_province='" + unit_province + '\'' +
                ", unit_province_name='" + unit_province_name + '\'' +
                ", unit_city='" + unit_city + '\'' +
                ", unit_city_name='" + unit_city_name + '\'' +
                ", unit_county='" + unit_county + '\'' +
                ", unit_county_name='" + unit_county_name + '\'' +
                ", unit_adr_detail='" + unit_adr_detail + '\'' +
                '}';
    }
}
