package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.config.NetConstantValues;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 问题验证
 *
 * @author neil
 * @date 2017/11/6
 */
public interface QuestValidateService {

    /**
     * 保存通讯录
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<String>> saveContactsList(@FieldMap Map<String, String> map);

}
