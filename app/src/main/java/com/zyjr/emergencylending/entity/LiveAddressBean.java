package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * 居住地址bean
 * Created by neil on 2017/10/25
 */
public class LiveAddressBean implements Serializable {

    private static final long serialVersionUID = -2224450830576205050L;

    private String live_province; // 省份编码
    private String live_province_name; // 省份名称
    private String live_city; // 城市编码
    private String live_city_name;// 城市名称
    private String live_county; // 区/镇编码
    private String live_county_name; // 名称
    private String live_adr; // 居住省市区
    private String live_adr_detail; //现居住详细地址

    public String getLive_province() {
        return live_province;
    }

    public void setLive_province(String live_province) {
        this.live_province = live_province;
    }

    public String getLive_province_name() {
        return live_province_name;
    }

    public void setLive_province_name(String live_province_name) {
        this.live_province_name = live_province_name;
    }

    public String getLive_city() {
        return live_city;
    }

    public void setLive_city(String live_city) {
        this.live_city = live_city;
    }

    public String getLive_city_name() {
        return live_city_name;
    }

    public void setLive_city_name(String live_city_name) {
        this.live_city_name = live_city_name;
    }

    public String getLive_county() {
        return live_county;
    }

    public void setLive_county(String live_county) {
        this.live_county = live_county;
    }

    public String getLive_county_name() {
        return live_county_name;
    }

    public void setLive_county_name(String live_county_name) {
        this.live_county_name = live_county_name;
    }

    public String getLive_adr_detail() {
        return live_adr_detail;
    }

    public void setLive_adr_detail(String live_adr_detail) {
        this.live_adr_detail = live_adr_detail;
    }

    public String getLive_adr() {
        return live_adr;
    }

    public void setLive_adr(String live_adr) {
        this.live_adr = live_adr;
    }

    @Override
    public String toString() {
        return "LiveAddressBean{" +
                "live_province='" + live_province + '\'' +
                ", live_province_name='" + live_province_name + '\'' +
                ", live_city='" + live_city + '\'' +
                ", live_city_name='" + live_city_name + '\'' +
                ", live_county='" + live_county + '\'' +
                ", live_county_name='" + live_county_name + '\'' +
                ", live_adr='" + live_adr + '\'' +
                ", live_adr_detail='" + live_adr_detail + '\'' +
                '}';
    }
}
