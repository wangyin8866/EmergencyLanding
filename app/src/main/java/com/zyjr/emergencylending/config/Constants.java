package com.zyjr.emergencylending.config;

import android.content.Context;

import com.zyjr.emergencylending.utils.NetworkUtils;
import com.zyjr.emergencylending.utils.PhoneInfoUtils;
import com.zyjr.emergencylending.utils.WYUtils;

/**
 * Created by wangyin on 2017/10/12.
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
    public static String getPlatform(int type) {
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
    // 上传身份证正面
    public static final String UPLOAD_IDCARD_FRONT = "15";
    // 上传身份证反面
    public static final String UPLOAD_IDCARD_BACK = "16";
    // 上传手持
    public static final String UPLOAD_IDCARD_HOLD = "17";
    // 添加个人资料
    public static final String ADD_PERSONAL_INFO = "20";
    // 获取个人资料
    public static final String GET_PERSONAL_INFO = "21";
    // 修改个人资料
    public static final String EDIT_PERSONAL_INFO = "22";
    // 添加工作信息
    public static final String ADD_WORK_INFO = "23";
    // 获取工作信息
    public static final String GET_WORK_INFO = "24";
    // 修改工作信息
    public static final String EDIT_WORK_INFO = "25";
    // 添加联系人信息
    public static final String ADD_CONTACT_INFO = "26";
    // 获取联系人信息
    public static final String GET_CONTACT_INFO = "27";
    // 修改联系人信息
    public static final String EDIT_CONTACT_INFO = "28";
    // 获取绑定银行卡信息
    public static final String GET_BIND_BANKCARD_INFO = "29";
    // 添加绑定银行卡信息
    public static final String ADD_BIND_BANKCARD_INFO = "30";
    // 删除绑定银行卡信息
    public static final String EDIT_BIND_BANKCARD_INFO = "31";
    // 获取支持的银行信息
    public static final String GET_SUPPORT_BANK_INFO = "32";

}
