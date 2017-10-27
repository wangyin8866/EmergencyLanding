package com.zyjr.emergencylending.model.home;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.entity.MessageBean;
import com.zyjr.emergencylending.service.Api;

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


    public Observable<MessageBean> getMessage(Map<String, String> map) {

        return api.getUserNews(map);
    }
}
