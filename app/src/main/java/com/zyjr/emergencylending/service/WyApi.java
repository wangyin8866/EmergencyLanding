package com.zyjr.emergencylending.service;

import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.account.LoginBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wangyin on 2017/10/24.
 */

public interface WyApi {
    /**
     * 注册
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<Object> register(@FieldMap Map<String, String> params);

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
}
