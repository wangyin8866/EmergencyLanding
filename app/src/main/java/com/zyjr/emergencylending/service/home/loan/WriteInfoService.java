package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.entity.WriteInfoBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by neil on 2017/10/23
 * 备注: 填写资料信息
 */
public interface WriteInfoService {

    /**
     * 获取用户资料完成信息
     */
    @FormUrlEncoded
    @POST("1564")
    Observable<ApiResult<WriteInfoBean>> getWriteInfo(@FieldMap Map<String,String> map);


    /**
     * 提交填写资料
     */
    @FormUrlEncoded
    @POST("")
    Observable<ApiResult<String>> submitWriteInfo(@FieldMap Map<String,String> map);
}


