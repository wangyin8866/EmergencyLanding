package com.zyjr.emergencylending.ui.salesman.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.ImmediateBean;
import com.zyjr.emergencylending.ui.salesman.model.SalesmanModel;
import com.zyjr.emergencylending.ui.salesman.view.ImmediatelyView;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * @author wangyin
 * @date 2017/10/31.
 * @description :
 */

public class ImmediatelyPresenter extends BasePresenter<ImmediatelyView> {
    public ImmediatelyPresenter(Context context) {
        super(context);
    }

    public void preCheckBook(String router, String phone,String valid_code) {
        invoke(SalesmanModel.getInstance().preCheckBook(router, phone,valid_code), new ProgressSubscriber<ImmediateBean>(new SubscriberOnNextListener<ImmediateBean>() {
            @Override
            public void onNext(ImmediateBean result) {
                if (result.getFlag().equals(Config.CODE_SUCCESS)) {
                    getView().preCheckBook(result);
                } else {
                    ToastAlone.showShortToast(mContext, result.getMsg());
                }
            }
            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void borrowSkip(String router ,String cust_juid) {
        invoke(SalesmanModel.getInstance().onlineToOffline(router,cust_juid), new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean result) {
                if (result.getFlag().equals(Config.CODE_SUCCESS)) {
                    getView().onlineToOffline(result);
                } else {
                    ToastAlone.showShortToast(mContext, result.getMsg());
                }
            }
            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }
}
