package com.zyjr.emergencylending.ui.salesman.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.CustomerBean;
import com.zyjr.emergencylending.entity.WaitApplyBean;
import com.zyjr.emergencylending.ui.salesman.model.CustomerModel;
import com.zyjr.emergencylending.ui.salesman.view.CustomerView;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * @author wangyin
 * @date 2017/10/28.
 * @description :
 */

public class CustomerPresenter extends BasePresenter<CustomerView> {
    public CustomerPresenter(Context context) {
        super(context);
    }

    public void myPerformance(String router,String type) {
        invoke(CustomerModel.getInstance().myPerformance(router,type), new ProgressSubscriber<CustomerBean>(new SubscriberOnNextListener<CustomerBean>() {
            @Override
            public void onNext(CustomerBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().myPerformance(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));

    }
    public void rankList(String router) {
        invoke(CustomerModel.getInstance().rankList(router), new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {

                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));

    }
    public void waitApply(String router,String type) {
        invoke(CustomerModel.getInstance().waitApply(router,type), new ProgressSubscriber<WaitApplyBean>(new SubscriberOnNextListener<WaitApplyBean>() {
            @Override
            public void onNext(WaitApplyBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().waitApply(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));

    }
}
