package com.zyjr.emergencylending.model;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.RepaymentSuccess;
import com.zyjr.emergencylending.service.Api;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @author wangyin
 * @date 2017/11/9.
 * @description :
 */

public class RepaymentModel extends BaseModel {
    private Api api;

    public RepaymentModel(String url) {
        super(url);
        api = retrofit.create(Api.class);
    }


    private static class SingletonHolder {
        private static final RepaymentModel HOME_MODEL = new RepaymentModel(NetConstantValues.REPAYMENT_LOGIN_BASE);
    }

    public static RepaymentModel getInstance() {
        return SingletonHolder.HOME_MODEL;
    }


    /**
     * 我的借款登陆
     */
    @Deprecated
    public Observable<RepaymentSuccess> repaymentLogin(RequestBody body) {
        return api.repaymentLogin(body);
    }
}
