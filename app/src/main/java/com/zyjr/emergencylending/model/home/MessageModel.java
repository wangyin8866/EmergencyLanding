package com.zyjr.emergencylending.model.home;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.MessageBean;
import com.zyjr.emergencylending.service.Api;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * @author wangyin
 * @date 2017/10/24
 */

public class MessageModel extends BaseModel {
    private Api api;

    private MessageModel() {
        super();
        api = retrofit.create(Api.class);
    }

    private static class SingletonHolder {
        private static final MessageModel HOME_MODEL = new MessageModel();
    }

    public static MessageModel getInstance() {
        return SingletonHolder.HOME_MODEL;
    }

    /**
     * 获取消息
     *
     * @return
     */
    public Observable<MessageBean> getMessage(String router, String pageNum,String pageSize) {
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("router", router);
        map.put("pageNo", pageNum);
        map.put("pageSize", pageSize);
        return api.getUserNews(map);
    }

    /**
     * 消息操作
     *
     * @return
     */
    public Observable<BaseBean> updateUserNews(String router, String getNews_id, String opr_type) {
        Map<String, String> map = new HashMap<String, String>(3);
        map.put("router", router);
        map.put("news_id", getNews_id);
        map.put("opr_type", opr_type);
        return api.updateUserNews(map);
    }
}
