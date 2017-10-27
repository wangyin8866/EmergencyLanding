package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.WorkInfoBean;
import com.zyjr.emergencylending.service.home.loan.WorktInfoService;

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
        return worktInfoService.getWorkInfo(params);
    }


    public Observable<ApiResult<WorkInfoBean>> addWorkInfo(Map<String, String> params) {
        return worktInfoService.addWorkInfo(params);
    }


    public Observable<ApiResult<WorkInfoBean>> editWorkInfo(Map<String, String> params) {
        return worktInfoService.editWorkInfo(params);
    }

}
