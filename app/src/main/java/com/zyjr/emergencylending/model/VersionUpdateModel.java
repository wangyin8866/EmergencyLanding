package com.zyjr.emergencylending.model;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.VersionBean;
import com.zyjr.emergencylending.service.Api;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * @author wangyin
 * @date 2017/10/24
 */

public class VersionUpdateModel extends BaseModel {
    private Api mApi;

    private VersionUpdateModel() {
        super();
        mApi = retrofit.create(Api.class);
    }

    public static class SingletonHolder {
        private static final VersionUpdateModel VERSION_UPDATE_MODEL = new VersionUpdateModel();
    }

    public static VersionUpdateModel getInstance() {
        return SingletonHolder.VERSION_UPDATE_MODEL;
    }


    /**
     * 版本升级
     */
    public Observable<VersionBean> update(String router) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        return mApi.versionUpdate(map);
    }

}
