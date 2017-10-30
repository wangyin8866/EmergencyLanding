package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * 户籍地址bean
 * Created by neil on 2017/10/25
 */
public class HujiAddressBean implements Serializable {

    private static final long serialVersionUID = -2334450830576205050L;

    private String huji_province; // 省份编码
    private String huji_province_name; // 省份名称
    private String huji_city; // 城市编码
    private String huji_city_name;// 城市名称
    private String huji_county; // 区/镇编码
    private String huji_county_name; // 名称
    private String huji_adr; // 户籍省市区

    public String getHuji_province() {
        return huji_province;
    }

    public void setHuji_province(String huji_province) {
        this.huji_province = huji_province;
    }

    public String getHuji_province_name() {
        return huji_province_name;
    }

    public void setHuji_province_name(String huji_province_name) {
        this.huji_province_name = huji_province_name;
    }

    public String getHuji_city() {
        return huji_city;
    }

    public void setHuji_city(String huji_city) {
        this.huji_city = huji_city;
    }

    public String getHuji_city_name() {
        return huji_city_name;
    }

    public void setHuji_city_name(String huji_city_name) {
        this.huji_city_name = huji_city_name;
    }

    public String getHuji_county() {
        return huji_county;
    }

    public void setHuji_county(String huji_county) {
        this.huji_county = huji_county;
    }

    public String getHuji_county_name() {
        return huji_county_name;
    }

    public void setHuji_county_name(String huji_county_name) {
        this.huji_county_name = huji_county_name;
    }

    public String getHuji_adr() {
        return huji_adr;
    }

    public void setHuji_adr(String huji_adr) {
        this.huji_adr = huji_adr;
    }

    @Override
    public String toString() {
        return "HujiAddressBean{" +
                "huji_province='" + huji_province + '\'' +
                ", huji_province_name='" + huji_province_name + '\'' +
                ", huji_city='" + huji_city + '\'' +
                ", huji_city_name='" + huji_city_name + '\'' +
                ", huji_county='" + huji_county + '\'' +
                ", huji_county_name='" + huji_county_name + '\'' +
                ", huji_adr='" + huji_adr + '\'' +
                '}';
    }
}
