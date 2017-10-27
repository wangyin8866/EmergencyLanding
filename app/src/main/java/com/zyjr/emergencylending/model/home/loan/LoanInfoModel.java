package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.entity.WorkInfoBean;
import com.zyjr.emergencylending.service.home.loan.LoanInfoService;

import java.util.Map;

import rx.Observable;

/**
 * Created by neil on 2017/10/23
 * 备注: 获取用户信息资料
 */
public class LoanInfoModel extends BaseModel {

    private LoanInfoService loanInfoService;

    private LoanInfoModel() {
        super();
        this.loanInfoService = retrofit.create(LoanInfoService.class);
    }

    private static class SingletonHolder {
        private static final LoanInfoModel infoModel = new LoanInfoModel();
    }

    public static LoanInfoModel getInstance() {
        return SingletonHolder.infoModel;
    }

    public Observable<ApiResult<String>> savePersonInfo(Map<String, String> params) {
        return loanInfoService.savePersonInfo(params);
    }

    public Observable<ApiResult<PersonalInfoBean>> getPersonalInfo(Map<String, String> params) {
        return loanInfoService.getPersonInfo(params);
    }

    public Observable<ApiResult<String>> saveWorkInfo(Map<String, String> params) {
        return loanInfoService.saveWorkInfo(params);
    }

    public Observable<ApiResult<WorkInfoBean>> getWorkInfo(Map<String, String> params) {
        return loanInfoService.getWorkInfo(params);
    }

    public Observable<ApiResult<String>> saveContactInfo(Map<String, String> params) {
        return loanInfoService.saveContactInfo(params);
    }

    public Observable<ApiResult<ContactInfoBean>> getContactInfo(Map<String, String> params) {
        return loanInfoService.getContactInfo(params);
    }

    public Observable<ApiResult<String>> saveBankcardInfo(Map<String, String> params) {
        return loanInfoService.saveBankcardInfo(params);
    }

    public Observable<ApiResult<BankcardInfo>> getBankcardInfo(Map<String, String> params) {
        return loanInfoService.getBankcardInfo(params);
    }

}
