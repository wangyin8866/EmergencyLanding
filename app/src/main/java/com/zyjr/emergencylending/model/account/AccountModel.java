package com.zyjr.emergencylending.model.account;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.account.LoginBean;
import com.zyjr.emergencylending.service.WyApi;

import rx.Observable;

/**
 * Created by wangyin on 2017/10/24.
 */

public class AccountModel extends BaseModel {
    private WyApi mWyApi;

    private AccountModel() {
        super();
        mWyApi = retrofit.create(WyApi.class);
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
    public Observable<Object> register(String router, String phone, String clientid,
                                             String verify_code, String password, String recommend_code,
                                             String register_platform, String register_ip,String register_device_no) {
        map.clear();
        map.put("router", router);
        map.put("phone", phone);
        map.put("clientid", clientid);
        map.put("verify_code", verify_code);
        map.put("password", password);
        map.put("recommend_code", recommend_code);
        map.put("register_platform", register_platform);
        map.put("register_ip", register_ip);
        map.put("register_device_no", register_device_no);
        return mWyApi.register(map);
    }

    /**
     * 登录
     */
    public Observable<LoginBean> login(String router, String phone, String password,
                                       String clientid, String login_ip, String login_platform,String login_device_no) {
        map.clear();
        map.put("router", router);
        map.put("phone", phone);
        map.put("password", password);
        map.put("clientid", clientid);
        map.put("login_ip", login_ip);
        map.put("login_platform", login_platform);
        map.put("login_device_no", login_device_no);
        return mWyApi.login(map);
    }

    /**
     * 发送短信验证码
     * @param router
     * @param phone
     * @param registerPlatform
     * @param versionNo
     * @return
     */
    public Observable<BaseBean> sendSMS(String router, String phone,
                                        String registerPlatform, String versionNo) {
        map.clear();
        map.put("router", router);
        map.put("phone", phone);
        map.put("register_platform", registerPlatform);
        map.put("version_no", versionNo);
        return mWyApi.sendSMS(map);
    }
}
