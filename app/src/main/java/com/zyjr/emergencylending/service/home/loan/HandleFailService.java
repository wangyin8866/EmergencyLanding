package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.config.NetConstantValues;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author neil
 * @date 2017/11/13
 */
public interface HandleFailService {

    /**
     * 做废件处理
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<String>> deleteLoanOrder(@FieldMap Map<String, String> params);



}
