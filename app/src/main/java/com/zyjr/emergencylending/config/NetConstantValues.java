package com.zyjr.emergencylending.config;

/**
 *
 * @author wangyin
 * @date 2017/10/6
 */

public interface NetConstantValues {
    /**
     * 测试服务器
     */
    String HOST_URL = "http://192.168.6.107:8086/";

    /**
     * 二级地址
     */
    String LOAN = "jjt-api/http/do";

    /**
     * 订单状态
     */

    String order = "http://192.168.6.107:9090/jjth5/orderStatus.html";
    /**
     * 关于我们
     */
    String ABOUT_US = "http://192.168.6.107:9090/jjth5/about.html";
    /**
     * 帮助说明
     */
    String HELP = "http://192.168.6.107:9090/jjth5/question.html";
    /**
     *  我的收入
     */
    String MY_INCOME = "http://192.168.6.107:9090/jjth5/myIncome.html";
    /**
     * 我的名片
     */
    String MY_NAME = "http://192.168.6.107:9090/jjth5/myCard.html";
    /**
     *   龙虎榜
     */
    String RANKING_LIST = "http://192.168.6.107:9090/jjth5/rankingList.html";

    /**
     * 身份证扫描
     */
    String IDCARD_URL = "https://api.faceid.com/faceid/";

    /**
     * 身份证扫描
     */
    String FACE_AUTH_URL = "https://api.megvii.com/faceid/";

    /**
     * 注册
     */
    String REGISTER = "zyUserService.register";
    /**
     * 登录
     */
    String LOGIN = "zyUserService.login";
    /**
     * 短信验证码
     */
    String SMS = "zyUserService.sendSms";
    /**
     * 重置密码
     */
    String REST_PASSWORD = "zyUserService.findPassword";

    /**
     * 版本更新
     */
    String VERISON_UPDATE = "zyAppVersionService.checkAppVersion";

}
