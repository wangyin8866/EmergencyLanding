package com.zyjr.emergencylending.ui.salesman.model;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.CardBean;
import com.zyjr.emergencylending.service.Api;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * @author wangyin
 * @date 2017/10/24
 */

public class MineModel extends BaseModel {
    private Api api;

    private MineModel() {
        super();
        api = retrofit.create(Api.class);
    }

    private static class SingletonHolder {
        private static final MineModel HOME_MODEL = new MineModel();
    }

    public static MineModel getInstance() {
        return SingletonHolder.HOME_MODEL;
    }


    public Observable<CardBean> myCard(String router) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        return api.myIncome(map);
    }

    public Observable<BaseBean> updatePic(String router, String fileName, String fileExtName, String fileContext, String fileType) {
        Map<String, String> map = new HashMap<String, String>(5);
        map.put("router", router);
        map.put("fileName", fileName);
        map.put("fileExtName", fileExtName);
        map.put("fileContext", fileContext);
        map.put("fileType", fileType);
        return api.uploadFile(map);
    }
}
