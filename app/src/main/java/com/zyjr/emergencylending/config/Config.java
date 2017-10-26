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

    String TIP_ALL="您所输入的账号或密码有误，请重新输入！";
    String TIP_NET_ERROR="网络异常！";
    /**
     * 请求成功
     */
    String TIP_SUCCESS = "API0000";
}
