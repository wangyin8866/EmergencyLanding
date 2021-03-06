package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.ReceiveMoneyBean;
import com.zyjr.emergencylending.entity.RemindBean;
import com.zyjr.emergencylending.service.home.loan.ReceiveMoneyService;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * 领取金额
 * Created by neil on 2017/10/24
 */
public class ReceiveMoneyModel extends BaseModel {

    private ReceiveMoneyService receiveMoneyService;

    private ReceiveMoneyModel() {
        super();
        receiveMoneyService = retrofit.create(ReceiveMoneyService.class);
    }

    private static class SingletonHolder {
        private static final ReceiveMoneyModel model = new ReceiveMoneyModel();
    }

    public static ReceiveMoneyModel getInstance() {
        return SingletonHolder.model;
    }

    public Observable<ApiResult<ReceiveMoneyBean>> getReceiveMoneyInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.RONTER_GET_RECEIVE_MONEY);
        return receiveMoneyService.getReceiveMoneyInfo(params);
    }

    public Observable<ApiResult<RemindBean>> confirmReceiveInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_CONFIRM_RECEIVE_INFO);
        return receiveMoneyService.confirmReceiveInfo(params);
    }


}
