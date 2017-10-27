package com.zyjr.emergencylending.model.home;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.BaseBean;

import rx.Observable;

/**
 * Created by wangyin on 2017/10/24.
 */

public class HomeModel extends BaseModel{


    private static class SingletonHolder {
        private static final HomeModel HOME_MODEL = new HomeModel();
    }

    public static HomeModel getInstance() {
        return SingletonHolder.HOME_MODEL;
    }

    public Observable<BaseBean> getHomeAds(String router) {
        map.clear();
        map.put("router", router);
        return mApi.getHomeAds(map);
    }

}
