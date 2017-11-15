package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.MayApplyProBean;
import com.zyjr.emergencylending.entity.StoreResultBean;
import com.zyjr.emergencylending.entity.WriteInfoBean;
import com.zyjr.emergencylending.service.home.loan.WriteInfoService;
import com.zyjr.emergencylending.utils.SPUtils;

import java.util.Map;

import rx.Observable;

/**
 * Created by neil on 2017/10/23
 * 备注: 用户借款 资料
 */
public class WriteInfoModel extends BaseModel {
    private WriteInfoService writeInfoService;

    private WriteInfoModel() {
        super();
        writeInfoService = retrofit.create(WriteInfoService.class);
    }

    private static class SingletonHolder {
        private static final WriteInfoModel writeInfoModel = new WriteInfoModel();
    }

    public static WriteInfoModel getInstance() {
        return SingletonHolder.writeInfoModel;
    }

    public Observable<ApiResult<WriteInfoBean>> getWriteInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_GET_WRITE_INFO);
        params.put("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_CUST_JUID, SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, "1")));
        return writeInfoService.getWriteInfo(params);
    }

    public Observable<ApiResult<String>> submitLoanInformation(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_SUBMIT_LOAN_INFORMATION);
        params.put("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_CUST_JUID, SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, "1")));
        return writeInfoService.submitLoanInformation(params);
    }

    public Observable<ApiResult<MayApplyProBean>> getMayApplayProductType(Map<String, String> params){
        params.put("router", NetConstantValues.ROUTER_GET_MAYAPPLY_PRODUCT_TYPE);
        return writeInfoService.getMayApplyProductType(params);
    }

    public Observable<ApiResult<StoreResultBean>> getLocalStoreList(Map<String, String> params){
        params.put("router", NetConstantValues.ROUTER_GET_LOCAL_STORE_LIST);
        params.put("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_CUST_JUID, SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, "1")));
        return writeInfoService.getLocalStoreList(params);
    }

}
