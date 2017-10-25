package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.entity.ReceiveMoneyBean;

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
    @POST("/111")
    Observable<ApiResult<ReceiveMoneyBean>> getReceiveMoneyInfo(@FieldMap Map<String, String> params);

}
