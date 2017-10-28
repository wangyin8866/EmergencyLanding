package com.zyjr.emergencylending.ui.my.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.H5Bean;
import com.zyjr.emergencylending.model.SettingModel;
import com.zyjr.emergencylending.ui.my.View.SettingView;
import com.zyjr.emergencylending.utils.AppToast;

/**
 * @author wangyin
 * @date 2017/8/9.
 * @description :
 */

public class SettingPresenter extends BasePresenter<SettingView> {

    public SettingPresenter(Context context) {
        super(context);
    }
    public void aboutUs(String router) {
        invoke(SettingModel.getInstance().aboutUs(router),new ProgressSubscriber<H5Bean>(new SubscriberOnNextListener<H5Bean>() {
            @Override
            public void onNext(H5Bean h5Bean) {
                if (Config.CODE_SUCCESS.equals(h5Bean.getFlag())) {
                    getView().aboutUs(h5Bean);
                } else {
                    AppToast.showShortText(mContext,h5Bean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                AppToast.showShortText(mContext, e.getMessage());
            }
        },mContext));

    }
}
