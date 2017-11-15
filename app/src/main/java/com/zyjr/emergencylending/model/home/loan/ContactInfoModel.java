package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.service.home.loan.ContactInfoService;
import com.zyjr.emergencylending.utils.SPUtils;

import java.util.List;
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


    public Observable<ApiResult<List<ContactInfoBean>>> getContactInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_GET_CONTACT_INFO);
        params.put("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_CUST_JUID, SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, "1")));
        return contactInfoService.getContactInfo(params);
    }


    public Observable<ApiResult<List<ContactInfoBean>>> addContactInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_ADD_CONTACT_INFO);
        params.put("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_CUST_JUID, SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, "1")));
        return contactInfoService.addContactInfo(params);
    }


    public Observable<ApiResult<List<ContactInfoBean>>> editContactInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_EDIT_CONTACT_INFO);
        params.put("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_CUST_JUID, SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, "1")));
        return contactInfoService.editContactInfo(params);
    }

}
