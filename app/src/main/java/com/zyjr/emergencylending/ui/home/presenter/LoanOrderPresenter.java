package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.LoanOrderBean;
import com.zyjr.emergencylending.model.home.loan.LoanOrderModel;
import com.zyjr.emergencylending.ui.home.View.LoanOrderView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.Map;

/**
 * 借款订单
 * Created by neil on 2017/10/24
 */
public class LoanOrderPresenter extends BasePresenter<LoanOrderView> {

    public LoanOrderPresenter(Context context) {
        super(context);
    }

    public void getCurrentOrderDetail(Map<String, String> params) {
        invoke(LoanOrderModel.getInstance().getCurrentOrderDetail(params), new ProgressSubscriber<ApiResult<LoanOrderBean>>(new SubscriberOnNextListener<ApiResult<LoanOrderBean>>() {
            @Override
            public void onNext(ApiResult<LoanOrderBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("查询当前用户借款订单信息(成功)---->" + result.getResult().toString());
                    getView().onSuccessGet(Constants.GET_CURRENT_LOAN_ORDER_INFO, result.getResult());
                } else {
                    LogUtils.d("查询当前用户借款订单信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_CURRENT_LOAN_ORDER_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("查询当前用户借款订单信息(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_CURRENT_LOAN_ORDER_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }


    public void getCurrentEffectiveLoanOrder(Map<String, String> params) {
        invoke(LoanOrderModel.getInstance().getEffectiveLoanOrder(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("查询当前用户有效订单(成功)---->" + result.getResult().toString());
                    getView().onSuccessGetEffectiveOrder(Constants.GET_CURRENT_EFFECTIVE_LOAN_ORDER, result.getResult());
                } else {
                    LogUtils.d("查询当前用户有效订单(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_CURRENT_EFFECTIVE_LOAN_ORDER, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("查询当前用户有效订单(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_CURRENT_EFFECTIVE_LOAN_ORDER, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void deleteLoanOrder(Map<String, String> params) {
        invoke(LoanOrderModel.getInstance().deleteLoanOrder(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("进件做废件处理(成功)---->" + result.getResult().toString());
                    getView().onSuccessGetEffectiveOrder(Constants.DELETE_LOAN_ORDER_INFO, result.getResult());
                } else {
                    LogUtils.d("进件做废件处理(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.DELETE_LOAN_ORDER_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("进件做废件处理(异常)---->" + e.getMessage());
                getView().onError(Constants.DELETE_LOAN_ORDER_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }
}
