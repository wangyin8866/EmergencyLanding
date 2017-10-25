package com.zyjr.emergencylending.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 领取借款 bean
 * Created by neil on 2017/10/24.
 */
public class ReceiveMoneyBean implements Serializable {

    private static final long serialVersionUID = -2368558430146205050L;

    private String loan_amount; // 借款金额
    private String loan_period; // 借款周期
    private String period_amount; // 期还款额
    private String loan_unit; // 周期单位
    private List<Contract> loan_contract; // 借款合同模板

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getLoan_period() {
        return loan_period;
    }

    public void setLoan_period(String loan_period) {
        this.loan_period = loan_period;
    }

    public String getPeriod_amount() {
        return period_amount;
    }

    public void setPeriod_amount(String period_amount) {
        this.period_amount = period_amount;
    }

    public String getLoan_unit() {
        return loan_unit;
    }

    public void setLoan_unit(String loan_unit) {
        this.loan_unit = loan_unit;
    }

    public List<Contract> getLoan_contract() {
        return loan_contract;
    }

    public void setLoan_contract(List<Contract> loan_contract) {
        this.loan_contract = loan_contract;
    }

    /**
     * 借款合同模板
     */
    public static class Contract {
        private String contract_no; // 合同模板标号
        private String contract_url; // 合同模板url
        private String contract_name; // 合同名称

        public String getContract_no() {
            return contract_no;
        }

        public void setContract_no(String contract_no) {
            this.contract_no = contract_no;
        }

        public String getContract_url() {
            return contract_url;
        }

        public void setContract_url(String contract_url) {
            this.contract_url = contract_url;
        }

        public String getContract_name() {
            return contract_name;
        }

        public void setContract_name(String contract_name) {
            this.contract_name = contract_name;
        }
    }


    @Override
    public String toString() {
        return "ReceiveMoneyBean{" +
                "loan_amount='" + loan_amount + '\'' +
                ", loan_period='" + loan_period + '\'' +
                ", period_amount='" + period_amount + '\'' +
                ", loan_unit='" + loan_unit + '\'' +
                ", loan_contract=" + loan_contract +
                '}';
    }
}
