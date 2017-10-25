package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.entity.WorkInfoBean;
import com.zyjr.emergencylending.entity.WriteInfoBean;
import com.zyjr.emergencylending.model.BaseModel;
import com.zyjr.emergencylending.service.home.loan.WriteInfoService;

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

    private static class SingletonHolder{
        private static final WriteInfoModel writeInfoModel = new WriteInfoModel();
    }

    public static WriteInfoModel getInstance(){
        return SingletonHolder.writeInfoModel;
    }

    public Observable<ApiResult<WriteInfoBean>> getWriteInfo(Map<String, String> params) {
        return writeInfoService.getWriteInfo(params);
    }

}
