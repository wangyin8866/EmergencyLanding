package com.zyjr.emergencylending.utils;

import com.zyjr.emergencylending.entity.CityModel;
import java.util.Comparator;

/**
 * 按拼音排序
 */
public class PinyinComparator implements Comparator<CityModel> {

    public int compare(CityModel o1, CityModel o2) {
        if (o1.getFirstName().equals("@") || o2.getFirstName().equals("#")) {
            return -1;
        } else if (o1.getFirstName().equals("#") || o2.getFirstName().equals("@")) {
            return 1;
        } else {
            return (o1.getPingyin()).compareTo(o2.getPingyin());
        }
    }

}
