package com.zyjr.emergencylending.config;

import android.content.Context;

import com.zyjr.emergencylending.utils.NetworkUtils;
import com.zyjr.emergencylending.utils.PhoneInfoUtils;
import com.zyjr.emergencylending.utils.WYUtils;

/**
 * @author wangyin
 * @date 2017/10/12
 */

public class Constants {
    /**
     * 判断是否更新APK
     */
    public static boolean update = true;

    /**
     * 身份证信息扫描|活体验证
     */
    public static final String FACE_APPKEY = "pI75sT_t2hb3nlyPLx2xgPzEOC1joAJz";
    public static final String FACE_APPSECRET = "6XDvhkOPeRSl5vh_bQL6RQKkAPbxOPzi";

    /**
     * 平台号
     *
     * @param type
     * @return
     */
    public static String getAppType(int type) {
        if (1 == type) {
            return "JJT_1";
        } else if (2 == type) {
            return "YXT_1";
        }
        return "JJT_1";
    }

    /**
     * 设备号
     */
    public static String getDeviceCode() {

        return PhoneInfoUtils.getPhoneBrand() + "," + PhoneInfoUtils.getPhoneModel() + "," + PhoneInfoUtils.getSystemVersion();
    }

    /**
     * 网络IP
     */
    public static String getNetIp(Context context) {

        return NetworkUtils.getIp(context);
    }


    /**
     * 版本号
     */
    public static String getVersionCode(Context context) {

        return String.valueOf(WYUtils.getVersionCode(context));
    }
}
