package com.zyjr.emergencylending.model.account;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.EffectiveOrderBean;
import com.zyjr.emergencylending.entity.H5Bean;
import com.zyjr.emergencylending.entity.MyBorrow;
import com.zyjr.emergencylending.entity.UserInfo;
import com.zyjr.emergencylending.entity.UserStatus;
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
                                             String register_ip, String register_device_no,String phone_equipment) {
        Map<String, String> map = new HashMap<String, String>(8);
        map.put("router", router);
        map.put("phone", phone);
        map.put("clientid", clientid);
        map.put("verify_code", verify_code);
        map.put("password", password);
        map.put("recommend_code", recommend_code);
        map.put("register_ip", register_ip);
        map.put("register_device_no", register_device_no);
        map.put("phone_equipment", phone_equipment);
        return mApi.register(map);
    }

    /**
     * 登录
     */
    public Observable<LoginBean> login(String router, String phone, String password,
                                       String clientid, String login_ip, String login_platform, String login_device_no,String phone_equipment) {
        Map<String, String> map = new HashMap<String, String>(6);
        map.put("router", router);
        map.put("phone", phone);
        map.put("password", password);
        map.put("clientid", clientid);
        map.put("login_ip", login_ip);
        map.put("login_platform", login_platform);
        map.put("login_device_no", login_device_no);
        map.put("phone_equipment", phone_equipment);
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
                                               String verify_code, String password, String type) {
        Map<String, String> map = new HashMap<String, String>(4);
        map.put("router", router);
        map.put("phone", phone);
        map.put("verify_code", verify_code);
        map.put("password", password);
        map.put("type", type);
        return mApi.sendSMS(map);
    }

    /**
     * h5
     */
    public Observable<H5Bean> getH5Url(String url_type) {
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("router", NetConstantValues.H5_URL);
        map.put("url_type", url_type);
        return mApi.getH5Url(map);
    }

    /**
     * 获取用户状态
     */
    public Observable<UserStatus> getUserInfoStatus(String router) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        return mApi.getUserInfoStatus(map);
    }

    /**
     * 获取用户资料
     */
    public Observable<UserInfo> getBasicInfo(String router) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        return mApi.getBasicInfo(map);
    }

    /**
     * 获取用户资料
     */
    public Observable<EffectiveOrderBean> isEffectiveOrder(String router) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        return mApi.isEffectiveOrder(map);
    }

    /**
     * 我的借款
     */
    public Observable<MyBorrow> myBorrow(String router, String pageNo, String pageSize) {
        Map<String, String> map = new HashMap<String, String>(3);
        map.put("router", router);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        return mApi.myBorrow(map);
    }

}
