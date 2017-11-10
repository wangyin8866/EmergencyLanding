package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.QrBean;
import com.zyjr.emergencylending.model.home.QrModel;
import com.zyjr.emergencylending.utils.AppToast;

/**
 * @author wangyin
 * @date 2017/10/27.
 * @description :
 */

public class QrPresenter extends BasePresenter<BaseView<QrBean>> {
    public QrPresenter(Context context) {

        super(context);
    }

    public void getQr(String router) {
        invoke(QrModel.getInstance().getQr(router),new ProgressSubscriber<QrBean>(new SubscriberOnNextListener<QrBean>() {
            @Override
            public void onNext(QrBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().getCommonData(baseBean);
                } else {
                    AppToast.showShortText(mContext,baseBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                AppToast.showShortText(mContext, e.getMessage());
            }
        },mContext));

    }
}
