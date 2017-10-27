package com.zyjr.emergencylending.service;

import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.account.LoginBean;
import com.zyjr.emergencylending.entity.account.RegisterBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wangyin on 2017/10/24.
 */

public interface Api {
    /**
     * 注册
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<RegisterBean> register(@FieldMap Map<String, String> params);

    /**
     * 登录
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<LoginBean> login(@FieldMap Map<String, String> params);

    /**
     * 版本更新
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<String> versionUpdate(@FieldMap Map<String, String> params);

    /**
     * 发送验证码
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<BaseBean> sendSMS(@FieldMap Map<String, String> params);
    /**
     * 忘记密码
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<BaseBean> forgetPassword(@FieldMap Map<String, String> params);

    /**
     * 首页广告
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<BaseBean> getHomeAds(@FieldMap Map<String, String> params);
}
