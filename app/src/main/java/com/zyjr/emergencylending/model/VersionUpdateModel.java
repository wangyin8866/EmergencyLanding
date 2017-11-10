package com.zyjr.emergencylending.model;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.VersionBean;
import com.zyjr.emergencylending.service.Api;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 *
 * @author wangyin
 * @date 2017/10/24
 */

public class VersionUpdateModel extends BaseModel {
    private Api mApi;
    private static String mUString;

    private VersionUpdateModel(String url) {
        super(url);
        mApi = retrofit.create(Api.class);
    }

    public static class SingletonHolder {
        private static final VersionUpdateModel VERSION_UPDATE_MODEL = new VersionUpdateModel(mUString);
    }

    public static VersionUpdateModel getInstance(String url) {
        mUString = url;
        return SingletonHolder.VERSION_UPDATE_MODEL;
    }









    /**
     * 版本升级
     */
    public Observable<VersionBean> update(String router, String juid, String login_token,
                                          String app_version_no) {
        Map<String, String> map = new HashMap<String, String>(6);
        map.put("router", router);
        map.put("juid", juid);
        map.put("login_token", login_token);
        map.put("app_version_no", app_version_no);
        map.put("app_type", "1");
        map.put("platform_type", "1");
        return mApi.versionUpdate(map);
    }

}
