package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.entity.AuthResult;
import com.zyjr.emergencylending.service.home.loan.AuthInfoService;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * 认证中心
 * Created by neil on 2017/10/25
 */
public class AuthCenterModel extends BaseModel {

    private AuthInfoService authInfoService;

    private AuthCenterModel() {
        super();
        authInfoService = retrofit.create(AuthInfoService.class);
    }

    private static class SingletonHolder {
        private static final AuthCenterModel authCenterModel = new AuthCenterModel();
    }

    public static AuthCenterModel getInstance() {
        return SingletonHolder.authCenterModel;
    }

    public Observable<ApiResult<AuthResult>> getCurrentAuthInfo(Map<String,String> params){
        params.put("router", NetConstantValues.ROUTER_GET_AUTH_STATUS);
        return authInfoService.getCurrentAuthInfo(params);
    }


}
