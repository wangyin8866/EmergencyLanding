package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.LoanOrderBean;
import com.zyjr.emergencylending.service.Api;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 借款 订单
 * @author neil
 *         Created on 2017/10/24
 */
public interface LoanOrderService {

    /**
     * 获取当前借款订单信息
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<LoanOrderBean>> getCurrentOrderDetail(@FieldMap Map<String, String> params);

    /**
     * 获取当前有效借款订单
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<String>> getEffectiveLoanOrder(@FieldMap Map<String, String> params);

    /**
     * 做废件处理
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<String>> deleteLoanOrder(@FieldMap Map<String, String> params);


}
