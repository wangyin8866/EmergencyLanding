package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * 可申请产品类型
 * @author neil
 * @date 2017/11/2
 */
public class MayApplyProBean implements Serializable {

    private static final long serialVersionUID = -2233254130686205050L;

    private String is_view; // 是否显示推荐产品
    private String renew_loan_flag; // 首贷续贷表示
    private String loan_amount; // 可借款额度
    private String loan_periods; // 借款期数
    private String loan_zq;  //申请期数间隔
    private String loan_unit;// 周期单位

    public String getIs_view() {
        return is_view;
    }

    public void setIs_view(String is_view) {
        this.is_view = is_view;
    }

    public String getRenew_loan_flag() {
        return renew_loan_flag;
    }

    public void setRenew_loan_flag(String renew_loan_flag) {
        this.renew_loan_flag = renew_loan_flag;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getLoan_periods() {
        return loan_periods;
    }

    public void setLoan_periods(String loan_periods) {
        this.loan_periods = loan_periods;
    }

    public String getLoan_zq() {
        return loan_zq;
    }

    public void setLoan_zq(String loan_zq) {
        this.loan_zq = loan_zq;
    }

    public String getLoan_unit() {
        return loan_unit;
    }

    public void setLoan_unit(String loan_unit) {
        this.loan_unit = loan_unit;
    }

    @Override
    public String toString() {
        return "MayApplyProBean{" +
                "is_view='" + is_view + '\'' +
                ", renew_loan_flag='" + renew_loan_flag + '\'' +
                ", loan_amount='" + loan_amount + '\'' +
                ", loan_periods='" + loan_periods + '\'' +
                ", loan_zq='" + loan_zq + '\'' +
                ", loan_unit='" + loan_unit + '\'' +
                '}';
    }
}
