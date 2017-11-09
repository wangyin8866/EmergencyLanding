package com.zyjr.emergencylending.utils;

import com.zyjr.emergencylending.entity.SupportCityBean;

import java.util.Comparator;

/**
 * 按拼音排序
 */
public class PinyinComparator implements Comparator<SupportCityBean> {

//    public int compare(SupportCityBean o1, SupportCityBean o2) {
//        if (o1.getFirstName().equals("@") || o2.getFirstName().equals("#")) {
//            return -1;
//        } else if (o1.getFirstName().equals("#") || o2.getFirstName().equals("@")) {
//            return 1;
//        } else {
//            return (o1.getPinyin()).compareTo(o2.getPinyin());
//        }
//    }

    public int compare(SupportCityBean o1, SupportCityBean o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o2.getPinyin().equals("#")) {
            return -1;
        } else if (o1.getPinyin().equals("#")) {
            return 1;
        } else {
            return o1.getPinyin().compareTo(o2.getPinyin());
        }
    }

}
