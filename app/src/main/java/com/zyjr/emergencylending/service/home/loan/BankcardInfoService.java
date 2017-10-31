package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.CustomApiResult;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.SupportBank;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by neil on 2017/10/23
 * 备注: 银行|银行卡相关
 */
public interface BankcardInfoService {

    /**
     * 获取支持银行列表
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<List<SupportBank>>> getSupportBankList(@FieldMap Map<String, String> params);

    /**
     * 获取银行卡信息
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<CustomApiResult<BankcardInfo,BankcardInfo>> getBankcardInfo(@FieldMap Map<String, String> params);

    /**
     * 保存银行卡信息
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<BankcardInfo>> addBankcardInfo(@FieldMap Map<String, String> params);


    /**
     * 修改银行卡信息
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<BankcardInfo>> editBankcardInfo(@FieldMap Map<String, String> params);


    /**
     * 删除银行卡信息
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<BankcardInfo>> deteteBankcardInfo(@FieldMap Map<String, String> params);
}
