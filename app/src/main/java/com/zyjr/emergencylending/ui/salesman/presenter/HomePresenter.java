package com.zyjr.emergencylending.ui.salesman.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.NoticeBean;
import com.zyjr.emergencylending.ui.salesman.model.HomeModel;
import com.zyjr.emergencylending.ui.salesman.view.HomeView;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * @author wangyin
 * @date 2017/10/28.
 * @description :
 */

public class HomePresenter extends BasePresenter<HomeView> {
    public HomePresenter(Context context) {
        super(context);
    }

    public void getNoticeList(String router, final String get_num) {
        invoke(HomeModel.getInstance().getNoticeList(router, get_num), new ProgressSubscriber<NoticeBean>(new SubscriberOnNextListener<NoticeBean>() {
            @Override
            public void onNext(NoticeBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().callBack(baseBean);
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
