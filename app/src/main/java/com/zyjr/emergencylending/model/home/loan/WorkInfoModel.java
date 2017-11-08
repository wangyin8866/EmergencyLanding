package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.WorkInfoBean;
import com.zyjr.emergencylending.service.home.loan.WorktInfoService;
import com.zyjr.emergencylending.utils.SPUtils;

import java.util.Map;

import rx.Observable;

/**
 * Created by neil on 2017/10/23
 * 备注: 工作信息相关
 */
public class WorkInfoModel extends BaseModel {

    private WorktInfoService worktInfoService;

    private WorkInfoModel() {
        super();
        this.worktInfoService = retrofit.create(WorktInfoService.class);
    }

    private static class SingletonHolder {
        private static final WorkInfoModel infoModel = new WorkInfoModel();
    }

    public static WorkInfoModel getInstance() {
        return SingletonHolder.infoModel;
    }


    public Observable<ApiResult<WorkInfoBean>> getWorkInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_GET_WORK_INFO);
        params.put("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_CUST_JUID, "1"));
        return worktInfoService.getWorkInfo(params);
    }


    public Observable<ApiResult<WorkInfoBean>> addWorkInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_ADD_WORK_INFO);
        params.put("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_CUST_JUID, "1"));
        return worktInfoService.addWorkInfo(params);
    }


    public Observable<ApiResult<WorkInfoBean>> editWorkInfo(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_EDIT_WORK_INFO);
        return worktInfoService.editWorkInfo(params);
    }

}
