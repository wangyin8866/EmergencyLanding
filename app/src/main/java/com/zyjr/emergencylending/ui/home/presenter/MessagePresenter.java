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
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * @author wangyin
 * @date 2017/10/27.
 * @description :
 */

public class MessagePresenter extends BasePresenter<MessageView> {
    public MessagePresenter(Context context) {
        super(context);
    }

    public void getMessage(String router, String pageNum, String pageSize) {
        invoke(MessageModel.getInstance().getMessage(router, pageNum, pageSize), new ProgressSubscriber<MessageBean>(new SubscriberOnNextListener<MessageBean>() {
            @Override
            public void onNext(MessageBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().getMessage(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastAlone.showShortToast(mContext, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void getMessageMore(String router, String pageNum, String pageSize) {
        invoke(MessageModel.getInstance().getMessage(router, pageNum, pageSize), new ProgressSubscriber<MessageBean>(new SubscriberOnNextListener<MessageBean>() {
            @Override
            public void onNext(MessageBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().getMessageMore(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastAlone.showShortToast(mContext, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void updateUserNews(String router, String getNews_id, final String opr_type, final int position) {
        invoke(MessageModel.getInstance().updateUserNews(router, getNews_id, opr_type), new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    switch (opr_type) {
                        case "1":
                            //更新
                            getView().updateMessage(opr_type, position);
                            break;
                        case "2":
                            //删去
                            getView().updateMessage(opr_type, position);
                            break;
                        case "3":
                            //清空
                            getView().updateMessage(opr_type, position);
                            break;
                    }

                } else {

                    ToastAlone.showShortToast(mContext, baseBean.getPromptMsg());

                }
            }

            @Override
            public void onError(Throwable e) {
                ToastAlone.showShortToast(mContext, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }
}
