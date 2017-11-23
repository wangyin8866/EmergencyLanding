package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * 续贷产品
 * @author neil
 * @date 2017/11/20
 */
public class ReloanProductBean implements Serializable {

    private static final long serialVersionUID = -2368585430146205050L;

    private String money; // 金额

    private String period; // 周期

    private boolean isSelected; // 是否选中

    private String isFirst; // 是否首推

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst;
    }

    @Override
    public String toString() {
        return "ReloanProductBean{" +
                "money='" + money + '\'' +
                ", period='" + period + '\'' +
                ", isSelected=" + isSelected +
                ", isFirst='" + isFirst + '\'' +
                '}';
    }
}
