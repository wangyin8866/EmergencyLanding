package com.zyjr.emergencylending.entity;

/**
 * Created by wangyin on 2017/10/21.
 */

public class ApplyBean {
    private String name;
    private double amount;
    private String phone;
    private String deadline;
    private String status;
    private String date;

    public ApplyBean(String name, double amount, String phone, String deadline, String status, String date) {
        this.name = name;
        this.amount = amount;
        this.phone = phone;
        this.deadline = deadline;
        this.status = status;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
