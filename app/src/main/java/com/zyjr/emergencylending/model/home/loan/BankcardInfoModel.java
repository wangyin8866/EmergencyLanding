package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.base.CustomApiResult;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.SupportBank;
import com.zyjr.emergencylending.service.home.loan.BankcardInfoService;
import com.zyjr.emergencylending.utils.SPUtils;

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
        bankcardInfoService = retrofit.create(BankcardInfoService.class);
    }

    private static class SingletonHolder {
        private static final BankcardInfoModel infoModel = new BankcardInfoModel();
    }

    public static BankcardInfoModel getInstance() {
        return SingletonHolder.infoModel;
    }

    public Observable<ApiResult<List<SupportBank>>> getSupportBankList(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_GET_SUPPORT_BANK_LIST);
        return bankcardInfoService.getSupportBankList(params);
    }

    public Observable<CustomApiResult<BankcardInfo, BankcardInfo>> getBankcardInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_GET_BIND_BANK_CARD);
        params.put("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_CUST_JUID, SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, "1")));
        return bankcardInfoService.getBankcardInfo(params);
    }


    public Observable<ApiResult<BankcardInfo>> addBankcardInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_ADD_BIND_BANK_CARD);
        params.put("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_CUST_JUID, SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, "1")));
        return bankcardInfoService.addBankcardInfo(params);
    }


    public Observable<ApiResult<BankcardInfo>> editBankcardInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_EDIT_BIND_BANK_CARD);
        params.put("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_CUST_JUID, SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, "1")));
        return bankcardInfoService.editBankcardInfo(params);
    }

}
