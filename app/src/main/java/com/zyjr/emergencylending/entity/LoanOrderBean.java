package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * 借款订单bean
 * Created by neil on 2017/10/24
 */
public class LoanOrderBean implements Serializable {

    private static final long serialVersionUID = -2368550830576205050L;

    private String order_status; // 订单状态
    private String step_status; // 步骤码
    private String out_push_url; //外推URL
    private String out_push_image_url; // 外推图片URL
    private String out_push_title; // 外推标题
    private String order_code_desc;  // 订单状态返回码描述
    private String product_name; // 产品名称
    private String loan_amount; // 借款金额
    private String loan_zq; // 借款周期
    private String zq_unit; // 周期单位
    private String is_clerk_opt; // 是否业务员操作 1:业务员;0:用户
    private String is_verified_contract; //是否验证过通讯录
    private String product_id; //线上线下标识

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getStep_status() {
        return step_status;
    }

    public void setStep_status(String step_status) {
        this.step_status = step_status;
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

    public String getOrder_code_desc() {
        return order_code_desc;
    }

    public void setOrder_code_desc(String order_code_desc) {
        this.order_code_desc = order_code_desc;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getLoan_zq() {
        return loan_zq;
    }

    public void setLoan_zq(String loan_zq) {
        this.loan_zq = loan_zq;
    }

    public String getZq_unit() {
        return zq_unit;
    }

    public void setZq_unit(String zq_unit) {
        this.zq_unit = zq_unit;
    }

    public String getIs_clerk_opt() {
        return is_clerk_opt;
    }

    public void setIs_clerk_opt(String is_clerk_opt) {
        this.is_clerk_opt = is_clerk_opt;
    }

    public String getIs_verified_contract() {
        return is_verified_contract;
    }

    public void setIs_verified_contract(String is_verified_contract) {
        this.is_verified_contract = is_verified_contract;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "LoanOrderBean{" +
                "order_status='" + order_status + '\'' +
                ", step_status='" + step_status + '\'' +
                ", out_push_url='" + out_push_url + '\'' +
                ", out_push_image_url='" + out_push_image_url + '\'' +
                ", out_push_title='" + out_push_title + '\'' +
                ", order_code_desc='" + order_code_desc + '\'' +
                ", product_name='" + product_name + '\'' +
                ", loan_amount='" + loan_amount + '\'' +
                ", loan_zq='" + loan_zq + '\'' +
                ", zq_unit='" + zq_unit + '\'' +
                ", is_clerk_opt='" + is_clerk_opt + '\'' +
                ", is_verified_contract='" + is_verified_contract + '\'' +
                ", product_id='" + product_id + '\'' +
                '}';
    }
}
