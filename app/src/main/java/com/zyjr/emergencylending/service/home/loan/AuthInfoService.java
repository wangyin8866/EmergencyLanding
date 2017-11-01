package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.AuthInfoBean;

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
     * 获取当前借款信息的有效认证状态集合
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<List<AuthInfoBean>>> getCurrentAuthInfo(@FieldMap Map<String,String> params);

    /**
     * 提交认证信息
     */
    @FormUrlEncoded
    @POST("115")
    Observable<ApiResult<AuthInfoBean>> submitAuthInfo(@FieldMap Map<String,String> params);

}
