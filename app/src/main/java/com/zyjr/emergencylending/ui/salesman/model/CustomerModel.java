package com.zyjr.emergencylending.ui.salesman.model;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.CustomerBean;
import com.zyjr.emergencylending.entity.WaitApplyBean;
import com.zyjr.emergencylending.service.Api;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 *
 * @author wangyin
 * @date 2017/10/24
 */

public class CustomerModel extends BaseModel{
    private Api api;

    private CustomerModel() {
        super();
        api = retrofit.create(Api.class);
    }
    private static class SingletonHolder {
        private static final CustomerModel HOME_MODEL = new CustomerModel();
    }
    public static CustomerModel getInstance() {
        return SingletonHolder.HOME_MODEL;
    }


    public Observable<CustomerBean> myPerformance(String router, String type) {
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("router", router);
        map.put("type", type);
        return api.myPerformance(map);
    }
    public Observable<BaseBean> rankList(String router) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        return api.rankList(map);
    }
    public Observable<WaitApplyBean> waitApply(String router, String type) {
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("router", router);
        map.put("type", type);
        return api.waitApply(map);
    }
}
