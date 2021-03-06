package com.zyjr.emergencylending.model.home;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.QrBean;
import com.zyjr.emergencylending.service.Api;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 *
 * @author wangyin
 * @date 2017/10/24
 */

public class QrModel extends BaseModel{
    private Api api;

    private QrModel() {
        super();
        api = retrofit.create(Api.class);
    }
    private static class SingletonHolder {
        private static final QrModel HOME_MODEL = new QrModel();
    }
    public static QrModel getInstance() {
        return SingletonHolder.HOME_MODEL;
    }


    public Observable<QrBean> getQr(String router) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        return api.getQr(map);
    }
}
