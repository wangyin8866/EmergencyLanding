package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * 数据库 银行bean
 * Created by neil on 2017/10/24
 */
public class BankDbBean implements Serializable{

    private static final long serialVersionUID = -2368550840756205050L;

    private String BIN;

    private String Bank_name;

    private String Bank_code;

    private String is_enable;

    public String getBIN() {
        return BIN;
    }

    public void setBIN(String BIN) {
        this.BIN = BIN;
    }

    public String getBank_name() {
        return Bank_name;
    }

    public void setBank_name(String bank_name) {
        Bank_name = bank_name;
    }

    public String getBank_code() {
        return Bank_code;
    }

    public void setBank_code(String bank_code) {
        Bank_code = bank_code;
    }

    public String getIs_enable() {
        return is_enable;
    }

    public void setIs_enable(String is_enable) {
        this.is_enable = is_enable;
    }

    @Override
    public String toString() {
        return "BankDbBean{" +
                "BIN='" + BIN + '\'' +
                ", Bank_name='" + Bank_name + '\'' +
                ", Bank_code='" + Bank_code + '\'' +
                ", is_enable='" + is_enable + '\'' +
                '}';
    }
}
