package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.ReceiveMoneyBean;
import com.zyjr.emergencylending.entity.RemindBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 领取 金额
 * Created by neil on 2017/10/24
 */
public interface ReceiveMoneyService {

    /**
     * 获取领取金额信息
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<ReceiveMoneyBean>> getReceiveMoneyInfo(@FieldMap Map<String, String> params);


    /**
     * 确认领取信息(根据条件会给出温馨提示)
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<RemindBean>> confirmReceiveInfo(@FieldMap Map<String, String> params);
}
