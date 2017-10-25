package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.entity.SupportBank;
import com.zyjr.emergencylending.model.BaseModel;
import com.zyjr.emergencylending.service.home.loan.BankcardInfoService;
import com.zyjr.emergencylending.service.home.loan.ContactInfoService;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by neil on 2017/10/23
 * 备注: 银行卡相关
 */
public class BankcardInfoModel extends BaseModel {

    private BankcardInfoService bankcardInfoService;

    private BankcardInfoModel() {
        super();
        this.bankcardInfoService = retrofit.create(BankcardInfoService.class);
    }

    private static class SingletonHolder {
        private static final BankcardInfoModel infoModel = new BankcardInfoModel();
    }

    public static BankcardInfoModel getInstance() {
        return SingletonHolder.infoModel;
    }

    public Observable<ApiResult<List<SupportBank>>> getSupportBankList(Map<String, String> params) {
        return bankcardInfoService.getSupportBankList(params);
    }

    public Observable<ApiResult<BankcardInfo>> getBankcardInfo(Map<String, String> params) {
        return bankcardInfoService.getBankcardInfo(params);
    }


    public Observable<ApiResult<BankcardInfo>> addBankcardInfo(Map<String, String> params) {
        return bankcardInfoService.addBankcardInfo(params);
    }


    public Observable<ApiResult<BankcardInfo>> editBankcardInfo(Map<String, String> params) {
        return bankcardInfoService.editBankcardInfo(params);
    }

}
