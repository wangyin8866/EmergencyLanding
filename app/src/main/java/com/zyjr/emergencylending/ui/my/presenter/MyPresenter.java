package com.zyjr.emergencylending.ui.my.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.MyBorrow;
import com.zyjr.emergencylending.entity.RepaymentSuccess;
import com.zyjr.emergencylending.entity.UserInfo;
import com.zyjr.emergencylending.model.RepaymentModel;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.ui.my.View.MyView;
import com.zyjr.emergencylending.ui.salesman.model.SalesmanModel;
import com.zyjr.emergencylending.utils.ToastAlone;

import okhttp3.RequestBody;

/**
 * @author wangyin
 * @date 2017/8/9.
 * @description :
 */

public class MyPresenter extends BasePresenter<MyView> {

    public MyPresenter(Context context) {
        super(context);
    }

    public void uploadFile(String router, String fileName, String fileExtName, String fileContext, String fileType) {
        invoke(SalesmanModel.getInstance().updatePic(router, fileName, fileExtName, fileContext, fileType), new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean result) {
                if (result.getFlag().equals(Config.CODE_SUCCESS)) {
                    getView().update(result);
                } else {
                    ToastAlone.showShortToast(mContext, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }
    public void getBasicInfo(String router) {
        invoke(AccountModel.getInstance().getBasicInfo(router), new ProgressSubscriber<UserInfo>(new SubscriberOnNextListener<UserInfo>() {
            @Override
            public void onNext(UserInfo baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                        getView().getUserInfo(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void getData(String router, String pageNo, String pageSize) {
        invoke(AccountModel.getInstance().myBorrow(router, pageNo, pageSize), new ProgressSubscriber<MyBorrow>(new SubscriberOnNextListener<MyBorrow>() {
            @Override
            public void onNext(MyBorrow baseBean) {
                if (baseBean.getFlag().equals(Config.CODE_SUCCESS)) {
                    getView().getBorrowInfoByUserId(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void repaymentLogin(RequestBody body) {
        invoke(RepaymentModel.getInstance().repaymentLogin(body), new ProgressSubscriber<RepaymentSuccess>(new SubscriberOnNextListener<RepaymentSuccess>() {
            @Override
            public void onNext(RepaymentSuccess repaymentSuccess) {
                if ("0000".equals(repaymentSuccess.getReturncode())) {
                    getView().getRepaymentLogin(repaymentSuccess);
                } else {
                    ToastAlone.showShortToast(mContext, repaymentSuccess.getErrormsg());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }
}
