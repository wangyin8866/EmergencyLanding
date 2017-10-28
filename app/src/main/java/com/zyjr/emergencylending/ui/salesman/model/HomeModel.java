package com.zyjr.emergencylending.ui.salesman.model;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.service.Api;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 *
 * @author wangyin
 * @date 2017/10/24
 */

public class HomeModel extends BaseModel{
    private Api api;

    private HomeModel() {
        super();
        api = retrofit.create(Api.class);
    }
    private static class SingletonHolder {
        private static final HomeModel HOME_MODEL = new HomeModel();
    }
    public static HomeModel getInstance() {
        return SingletonHolder.HOME_MODEL;
    }


    public Observable<BaseBean> getNoticeList(String router,String get_num) {
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("router", router);
        map.put("get_num", get_num);
        return api.getNoticeList(map);
    }
}
