package com.zyjr.emergencylending.model.home;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.Banner;
import com.zyjr.emergencylending.service.Api;

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

    public Observable<Banner> getHomeAds(String router) {
        map.clear();
        map.put("router", router);
        return api.getHomeAds(map);
    }

}
