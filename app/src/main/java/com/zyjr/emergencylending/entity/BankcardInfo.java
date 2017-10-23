package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/23
 * 备注: 银行卡信息
 */
public class BankcardInfo implements Serializable {

    private static final long serialVersionUID = -2345450930426365050L;

    private String bank_name; // 开户行
    private String bank_code; // 银行行号
    private String bankcard_no; // 银行卡号
    private String bank_phone; // 银行预留手机号
    private String bank_username; //户主名
    private String id_card; // 身份证号码

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBankcard_no() {
        return bankcard_no;
    }

    public void setBankcard_no(String bankcard_no) {
        this.bankcard_no = bankcard_no;
    }

    public String getBank_phone() {
        return bank_phone;
    }

    public void setBank_phone(String bank_phone) {
        this.bank_phone = bank_phone;
    }

    public String getBank_username() {
        return bank_username;
    }

    public void setBank_username(String bank_username) {
        this.bank_username = bank_username;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    @Override
    public String toString() {
        return "BankcardInfo{" +
                "bank_name='" + bank_name + '\'' +
                ", bank_code='" + bank_code + '\'' +
                ", bankcard_no='" + bankcard_no + '\'' +
                ", bank_phone='" + bank_phone + '\'' +
                ", bank_username='" + bank_username + '\'' +
                ", id_card='" + id_card + '\'' +
                '}';
    }
}
