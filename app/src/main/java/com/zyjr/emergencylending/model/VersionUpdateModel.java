package com.zyjr.emergencylending.model;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.service.WyApi;

import rx.Observable;

/**
 * Created by wangyin on 2017/10/24.
 */

public class VersionUpdateModel extends BaseModel {
    private WyApi mWyApi;

    private VersionUpdateModel() {
        super();
        mWyApi = retrofit.create(WyApi.class);
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
    public Observable<String> update(String router, String juid, String form_token,
                                     String app_version_no, String app_type, String platform_type) {
        map.clear();
        map.put("router", router);
        map.put("juid", juid);
        map.put("form_token", form_token);
        map.put("app_version_no", app_version_no);
        map.put("app_type", app_type);
        map.put("platform_type", platform_type);
        return mWyApi.versionUpdate(map);
    }

}
