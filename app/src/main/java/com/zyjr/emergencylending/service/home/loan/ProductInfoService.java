package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.PrecheckResultBean;
import com.zyjr.emergencylending.entity.SupportCityBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 产品 相关
 * Created by neil on 2017/10/25
 */
public interface ProductInfoService {

    /**
     * 获取产品说明
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<PrecheckResultBean.ProIntroduceBean>> getProIntroduce(@FieldMap Map<String, String> params);


    /**
     * 获取支持城市
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<List<SupportCityBean>>> getProSupportCities(@FieldMap Map<String, String> params);


}
