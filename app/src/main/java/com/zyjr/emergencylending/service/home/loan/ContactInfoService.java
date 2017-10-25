package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.entity.ContactInfoBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by neil on 2017/10/23
 * 备注: 联系人相关
 */

public interface ContactInfoService {

    /**
     * 获取联系人资料
     */
    @FormUrlEncoded
    @POST("/115")
    Observable<ApiResult<ContactInfoBean>> getContactInfo(@FieldMap Map<String, String> params);

    /**
     * 新增联系人资料
     */
    @FormUrlEncoded
    @POST("/116")
    Observable<ApiResult<ContactInfoBean>> addContactInfo(@FieldMap Map<String, String> params);

    /**
     * 编辑联系人资料
     */
    @FormUrlEncoded
    @POST("/116")
    Observable<ApiResult<ContactInfoBean>> editContactInfo(@FieldMap Map<String, String> params);

    /**
     * 上传通讯录信息
     */
    Observable<ApiResult<String>> submitContacts(@FieldMap Map<String, String> params);

}
