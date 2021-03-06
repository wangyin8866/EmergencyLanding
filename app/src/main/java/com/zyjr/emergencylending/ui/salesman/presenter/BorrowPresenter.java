package com.zyjr.emergencylending.ui.salesman.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.MessageBean;
import com.zyjr.emergencylending.entity.NoticeBean;
import com.zyjr.emergencylending.model.home.MessageModel;
import com.zyjr.emergencylending.ui.salesman.model.SalesmanModel;
import com.zyjr.emergencylending.ui.salesman.view.MessageView;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * @author wangyin
 * @date 2017/10/28.
 * @description :
 */

public class BorrowPresenter extends BasePresenter<MessageView> {
    public BorrowPresenter(Context context) {
        super(context);
    }

    public void getNoticeList(String router, final String get_num) {
        invoke(SalesmanModel.getInstance().getNoticeList(router, get_num), new ProgressSubscriber<NoticeBean>(new SubscriberOnNextListener<NoticeBean>() {
            @Override
            public void onNext(NoticeBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().getCommonData(baseBean);
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
}
