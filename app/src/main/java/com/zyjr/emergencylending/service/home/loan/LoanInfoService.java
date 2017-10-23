package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.entity.WorkInfoBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by neil on 2017/10/23
 * 备注: 资料信息 服务
 */
public interface LoanInfoService {

    /**
     * 获取个人信息
     */
    @FormUrlEncoded
    @POST("/111")
    Observable<ApiResult<PersonalInfoBean>> getPersonInfo(@FieldMap Map<String, String> params);

    /**
     * 保存个人信息
     */
    @FormUrlEncoded
    @POST("/112")
    Observable<ApiResult<String>> savePersonInfo(@FieldMap Map<String, String> params);

    /**
     * 获取工作信息
     */
    @FormUrlEncoded
    @POST("/113")
    Observable<ApiResult<WorkInfoBean>> getWorkInfo(@FieldMap Map<String, String> params);

    /**
     * 保存工作信息
     */
    @FormUrlEncoded
    @POST("/114")
    Observable<ApiResult<String>> saveWorkInfo(@FieldMap Map<String, String> params);

    /**
     * 获取联系人资料
     */
    @FormUrlEncoded
    @POST("/115")
    Observable<ApiResult<ContactInfoBean>> getContactInfo(@FieldMap Map<String, String> params);

    /**
     * 保存联系人资料
     */
    @FormUrlEncoded
    @POST("/116")
    Observable<ApiResult<String>> saveContactInfo(@FieldMap Map<String, String> params);


    /**
     * 获取银行卡信息
     */
    @FormUrlEncoded
    @POST("/117")
    Observable<ApiResult<BankcardInfo>> getBankcardInfo(@FieldMap Map<String, String> params);

    /**
     * 保存银行卡信息
     */
    @FormUrlEncoded
    @POST("/118")
    Observable<ApiResult<String>> saveBankcardInfo(@FieldMap Map<String, String> params);

}


