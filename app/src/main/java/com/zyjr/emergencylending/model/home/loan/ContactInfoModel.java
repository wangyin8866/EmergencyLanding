package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.model.BaseModel;
import com.zyjr.emergencylending.service.home.loan.ContactInfoService;

import java.util.Map;

import rx.Observable;

/**
 * Created by neil on 2017/10/23
 * 备注: 联系人相关
 */
public class ContactInfoModel extends BaseModel {

    private ContactInfoService contactInfoService;

    private ContactInfoModel() {
        super();
        this.contactInfoService = retrofit.create(ContactInfoService.class);
    }

    private static class SingletonHolder {
        private static final ContactInfoModel infoModel = new ContactInfoModel();
    }

    public static ContactInfoModel getInstance() {
        return SingletonHolder.infoModel;
    }


    public Observable<ApiResult<ContactInfoBean>> getContactInfo(Map<String, String> params) {
        return contactInfoService.getContactInfo(params);
    }


    public Observable<ApiResult<ContactInfoBean>> addContactInfo(Map<String, String> params) {
        return contactInfoService.addContactInfo(params);
    }


    public Observable<ApiResult<ContactInfoBean>> editContactInfo(Map<String, String> params) {
        return contactInfoService.editContactInfo(params);
    }

    public Observable<ApiResult<String>> submitContacts(Map<String, String> params){
        return contactInfoService.submitContacts(params);
    }

}
