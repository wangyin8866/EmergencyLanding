package com.zyjr.emergencylending.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 预检结果 bean
 *
 * @author neil
 * @date 2017/11/20
 */
public class PrecheckResultBean implements Serializable {

    private static final long serialVersionUID = -2312625430146205050L;

    private String submitFlag; // 提交标示
    private String is_run_risk; // 是否过风控首贷策略 1:是 0:否
    private String renew_loans; // 线上续贷标识 0:首贷 3:续贷
    private String renew_loans_grade; // 线上续贷等级 0:较差 1:一般 2:优质;备注: renew_loans为3才有值
    private String out_push_flag; // 外推标识
    private String out_push_url; // 外推地址
    private String out_push_image_url; // 外推图片地址
    private String out_push_title; // 外推标题

    private List<PrecheckResultBean.LoanProduct> loan_products;  //  审批结果集合

    public String getSubmitFlag() {
        return submitFlag;
    }

    public void setSubmitFlag(String submitFlag) {
        this.submitFlag = submitFlag;
    }

    public String getIs_run_risk() {
        return is_run_risk;
    }

    public void setIs_run_risk(String is_run_risk) {
        this.is_run_risk = is_run_risk;
    }

    public String getRenew_loans() {
        return renew_loans;
    }

    public void setRenew_loans(String renew_loans) {
        this.renew_loans = renew_loans;
    }

    public String getRenew_loans_grade() {
        return renew_loans_grade;
    }

    public void setRenew_loans_grade(String renew_loans_grade) {
        this.renew_loans_grade = renew_loans_grade;
    }

    public String getOut_push_flag() {
        return out_push_flag;
    }

    public void setOut_push_flag(String out_push_flag) {
        this.out_push_flag = out_push_flag;
    }

    public String getOut_push_url() {
        return out_push_url;
    }

    public void setOut_push_url(String out_push_url) {
        this.out_push_url = out_push_url;
    }

    public String getOut_push_image_url() {
        return out_push_image_url;
    }

    public void setOut_push_image_url(String out_push_image_url) {
        this.out_push_image_url = out_push_image_url;
    }

    public String getOut_push_title() {
        return out_push_title;
    }

    public void setOut_push_title(String out_push_title) {
        this.out_push_title = out_push_title;
    }

    public List<LoanProduct> getLoan_products() {
        return loan_products;
    }

    public void setLoan_products(List<LoanProduct> loan_products) {
        this.loan_products = loan_products;
    }

    // 审批结果集合
    public static class LoanProduct implements Serializable{

        private static final long serialVersionUID = -2527425430146205050L;

        private String loan_amount; // 审批金额
        private String loan_periods; // 审批期数
        private String loan_unit; // 审批期数单位
        private String loan_spac; // 审批期数间隔
        private boolean is_selected; // 是否选中
        private String is_first; // 是否首推

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

        public String getLoan_unit() {
            return loan_unit;
        }

        public void setLoan_unit(String loan_unit) {
            this.loan_unit = loan_unit;
        }

        public String getLoan_spac() {
            return loan_spac;
        }

        public void setLoan_spac(String loan_spac) {
            this.loan_spac = loan_spac;
        }

        public boolean getIs_selected() {
            return is_selected;
        }

        public void setIs_selected(boolean is_selected) {
            this.is_selected = is_selected;
        }

        public String getIs_first() {
            return is_first;
        }

        public void setIs_first(String is_first) {
            this.is_first = is_first;
        }
    }

    /**
     * Created by neil on 2017/10/14
     * 备注: 产品介绍bean
     */
    public static class ProIntroduceBean implements Serializable {

        private static final long serialVersionUID = -2164450930686205050L;

        private List<String> product_info;

        public List<String> getProduct_info() {
            return product_info;
        }

        public void setProduct_info(List<String> product_info) {
            this.product_info = product_info;
        }
    }
}
