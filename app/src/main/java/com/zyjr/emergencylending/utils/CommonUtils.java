package com.zyjr.emergencylending.utils;

/**
 * Created by User on 2017/7/12.
 */

public class CommonUtils {

    /**
     * 格式化电话号码（显示前3后4中间用*代替）
     *
     * @param tel
     * @return
     */
    public static String FormatTel(String tel) {
        if (tel != null && tel.length() == 11) {
            return tel.substring(0, 3) + "****" + tel.substring(7, 11);
        } else {
            return "格式错误";
        }
    }
}
