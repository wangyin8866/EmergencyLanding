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
    int PAGE_SIZE =15;
    /**
     * 请求成功
     */
    String CODE_SUCCESS = "API0000";
    /**
     * 用户ID
     */
    String KEY_JUID = "juid";
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

}
