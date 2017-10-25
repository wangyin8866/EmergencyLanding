package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.entity.ReceiveMoneyBean;
import com.zyjr.emergencylending.model.BaseModel;
import com.zyjr.emergencylending.service.home.loan.ReceiveMoneyService;

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
        return receiveMoneyService.getReceiveMoneyInfo(params);
    }


}
