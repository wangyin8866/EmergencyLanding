package com.zyjr.emergencylending.ui.salesman.model;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.H5Bean;
import com.zyjr.emergencylending.service.Api;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 *
 * @author wangyin
 * @date 2017/10/24
 */

public class MineModel extends BaseModel{
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


    public Observable<BaseBean> myIncome(String router) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        return api.myIncome(map);
    }
    public Observable<H5Bean> helpPage(String router) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        return api.helpPage(map);
    }
}
