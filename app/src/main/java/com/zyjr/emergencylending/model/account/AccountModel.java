package com.zyjr.emergencylending.model.account;

import com.zyjr.emergencylending.entity.account.LoginBean;
import com.zyjr.emergencylending.entity.account.RegisterBean;
import com.zyjr.emergencylending.model.BaseModel;
import com.zyjr.emergencylending.service.AccountApi;

import rx.Observable;

/**
 * Created by wangyin on 2017/10/24.
 */

public class AccountModel extends BaseModel {
    private AccountApi mAccountApi;

    private AccountModel() {
        super();
        mAccountApi = retrofit.create(AccountApi.class);
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
                                             String register_platform, String register_ip) {
        map.clear();
        map.put("router", router);
        map.put("phone", phone);
        map.put("clientid", clientid);
        map.put("verify_code", verify_code);
        map.put("password", password);
        map.put("recommend_code", recommend_code);
        map.put("register_platform", register_platform);
        map.put("register_ip", register_ip);
        return mAccountApi.register(map);
    }

    /**
     * 登录
     */
    public Observable<LoginBean> login(String router, String phone, String password,
                                       String clientid, String login_ip, String login_platform) {
        map.clear();
        map.put("router", router);
        map.put("phone", phone);
        map.put("password", password);
        map.put("clientid", clientid);
        map.put("login_ip", login_ip);
        map.put("login_platform", login_platform);
        return mAccountApi.login(map);
    }
}
