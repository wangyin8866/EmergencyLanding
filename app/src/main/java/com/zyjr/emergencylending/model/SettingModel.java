package com.zyjr.emergencylending.model;

import com.zyjr.emergencylending.base.BaseModel;
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

public class SettingModel extends BaseModel{
    private Api api;

    private SettingModel() {
        super();
        api = retrofit.create(Api.class);
    }
    private static class SingletonHolder {
        private static final SettingModel HOME_MODEL = new SettingModel();
    }
    public static SettingModel getInstance() {
        return SingletonHolder.HOME_MODEL;
    }


    public Observable<H5Bean> aboutUs(String router) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        return api.aboutUs(map);
    }
}
