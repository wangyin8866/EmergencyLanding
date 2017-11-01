package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
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
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("查询当前用户借款订单信息成功---->" + result.getResult().toString());
                    getView().onSuccessGet(Constants.GET_CURRENT_LOAN_ORDER_INFO, result.getResult());
                } else {
                    LogUtils.d("查询当前用户借款订单信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_CURRENT_LOAN_ORDER_INFO, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("查询当前用户借款订单信息异常---->" + e.getMessage());
                getView().onError(Constants.GET_CURRENT_LOAN_ORDER_INFO, e.getMessage());
            }
        }, mContext));
    }
}
