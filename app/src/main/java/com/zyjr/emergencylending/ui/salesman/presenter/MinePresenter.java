package com.zyjr.emergencylending.ui.salesman.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.H5Bean;
import com.zyjr.emergencylending.ui.salesman.model.MineModel;
import com.zyjr.emergencylending.ui.salesman.view.MineView;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * @author wangyin
 * @date 2017/10/28.
 * @description :
 */

public class MinePresenter extends BasePresenter<MineView> {
    public MinePresenter(Context context) {
        super(context);
    }

    public void myIncome(String router) {
        invoke(MineModel.getInstance().myIncome(router), new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {
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
    public void helpPage(String router) {
        invoke(MineModel.getInstance().helpPage(router), new ProgressSubscriber<H5Bean>(new SubscriberOnNextListener<H5Bean>() {
            @Override
            public void onNext(H5Bean baseBean) {
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
}
