package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.Banner;
import com.zyjr.emergencylending.model.home.HomeModel;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 *
 * @author wangyin
 * @date 2017/8/9
 */

public class HomePresenter extends BasePresenter<BaseView<Banner>>{
    public HomePresenter(Context context) {
        super(context);
    }
    public void getHomeAds(String router) {
        invoke(HomeModel.getInstance().getHomeAds(router),new ProgressSubscriber<Banner>(new SubscriberOnNextListener<Banner>() {
            @Override
            public void onNext(Banner banner) {
                if (Config.CODE_SUCCESS.equals(banner.getFlag())) {
                    getView().callBack(banner);
                } else {
                    ToastAlone.showShortToast(mContext, banner.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastAlone.showShortToast(mContext, Config.TIP_NET_ERROR);
            }
        },mContext));
    }
}
