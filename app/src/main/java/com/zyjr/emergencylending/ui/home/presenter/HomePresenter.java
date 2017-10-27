package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.model.home.HomeModel;

/**
 *
 * @author wangyin
 * @date 2017/8/9
 */

public class HomePresenter extends BasePresenter<BaseView<BaseBean>>{
    public HomePresenter(Context context) {
        super(context);
    }
    public void getHomeAds(String router) {
        invoke(HomeModel.getInstance().getHomeAds(router),new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean baseBean) {

            }

            @Override
            public void onError(Throwable e) {

            }
        },mContext));
    }
}
