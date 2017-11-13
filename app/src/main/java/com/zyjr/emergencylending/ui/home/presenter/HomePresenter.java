package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.Banner;
import com.zyjr.emergencylending.entity.EffectiveOrderBean;
import com.zyjr.emergencylending.entity.UserInfo;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.model.home.HomeModel;
import com.zyjr.emergencylending.ui.home.View.HomeView;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 *
 * @author wangyin
 * @date 2017/8/9
 */

public class HomePresenter extends BasePresenter<HomeView>{
    public HomePresenter(Context context) {
        super(context);
    }
    public void getHomeAds(String router) {
        invoke(HomeModel.getInstance().getHomeAds(router),new ProgressSubscriber<Banner>(new SubscriberOnNextListener<Banner>() {
            @Override
            public void onNext(Banner banner) {
                if (Config.CODE_SUCCESS.equals(banner.getFlag())) {
                    getView().getBanner(banner);
                } else {
                    ToastAlone.showShortToast(mContext, banner.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastAlone.showShortToast(mContext, Config.TIP_NET_ERROR);
            }
        },mContext));
    }
    public void getBasicInfo(String router) {
        invoke(AccountModel.getInstance().getBasicInfo(router), new ProgressSubscriber<UserInfo>(new SubscriberOnNextListener<UserInfo>() {
            @Override
            public void onNext(UserInfo baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().getBasicInfo(baseBean);
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
    public void isEffectiveOrder(String router) {
        invoke(AccountModel.getInstance().isEffectiveOrder(router), new ProgressSubscriber<EffectiveOrderBean>(new SubscriberOnNextListener<EffectiveOrderBean>() {
            @Override
            public void onNext(EffectiveOrderBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().isEffectiveOrder(baseBean);
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
