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
    public static boolean authRuntimePermissions;
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

    /**
     * 版本名称
     */
    public static String getVersionName(Context context) {
        return WYUtils.getAppVersionName(context);
    }


    // 获取填写信息
    public static final String GET_WRITE_INFO = "10";
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
    // 上传身份证图片
    public static final String UPLOAD_IDCARD_FILE = "33";
    // 上传通讯录资料
    public static final String UPLOAD_CONTACTS_LIST = "34";
    // 获取支持城市清单
    public static final String GET_SUPPORT_CITIES_LIST = "35";
    // 获取产品介绍
    public static final String GET_PRODUCT_INTRODUCE = "36";
    // 提交借款资料
    public static final String SUBMIT_LOAN_INFORMATION = "37";
    // 查询当前借款订单详情
    public static final String GET_CURRENT_LOAN_ORDER_INFO = "38";
    // 获取当前领取金额信息
    public static final String GET_RECEIVE_MONEY_INFO = "39";
    // 获取认证状态
    public static final String GET_AUTH_STATUS_INFO = "40";
    // 获取芝麻信用地址
    public static final String GET_ZHIMA_AUTH_URL = "41";
    // 获取芝麻信用分
    public static final String GET_ZHIMA_AUTH_SCORE = "42";
    // 获取可申请产品类型
    public static final String GET_MAYAPPLY_PRODUCT_TYPE = "43";
    // 提交人脸认证
    public static final String SUBMIT_FACE_AUTH = "44";
    // 校验运营商验证码是否有效
    public static final String JUDGE_MOBILE_CODE_VALIDE = "45";
    // 获取当前用户有效借款订单
    public static final String GET_CURRENT_EFFECTIVE_LOAN_ORDER = "46";
    // 运营商采集认证
    public static final String MOBILE_COLLECT_AUTH = "47";
    // 问题验证,保存通讯录信息
    public static final String SAVE_CONTACTS_LIST = "48";
    // 提交所有认证信息
    public static final String SUBMIT_ALL_AUTH_INFO = "49";
    // 获取门店信息
    public static final String GET_LOCAL_STORE_INFO = "50";
    // 确认领取金额
    public static final String CONFIRM_RECEIVE_INFO = "51";
    // 做废件处理
    public static final String DELETE_LOAN_ORDER_INFO = "52";
}
