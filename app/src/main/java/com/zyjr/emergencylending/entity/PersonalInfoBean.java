package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/23
 * 个人信息资料bean
 */
public class PersonalInfoBean implements Serializable {

    private static final long serialVersionUID = -2368450930576205050L;

    private String username; // 用户姓名
    private String idCard; // 用户身份证
    private String marriage; // 婚姻状况
    private String huji_adr; // 户籍省市区
    private String huji_province; // 户籍所在省
    private String huji_province_name; // 户籍所在省名称
    private String huji_city; // 户籍所在市
    private String huji_city_name; // 户籍所在市名称
    private String huji_county; // 户籍所在区
    private String huji_county_name; // 户籍所在区名称
    private String huji_adr_detail; // 户籍详细地址
    private String live_adr; // 现居住地址省市区
    private String live_province;  // 现住址省
    private String live_province_name;  // 现住址省名称
    private String live_city; // 现住址市
    private String live_city_name; // 现住址市名称
    private String live_county; // 现住址区
    private String live_county_name; // 现住址区名称
    private String live_adr_detail; // 现居住详细地址
    private String qq_no; // QQ号
    private String wechat_no; // 微信号
    private String idcard_z; // 身份证正面
    private String idcard_f; // 身份证反面
    private String idcard_hand; // 手持身份证
    private String live_status; // 居住状况
    private String max_repay_amount; // 每周最大还款额
    private String six_monbill;  // 六个月话费详单(1有,0无)

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getHuji_adr() {
        return huji_adr;
    }

    public void setHuji_adr(String huji_adr) {
        this.huji_adr = huji_adr;
    }

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

    public String getHuji_adr_detail() {
        return huji_adr_detail;
    }

    public void setHuji_adr_detail(String huji_adr_detail) {
        this.huji_adr_detail = huji_adr_detail;
    }

    public String getLive_adr() {
        return live_adr;
    }

    public void setLive_adr(String live_adr) {
        this.live_adr = live_adr;
    }

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

    public String getQq_no() {
        return qq_no;
    }

    public void setQq_no(String qq_no) {
        this.qq_no = qq_no;
    }

    public String getWechat_no() {
        return wechat_no;
    }

    public void setWechat_no(String wechat_no) {
        this.wechat_no = wechat_no;
    }

    public String getIdcard_z() {
        return idcard_z;
    }

    public void setIdcard_z(String idcard_z) {
        this.idcard_z = idcard_z;
    }

    public String getIdcard_f() {
        return idcard_f;
    }

    public void setIdcard_f(String idcard_f) {
        this.idcard_f = idcard_f;
    }

    public String getIdcard_hand() {
        return idcard_hand;
    }

    public void setIdcard_hand(String idcard_hand) {
        this.idcard_hand = idcard_hand;
    }

    public String getLive_status() {
        return live_status;
    }

    public void setLive_status(String live_status) {
        this.live_status = live_status;
    }

    public String getMax_repay_amount() {
        return max_repay_amount;
    }

    public void setMax_repay_amount(String max_repay_amount) {
        this.max_repay_amount = max_repay_amount;
    }

    public String getSix_monbill() {
        return six_monbill;
    }

    public void setSix_monbill(String six_monbill) {
        this.six_monbill = six_monbill;
    }

    @Override
    public String toString() {
        return "PersonalInfoBean{" +
                "username='" + username + '\'' +
                ", idCard='" + idCard + '\'' +
                ", marriage='" + marriage + '\'' +
                ", huji_adr='" + huji_adr + '\'' +
                ", huji_province='" + huji_province + '\'' +
                ", huji_province_name='" + huji_province_name + '\'' +
                ", huji_city='" + huji_city + '\'' +
                ", huji_city_name='" + huji_city_name + '\'' +
                ", huji_county='" + huji_county + '\'' +
                ", huji_county_name='" + huji_county_name + '\'' +
                ", huji_adr_detail='" + huji_adr_detail + '\'' +
                ", live_adr='" + live_adr + '\'' +
                ", live_province='" + live_province + '\'' +
                ", live_province_name='" + live_province_name + '\'' +
                ", live_city='" + live_city + '\'' +
                ", live_city_name='" + live_city_name + '\'' +
                ", live_county='" + live_county + '\'' +
                ", live_county_name='" + live_county_name + '\'' +
                ", live_adr_detail='" + live_adr_detail + '\'' +
                ", qq_no='" + qq_no + '\'' +
                ", wechat_no='" + wechat_no + '\'' +
                ", idcard_z='" + idcard_z + '\'' +
                ", idcard_f='" + idcard_f + '\'' +
                ", idcard_hand='" + idcard_hand + '\'' +
                ", live_status='" + live_status + '\'' +
                ", max_repay_amount='" + max_repay_amount + '\'' +
                ", six_monbill='" + six_monbill + '\'' +
                '}';
    }
}
