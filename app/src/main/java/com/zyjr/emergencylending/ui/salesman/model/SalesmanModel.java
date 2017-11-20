package com.zyjr.emergencylending.ui.salesman.model;

import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.CardBean;
import com.zyjr.emergencylending.entity.CustomerBean;
import com.zyjr.emergencylending.entity.ImmediateBean;
import com.zyjr.emergencylending.entity.NoticeBean;
import com.zyjr.emergencylending.entity.RankBean;
import com.zyjr.emergencylending.entity.WaitApplyBean;
import com.zyjr.emergencylending.service.Api;
import com.zyjr.emergencylending.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * @author wangyin
 * @date 2017/10/24
 */

public class SalesmanModel extends BaseModel {
    private Api api;

    private SalesmanModel() {
        super();
        api = retrofit.create(Api.class);
    }

    private static class SingletonHolder {
        private static final SalesmanModel HOME_MODEL = new SalesmanModel();
    }

    public static SalesmanModel getInstance() {
        return SingletonHolder.HOME_MODEL;
    }


    public Observable<CustomerBean> myPerformance(String router, String type) {
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("router", router);
        map.put("type", type);

        return api.myPerformance(map);
    }

    public Observable<RankBean> rankList(String router) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        return api.rankList(map);
    }

    public Observable<WaitApplyBean> waitApply(String router, String type, String isHome,String currentPage,String pageSize) {
        Map<String, String> map = new HashMap<String, String>(3);
        map.put("router", router);
        map.put("type", type);
        map.put("isHome", isHome);
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        return api.waitApply(map);
    }

    public Observable<NoticeBean> getNoticeList(String router, String get_num) {
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("router", router);
        map.put("get_num", get_num);
        return api.getNoticeList(map);
    }

    public Observable<CardBean> myCard(String router) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        return api.myIncome(map);
    }

    public Observable<BaseBean> updatePic(String router, String fileName, String fileExtName, String fileContext, String fileType) {
        Map<String, String> map = new HashMap<String, String>(6);
        map.put("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_CUST_JUID, SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, "1")));
        map.put("router", router);
        map.put("fileName", fileName);
        map.put("fileExtName", fileExtName);
        map.put("fileContext", fileContext);
        map.put("fileType", fileType);
        return api.uploadFile(map);
    }

    public Observable<ImmediateBean> preCheckBook(String router, String phone, String valid_code) {
        Map<String, String> map = new HashMap<String, String>(3);
        map.put("router", router);
        map.put("phone", phone);
        map.put("valid_code", valid_code);
        return api.preCheckBook(map);
    }

    public Observable<BaseBean> onlineToOffline(String router, String cust_juid) {
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("router", router);
        map.put("cust_juid", cust_juid);
        return api.onlineToOffline(map);
    }
}
