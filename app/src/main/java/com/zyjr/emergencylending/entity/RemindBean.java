package com.zyjr.emergencylending.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 提醒集合
 *
 * @author neil
 * @date 2017/11/28
 */
public class RemindBean implements Serializable {

    private static final long serialVersionUID = -2368450930574158090L;

    private List<String> infoList; // 提醒

    public List<String> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<String> infoList) {
        infoList = infoList;
    }

}
