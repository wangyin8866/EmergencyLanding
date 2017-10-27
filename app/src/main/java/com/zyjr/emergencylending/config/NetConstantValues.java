package com.zyjr.emergencylending.config;

/**
 * Created by DELL on 2017/1/6.
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
    String REST_PASSWORD = "zyUserService.resetPassword";

    /**
     * 版本更新
     */
    String VERSION_UPDATE = "zyAppVersionService.checkAppVersion";

    /**
     * 首页广告
     */
    String HOME_AD = "zyHomeAdService.getHomeAds";


    String BASE_PREFIX = "?router=";

    /**
     * 获取支持银行列表
     */
    String ROUTER_GET_SUPPORT_BANK_LIST = BASE_PREFIX + "zyUserBankCardService.getSupportUserBankDicInfo";
    /**
     * 获取绑定银行卡信息
     */
    String ROUTER_GET_BIND_BANK_CARD = BASE_PREFIX + "zyUserBankCardService.getUserBankCardInfo";
    /**
     * 保存银行卡
     */
    String ROUTER_ADD_BIND_BANK_CARD = BASE_PREFIX + "zyUserBankCardService.saveOrUpdateUserBankCard";
    /**
     * 修改银行卡
     */
    String ROUTER_EDIT_BIND_BANK_CARD = BASE_PREFIX + "zyUserBankCardService.saveOrUpdateUserBankCard";
    /**
     * 删除银行卡
     */
    String ROUTER_DELETE_BIND_BANK_CARD = BASE_PREFIX + "zyUserBankCardService.deleteUserBankCard";

}
