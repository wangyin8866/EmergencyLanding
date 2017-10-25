package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.entity.PersonalInfoBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by neil on 2017/10/23
 * 备注: 个人资料
 */

public interface PersonalInfoService {

    /**
     * 获取个人信息
     */
    @FormUrlEncoded
    @POST("/111")
    Observable<ApiResult<PersonalInfoBean>> getPersonInfo(@FieldMap Map<String, String> params);

    /**
     * 添加个人信息
     */
    @FormUrlEncoded
    @POST("/112")
    Observable<ApiResult<PersonalInfoBean>> addPersonInfo(@FieldMap Map<String, String> params);


    /**
     * 编辑个人信息
     */
    @FormUrlEncoded
    @POST("/112")
    Observable<ApiResult<PersonalInfoBean>> editPersonInfo(@FieldMap Map<String, String> params);

    /**
     * 上传照片
     */
    @FormUrlEncoded
    @POST("/112")
    Observable<ApiResult<String>> uploadFile(@FieldMap Map<String, String> params);

}
