package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.MessageBean;
import com.zyjr.emergencylending.model.home.MessageModel;
import com.zyjr.emergencylending.ui.home.View.MessageView;

import java.util.Map;

/**
 * @author wangyin
 * @date 2017/10/27.
 * @description :
 */

public class MessagePresenter extends BasePresenter<MessageView> {
    public MessagePresenter(Context context) {
        super(context);
    }
    public void getMessage(String router,String pageNum) {
        invoke(MessageModel.getInstance().getMessage(router,pageNum),new ProgressSubscriber<MessageBean>(new SubscriberOnNextListener<MessageBean>() {
            @Override
            public void onNext(MessageBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().getMessage(baseBean);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        },mContext));
    }
    public void getMessageMore(String router,String pageNum) {
        invoke(MessageModel.getInstance().getMessage(router,pageNum),new ProgressSubscriber<MessageBean>(new SubscriberOnNextListener<MessageBean>() {
            @Override
            public void onNext(MessageBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().getMessageMore(baseBean);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        },mContext));
    }
    public void updateUserNews(Map<String, String> map) {
        invoke(MessageModel.getInstance().updateUserNews(map),new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().updateMessage(baseBean);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        },mContext));
    }
}
