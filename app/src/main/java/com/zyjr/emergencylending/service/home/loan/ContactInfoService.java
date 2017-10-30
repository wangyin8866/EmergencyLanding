package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.ContactInfoBean;

import java.util.List;
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
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<List<ContactInfoBean>>> getContactInfo(@FieldMap Map<String, String> params);

    /**
     * 新增联系人资料
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<List<ContactInfoBean>>> addContactInfo(@FieldMap Map<String, String> params);

    /**
     * 编辑联系人资料
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<ApiResult<List<ContactInfoBean>>> editContactInfo(@FieldMap Map<String, String> params);

}
