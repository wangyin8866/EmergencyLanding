package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.ReceiveMoneyBean;
import com.zyjr.emergencylending.model.home.loan.ReceiveMoneyModel;
import com.zyjr.emergencylending.ui.home.View.ReceiveMoneyView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.Map;

/**
 * 领取金额
 * Created by neil on 2017/10/24
 */
public class ReceiveMoneyPresenter extends BasePresenter<ReceiveMoneyView> {

    public ReceiveMoneyPresenter(Context context) {
        super(context);
    }

    public void getReceiveMoneyInfo(Map<String, String> params) {
        invoke(ReceiveMoneyModel.getInstance().getReceiveMoneyInfo(params), new ProgressSubscriber<ApiResult<ReceiveMoneyBean>>(new SubscriberOnNextListener<ApiResult<ReceiveMoneyBean>>() {
            @Override
            public void onNext(ApiResult<ReceiveMoneyBean> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("获取领取金额信息成功---->" + result.getResult().toString());
                    getView().onSuccessGet(Constants.GET_RECEIVE_MONEY_INFO, result.getResult());
                } else {
                    LogUtils.d("获取领取金额信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_RECEIVE_MONEY_INFO, result.getMsg());
                }

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取领取金额信息异常---->" + e.getMessage());
                getView().onError(Constants.GET_RECEIVE_MONEY_INFO, e.getMessage());
            }
        }, mContext));
    }

}
