package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.model.BaseModel;
import com.zyjr.emergencylending.service.home.loan.PersonalInfoService;

import java.util.Map;

import rx.Observable;

/**
 * Created by neil on 2017/10/23
 * 备注: 个人信息相关
 */
public class PersonalInfoModel extends BaseModel {

    private PersonalInfoService personalInfoService;

    private PersonalInfoModel() {
        super();
        this.personalInfoService = retrofit.create(PersonalInfoService.class);
    }

    private static class SingletonHolder {
        private static final PersonalInfoModel infoModel = new PersonalInfoModel();
    }

    public static PersonalInfoModel getInstance() {
        return SingletonHolder.infoModel;
    }


    public Observable<ApiResult<PersonalInfoBean>> getPersonalInfo(Map<String, String> params) {
        return personalInfoService.getPersonInfo(params);
    }


    public Observable<ApiResult<PersonalInfoBean>> addPersonalInfo(Map<String, String> params) {
        return personalInfoService.addPersonInfo(params);
    }


    public Observable<ApiResult<PersonalInfoBean>> editPersonalInfo(Map<String, String> params) {
        return personalInfoService.editPersonInfo(params);
    }

    public Observable<ApiResult<String>> uploadFile(Map<String, String> params){
        return personalInfoService.uploadFile(params);
    }
}
