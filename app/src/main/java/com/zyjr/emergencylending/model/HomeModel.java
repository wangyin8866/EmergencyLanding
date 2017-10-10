package com.zyjr.emergencylending.model;


import com.zyjr.emergencylending.service.HomeAPi;

/**
 * Created by wangyin on 2017/6/29.
 */

public class HomeModel extends BaseModel {
    private HomeAPi homeAPi;

    private HomeModel() {
        super();
        homeAPi = retrofit.create(HomeAPi.class);
    }

    private static class SingletonHolder {
        private static final HomeModel homeModel = new HomeModel();
    }

    public static HomeModel getInstance() {
        return SingletonHolder.homeModel;
    }

   /* public Observable<Banner> getBanner(String type) {
        map.clear();
        map.put("type", type);
        map.put("platform", platform);
        map.put("client", client);
        return homeAPi.getBanner(map);
    }
  */

}
