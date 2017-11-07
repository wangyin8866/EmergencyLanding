package com.zyjr.emergencylending.config;

/**
 * @author wangyin
 * @date 2017/10/14.
 * @description :字符串常量配置
 */

public interface Config {
    /**
     * 倒计时时间
     */
    int seconds = 60000;

    String TIP_ALL = "您所输入的账号或密码有误，请重新输入！";
    String TIP_NET_ERROR = "网络异常！";
    /**
     * 每页数据
     */
    int PAGE_SIZE = 15;

    /**
     * 请求成功
     */
    String CODE_SUCCESS = "API0000";
    /**
     * 系统错误
     */
    String CODE_SYS_ERROR= "API9999";
    /**
     * 缺少参数或格式不正确
     */
    String CODE_ARGUMENT_ERROR= "API9999";
    /**
     * 业务异常
     */
    String CODE_BUSINESS_EXCEPTION= "API0004";


    /**
     * 用户ID
     */
    String KEY_JUID = "juid";
    /**
     * client_ID
     */
    String KEY_CLIENT_ID = "client_id";
    /**
     * 用户token
     */
    String KEY_TOKEN = "token";
    /**
     * 用户标识  1：业务员；0：普通客户
     */
    String KEY_USER_TYPE = "user_type";
    String USER_COMMON = "0";
    String USER_SALESMAN = "1";

    /**
     * 推荐码
     */
    String KEY_RECOMMEND_CODE = "recommend_code";
    /**
     * 客户Id
     */
    String KEY_CUST_ID = "cust_juid";
    /**
     * 是否登录
     */
    String KEY_LOGIN = "is_login";
    String KEY_PHONE = "phone";
    /**
     * h5 Key
     */
    /**
     * 关于我们
     */
    String H5_URL_HELP = "H5_URL_HELP";
    /**
     * 帮助说明
     */
    String H5_URL_ABOUTUS = "H5_URL_ABOUTUS";
    /**
     * 订单状态
     */
    String H5_URL_ORDERSTATUS = "H5_URL_ORDERSTATUS";
    /**
     * 龙虎榜
     */
    String H5_URL_WINNERSLIST = "H5_URL_WINNERSLIST";
    /**
     * 客户管理模块-我的业绩子页面(客户)
     */
    String H5_URL_MYRESULTS_CUSTOMER = "H5_URL_MYRESULTS_CUSTOMER";
    /**
     * 客户管理模块-我的业绩子页面(申请)
     */
    String H5_URL_MYRESULTS_APPLY = "H5_URL_MYRESULTS_APPLY";
    /**
     * //业务员首页(更多动态)
     */
    String H5_URL_MYRESULTS_SUCCESS = "H5_URL_MYRESULTS_SUCCESS";
    /**
     * 推荐有奖
     */
    String H5_URL_INVITE = "H5_URL_INVITE";
    /**
     * //业务员首页(更多动态)
     */
    String H5_URL_CLERKHOMEPAGE = "H5_URL_CLERKHOMEPAGE";
    /**
     * //帮助说明(客户经理)
     */
    String H5_URL_CLERKHELP = "H5_URL_CLERKHELP";
    /**
     * //收入明细
     */
    String H5_URL_INCOMEDETAIL = "H5_URL_INCOMEDETAIL";
    /**
     * //我的收入
     */
    String H5_URL_MYINCOME = "H5_URL_MYINCOME";
    /**
     * //我的榜单
     */
    String H5_URL_MYLIST = "H5_URL_MYLIST";
    /**
     * //活动详情
     */
    String H5_URL_ACTIVITYDETAILS = "H5_URL_ACTIVITYDETAILS";
    /**
     * //活动列表
     */
    String H5_URL_ACTIVITYLIST = "H5_URL_ACTIVITYLIST";
    /**
     * //我的名片
     */
    String H5_URL_MYCARD = "H5_URL_MYCARD";
    /**
     * //客服电话
     */
    String H5_URL_SERVICETEL = "H5_URL_SERVICETEL";
    /**
     * //意见反馈
     */
    String H5_URL_FEEDBACK = "H5_URL_FEEDBACK";


    String TRUE = "1";
    String FALSE = "0";


    /**
     * 业务员门店信息 API2022
     */

}
