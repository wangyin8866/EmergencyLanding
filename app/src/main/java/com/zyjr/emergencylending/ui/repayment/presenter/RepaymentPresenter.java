package com.zyjr.emergencylending.ui.repayment.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.EffectiveOrderBean;
import com.zyjr.emergencylending.entity.H5Bean;
import com.zyjr.emergencylending.entity.MyBorrow;
import com.zyjr.emergencylending.entity.RepaymentSuccess;
import com.zyjr.emergencylending.entity.UserInfo;
import com.zyjr.emergencylending.model.RepaymentModel;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.ui.repayment.view.RepaymentView;
import com.zyjr.emergencylending.utils.ToastAlone;

import okhttp3.RequestBody;

/**
 * @author wangyin
 * @date 2017/8/9
 */

public class RepaymentPresenter extends BasePresenter<RepaymentView> {
    public RepaymentPresenter(Context context) {
        super(context);
    }

    /**
     * 查看是否有还款(废弃)
     *
     * @param router
     * @param pageNo
     * @param pageSize
     */
    @Deprecated
    public void getData(String router, String pageNo, String pageSize) {
        invoke(AccountModel.getInstance().myBorrow(router, pageNo, pageSize), new ProgressSubscriber<MyBorrow>(new SubscriberOnNextListener<MyBorrow>() {
            @Override
            public void onNext(MyBorrow baseBean) {
                if (baseBean.getFlag().equals(Config.CODE_SUCCESS)) {
                    getView().requestSuccess();
                    getView().getBorrowInfoByUserId(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                getView().requestError();
            }
        }, mContext));
    }
    /**
     * 获取订单状态
     *
     * @param router
     */
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
                getView().requestError();
//                ToastAlone.showShortToast(mContext, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }
    /**
     * 获取手机号和身份证
     *
     * @param router
     */
    public void getBasicInfo(String router) {
        invoke(AccountModel.getInstance().getBasicInfo(router), new ProgressSubscriber<UserInfo>(new SubscriberOnNextListener<UserInfo>() {
            @Override
            public void onNext(UserInfo baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().getUserInfo(baseBean);
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

    /**
     * 掉还款的登陆接口
     *
     * @param body
     */
    public void repaymentLogin(RequestBody body) {
        invoke(RepaymentModel.getInstance().repaymentLogin(body), new ProgressSubscriber<RepaymentSuccess>(new SubscriberOnNextListener<RepaymentSuccess>() {
            @Override
            public void onNext(RepaymentSuccess repaymentSuccess) {
                if ("0000".equals(repaymentSuccess.getReturncode())) {
                    getView().getRepaymentLogin(repaymentSuccess);
                } else {
                    ToastAlone.showShortToast(mContext, repaymentSuccess.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastAlone.showShortToast(mContext, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    /**
     * 获取H5页面
     *
     * @param url_type
     */
    public void getRepaymentH5Url(String url_type
    ) {
        invoke(AccountModel.getInstance().getH5Url(url_type), new ProgressSubscriber<H5Bean>(new SubscriberOnNextListener<H5Bean>() {
            @Override
            public void onNext(H5Bean baseBean) {
                if (baseBean.getFlag().equals(Config.CODE_SUCCESS)) {

                    getView().loadH5(baseBean);

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
