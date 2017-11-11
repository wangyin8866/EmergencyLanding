package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.LoanOrderBean;
import com.zyjr.emergencylending.service.home.loan.LoanOrderService;

import java.util.Map;

import rx.Observable;

/**
 * 借款订单
 * Created by neil on 2017/10/24
 */
public class LoanOrderModel extends BaseModel {

    private LoanOrderService loanOrderService;

    private LoanOrderModel() {
        super();
        loanOrderService = retrofit.create(LoanOrderService.class);
    }

    private static class SingletonHolder {
        private static final LoanOrderModel loanOrderModel = new LoanOrderModel();
    }

    public static LoanOrderModel getInstance() {
        return SingletonHolder.loanOrderModel;
    }

    public Observable<ApiResult<LoanOrderBean>> getCurrentOrderDetail(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_GET_CURRENT_ORDER_INFO);
        return loanOrderService.getCurrentOrderDetail(params);
    }

    public Observable<ApiResult<String>> getEffectiveLoanOrder(Map<String, String> params){
        params.put("router", NetConstantValues.ROUTER_GET_CURRENT_EFFECTIVE_LOAN_ORDER);
        return loanOrderService.getEffectiveLoanOrder(params);
    }

    public Observable<ApiResult<String>> deleteLoanOrder(Map<String, String> params){
        params.put("router", NetConstantValues.ROUTER_DELETE_LOAN_ORDER);
        return loanOrderService.deleteLoanOrder(params);
    }

}
