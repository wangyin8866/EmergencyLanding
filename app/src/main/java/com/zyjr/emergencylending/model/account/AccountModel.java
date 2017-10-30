package com.zyjr.emergencylending.model.account;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.account.LoginBean;
import com.zyjr.emergencylending.entity.account.RegisterBean;
import com.zyjr.emergencylending.service.Api;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * @author wangyin
 * @date 2017/10/24
 */

public class AccountModel extends BaseModel {
    private Api mApi;

    private AccountModel() {
        super();
        mApi = retrofit.create(Api.class);
    }

    public static class SingletonHolder {
        private static final AccountModel ACCOUNT_MODEL = new AccountModel();
    }

    public static AccountModel getInstance() {
        return SingletonHolder.ACCOUNT_MODEL;
    }

    /**
     * 注册
     */
    public Observable<RegisterBean> register(String router, String phone, String clientid,
                                             String verify_code, String password, String recommend_code,
                                             String register_ip, String register_device_no) {
        Map<String, String> map = new HashMap<String, String>(8);
        map.put("router", router);
        map.put("phone", phone);
        map.put("clientid", clientid);
        map.put("verify_code", verify_code);
        map.put("password", password);
        map.put("recommend_code", recommend_code);
        map.put("register_ip", register_ip);
        map.put("register_device_no", register_device_no);
        return mApi.register(map);
    }

    /**
     * 登录
     */
    public Observable<LoginBean> login(String router, String phone, String password,
                                       String clientid, String login_ip, String login_platform, String login_device_no) {
        Map<String, String> map = new HashMap<String, String>(6);
        map.put("router", router);
        map.put("phone", phone);
        map.put("password", password);
        map.put("clientid", clientid);
        map.put("login_ip", login_ip);
        map.put("login_platform", login_platform);
        map.put("login_device_no", login_device_no);
        return mApi.login(map);
    }

    /**
     * 发送短信验证码
     *
     * @param router
     * @param phone
     * @return
     */
    public Observable<BaseBean> sendSMS(String router, String phone
    ) {
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("router", router);
        map.put("phone", phone);
        return mApi.sendSMS(map);
    }

    /**
     * 忘记密码
     */
    public Observable<BaseBean> forgetPassword(String router, String phone,
                                               String verify_code, String password) {
        Map<String, String> map = new HashMap<String, String>(4);
        map.put("router", router);
        map.put("phone", phone);
        map.put("verify_code", verify_code);
        map.put("password", password);
        return mApi.sendSMS(map);
    }
}
