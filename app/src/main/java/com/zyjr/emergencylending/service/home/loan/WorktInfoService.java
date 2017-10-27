package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.entity.WorkInfoBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by neil on 2017/10/23
 * 备注: 工作信息相关
 */
public interface WorktInfoService {

    /**
     * 获取工作信息
     */
    @FormUrlEncoded
    @POST("/zyUserJobService.getUserJobInfo")
    Observable<ApiResult<WorkInfoBean>> getWorkInfo(@FieldMap Map<String, String> params);

    /**
     * 保存工作信息
     */
    @FormUrlEncoded
    @POST("/ZyUserJobService.saveUserJobInfo")
    Observable<ApiResult<WorkInfoBean>> addWorkInfo(@FieldMap Map<String, String> params);

    /**
     * 编辑工作信息
     */
    @FormUrlEncoded
    @POST("/ZyUserJobService.saveUserJobInfo")
    Observable<ApiResult<WorkInfoBean>> editWorkInfo(@FieldMap Map<String, String> params);


}
