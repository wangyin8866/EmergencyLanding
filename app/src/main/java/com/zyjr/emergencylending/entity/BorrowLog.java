package com.zyjr.emergencylending.entity;

/**
 * Created by wangyin on 2017/10/16.
 */

public class BorrowLog {
    private String amount;
    private String deadline;
    private String date;
    private String status;

    public BorrowLog(String amount, String deadline, String date, String status) {
        this.amount = amount;
        this.deadline = deadline;
        this.date = date;
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
