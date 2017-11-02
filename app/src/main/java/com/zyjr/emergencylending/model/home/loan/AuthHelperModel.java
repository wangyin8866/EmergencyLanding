package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.entity.ZhimaAuthBean;
import com.zyjr.emergencylending.service.home.loan.AuthInfoService;

import java.util.Map;

import rx.Observable;

/**
 * 认证帮助类
 * Created by neil on 2017/10/25
 */
public class AuthHelperModel extends BaseModel {

    private AuthInfoService authInfoService;

    private AuthHelperModel() {
        super();
        authInfoService = retrofit.create(AuthInfoService.class);
    }

    private static class SingletonHolder {
        private static final AuthHelperModel authHelperModel = new AuthHelperModel();
    }

    public static AuthHelperModel getInstance() {
        return SingletonHolder.authHelperModel;
    }

    public Observable<ApiResult<AuthInfoBean>> submitAuthInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_SUBMIT_AUTH_INFO);
        return authInfoService.submitAuthInfo(params);
    }

    public Observable<ApiResult<ZhimaAuthBean>> getZhimaAuthUrl(Map<String,String> params){
        params.put("router", NetConstantValues.ROUTER_GET_ZHIMA_AUTH_URL);
        return authInfoService.getZhimaAuthUrl(params);
    }

    public  Observable<ApiResult<String>> getZhimaScore(Map<String,String> params){
        params.put("router", NetConstantValues.ROUTER_GET_ZHIMA_SCORE);
        return authInfoService.getZhimaScore(params);
    }

}
