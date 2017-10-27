package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.MessageBean;
import com.zyjr.emergencylending.model.home.MessageModel;

import java.util.Map;

/**
 * @author wangyin
 * @date 2017/10/27.
 * @description :
 */

public class MessagePresenter extends BasePresenter<BaseView<MessageBean>> {
    public MessagePresenter(Context context) {
        super(context);
    }
    public void getMessage(Map<String, String> map) {
        invoke(MessageModel.getInstance().getMessage(map),new ProgressSubscriber<MessageBean>(new SubscriberOnNextListener<MessageBean>() {
            @Override
            public void onNext(MessageBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().callBack(baseBean);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        },mContext));
    }
}
