package com.zyjr.emergencylending.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by neil on 2017/10/14
 * 备注: 门店实体bean
 */
public class StoreResultBean implements Serializable {

    private static final long serialVersionUID = -2368450930686205050L;

    private List<StoreBean> storePoList;

    public List<StoreBean> getStorePoList() {
        return storePoList;
    }

    public void setStorePoList(List<StoreBean> storePoList) {
        this.storePoList = storePoList;
    }

    public static class StoreBean {
        private String storeId; //
        private String storeName; // 名称
        private String storeAddr; // 地址
        private boolean isSelected; // 是否选中

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreAddr() {
            return storeAddr;
        }

        public void setStoreAddr(String storeAddr) {
            this.storeAddr = storeAddr;
        }

        public boolean getSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }


}
