package com.zyjr.emergencylending.service;

import com.zyjr.emergencylending.config.NetConstantValues;
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

public interface AccountApi {
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<RegisterBean> register(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<LoginBean> login(@FieldMap Map<String, String> params);
}
