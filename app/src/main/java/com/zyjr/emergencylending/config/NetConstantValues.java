package com.zyjr.emergencylending.config;

/**
 * @author DELL
 * @date 2017/1/6
 */

public interface NetConstantValues {
    /**
     * 测试服务器
     */
    String HOST_URL = "http://192.168.6.107:8086/";
//    String HOST_URL = "http://192.168.10.12:8080/";
    /**
     * 杨存本地
     */
//    String HOST_URL = "http://192.168.9.152:8086/";

    /**
     * 二级地址
     */
    String LOAN = "jjt-api/http/do";

    /**
     * 订单状态
     */

    String order = "http://192.168.6.107:9090/jjth5/orderStatus.html";

    /**
     * 帮助说明
     */
    String H5_URL = "zySalesmanService.getH5Url";

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
    /**
     * 消息中心
     */
    String USER_NEWS = "zyUserNewsService.getUserNews";
    /**
     * 消息操作
     */
    String UPDATE_USER_NEWS = "zyUserNewsService.updateUserNewsStatus";
    /**
     * 二维码
     */
    String QR_CODE = "userBorrowService.getMyQRCodeAndRecommendCode";

    /**
     * 获取支持银行列表
     */
    String ROUTER_GET_SUPPORT_BANK_LIST = "zyUserBankCardService.getSupportUserBankDicInfo";
    /**
     * 获取绑定银行卡信息
     */
    String ROUTER_GET_BIND_BANK_CARD = "zyUserBankCardService.getUserBankCardInfo";
    /**
     * 保存银行卡
     */
    String ROUTER_ADD_BIND_BANK_CARD = "zyUserBankCardService.saveOrUpdateUserBankCard";
    /**
     * 修改银行卡
     */
    String ROUTER_EDIT_BIND_BANK_CARD = "zyUserBankCardService.saveOrUpdateUserBankCard";
    /**
     * 删除银行卡
     */
    String ROUTER_DELETE_BIND_BANK_CARD = "zyUserBankCardService.deleteUserBankCard";
    /**
     * 获取个人资料
     */
    String ROUTER_GET_PERSONAL_INFO = "zyUserService.getUserPersonlInfo";
    /**
     * 获取用户资料
     */
    String GET_BASIC_INFO = "zyUserService.getBasicInfo";
    /**
     * 保存个人资料
     */
    String ROUTER_ADD_PERSONAL_INFO = "zyUserService.saveUserPersonlInfo";
    /**
     * 修改个人资料
     */
    String ROUTER_EDIT_PERSONAL_INFO = "zyUserService.saveUserPersonlInfo";
    /**
     * 获取联系人资料
     */
    String ROUTER_GET_CONTACT_INFO = "zyUserContactService.getUserContactInfo";
    /**
     * 添加联系人资料
     */
    String ROUTER_ADD_CONTACT_INFO = "zyUserContactService.saveUserContactInfo";
    /**
     * 修改联系人资料
     */
    String ROUTER_EDIT_CONTACT_INFO = "zyUserContactService.saveUserContactInfo";
    /**
     * 上传通讯录资料
     */
    String ROUTER_SUBMIT_CONTACTS_BOOK = "";
    /**
     * 获取填写信息(当前资料完成情况)
     */
    String ROUTER_GET_WRITE_INFO = "zyUserService.getUserInfoStatus";
    /**
     * 上传身份证图片信息
     */
    String ROUTER_UPLOAD_IDCARD_FILE = "zyUserService.uploadIdCard";
    /**
     * 上传头像图片
     */
    String ROUTER_UPLOAD_FILE = "zyUserService.uploadHead";

    /**
     * 公告清单(标题)及详情
     */
    String NOTICE_LIST = "zyNoticeService.getNoticeList";
    /**
     * 活动清单
     */
    String APP_ACTIVITYS = "zyAppActivityService.getAppActivitys";
    /**
     * 关于我们
     */
    String ABOUT_US = "zyUserService.getAboutUsUrl";
    /**
     * 获取工作信息
     */
    String ROUTER_GET_WORK_INFO = "zyUserJobService.getUserJobInfo";
    /**
     * 保存工作信息
     */
    String ROUTER_ADD_WORK_INFO = "zyUserJobService.saveUserJobInfo";
    /**
     * 修改工作信息
     */
    String ROUTER_EDIT_WORK_INFO = "zyUserJobService.saveUserJobInfo";
    /**
     * 获取产品介绍
     */
    String ROUTER_GET_PRODUCT_INTRODUCE = "zyProductInfoService.getProductExplainByProductId";
    /**
     * 获取支持城市
     */
    String ROUTER_GET_SUPPORT_CITIES_LIST = "zyStoreService.getSuppertCityInfo";
    /**
     * 业务员名片
     */
    String MY_CARD = "zySalesmanService.salesmanCard";
    /**
     * 提交借款资料
     */
    String ROUTER_SUBMIT_LOAN_INFORMATION = "userBorrowService.saveBorrowMessage";
    /**
     * 获取所有认证状态
     */
    String ROUTER_GET_AUTH_STATUS = "customerService.getAuthStatus";
    /**
     * 获取当前用户借款的订单
     */
    String ROUTER_GET_CURRENT_ORDER_INFO = "userBorrowService.orderQuery";
    /**
     * 领取借款金额
     */
    String RONTER_GET_RECEIVE_MONEY = "contractSignService.signGetAmount";
    /**
     * 我的业绩
     */
    String MY_PERFORMANCE = "zyUserService.getUserAchievement";
    /**
     * 待跟进申请
     */
    String WAIT_APPLY = "zySalesmanService.clerkRecordList";
    /**
     * 榜单
     */
    String RANKING_LIST = "zyUserService.getUserBillBoard";
    /**
     * 运营商认证采集
     */
    String ROUTER_SUBMIT_MOBILE_AUTH_INFO = "operatorService.verifyPhoneApprove";
    /**
     * 获取芝麻信用授权地址
     */
    String ROUTER_GET_ZHIMA_AUTH_URL = "customerService.zmxyAuth";
    /**
     * 获取芝麻信用分
     */
    String ROUTER_GET_ZHIMA_SCORE = "customerService.zmxyScoreGet";
    /**
     * 获取可申请产品类型
     */
    String ROUTER_GET_MAYAPPLY_PRODUCT_TYPE = "zyUserService.isViewRecommendProduct";
    /**
     * 提交人脸认证
     */
    String ROUTER_SUBMIT_FACE_AUTH = "customerService.saveFaceInfo";
    /**
     * 校验运营商验证码是否有效
     */
    String ROUTER_JUDGE_MOBILE_CODE_VALIDATE = "customerService.yysIsVaild";
    /**
     * 获取当前有效订单
     */
    String ROUTER_GET_CURRENT_EFFECTIVE_LOAN_ORDER = "userBorrowService.isEffectiveOrder";
    /**
     * 获取所在城市支持门店
     */
    String ROUTER_GET_LOCAL_STORE_LIST = "zyUserService.getStoreList";
    /**
     * 录件预检
     */
    String PRE_CHECK_BOOK = "zySalesmanService.preCheckBook"; /**
     * 录件预检
     */
    String ONLINE_TO_OFFLINE = "zySalesmanService.onlineToOffline";
}
