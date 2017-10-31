package com.zyjr.emergencylending.ui.salesman.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.CardBean;
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

    public void myCard(String router) {
        invoke(MineModel.getInstance().myCard(router),new ProgressSubscriber<CardBean>(new SubscriberOnNextListener<CardBean>() {
            @Override
            public void onNext(CardBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().myCard(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        },mContext));
    }


}
