package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/14
 * 备注: 门店实体bean
 */
public class StoreBean implements Serializable {

    private static final long serialVersionUID = -2368450930686205050L;
    private String storeName; // 名称
    private String storeAddress; // 地址
    private String storeNumber; // 编排号
    private boolean isSelected; // 是否选中

    public StoreBean(String storeName, String storeAddress, String storeNumber, boolean isSelected) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storeNumber = storeNumber;
        this.isSelected = isSelected;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
