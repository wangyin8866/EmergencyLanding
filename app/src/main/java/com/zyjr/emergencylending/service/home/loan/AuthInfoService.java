package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.entity.AuthResult;
import com.zyjr.emergencylending.entity.MobileBean;
import com.zyjr.emergencylending.entity.ZhimaAuthBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 认证信息
 * Created by neil on 2017/10/25
 */
public interface AuthInfoService {

    /**
     * 获取芝麻认证信息
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<String>> getZhimaScore(@FieldMap Map<String,String> params);

    /**
     * 获取芝麻认证信息
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<ZhimaAuthBean>> getZhimaAuthUrl(@FieldMap Map<String,String> params);

    /**
     * 获取当前借款信息的有效认证状态集合
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<AuthResult>> getCurrentAuthInfo(@FieldMap Map<String,String> params);

    /**
     * 运营商认证采集
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<AuthInfoBean>> submitMobileAuthInfo(@FieldMap Map<String,String> params);

    /**
     * 人脸识别通过后提交
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<String>> submitFaceAuthInfo(@FieldMap Map<String,String> params);

    /**
     * 运营商验证码是否有效
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<MobileBean>> judgeMobileCodeValide(@FieldMap Map<String,String> params);

}
