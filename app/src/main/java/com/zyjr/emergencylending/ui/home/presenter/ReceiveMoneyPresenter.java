package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.ReceiveMoneyBean;
import com.zyjr.emergencylending.entity.RemindBean;
import com.zyjr.emergencylending.model.home.loan.ReceiveMoneyModel;
import com.zyjr.emergencylending.ui.home.View.ReceiveMoneyView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.List;
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
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("获取领取金额信息(成功)---->" + result.getMsg());
                    getView().onSuccessGet(Constants.GET_RECEIVE_MONEY_INFO, result.getResult());
                } else {
                    LogUtils.d("获取领取金额信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_RECEIVE_MONEY_INFO, result.getPromptMsg());
                }

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取领取金额信息(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_RECEIVE_MONEY_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void confirmReceiveInfo(Map<String, String> params) {
        invoke(ReceiveMoneyModel.getInstance().confirmReceiveInfo(params), new ProgressSubscriber<ApiResult<RemindBean>>(new SubscriberOnNextListener<ApiResult<RemindBean>>() {
            @Override
            public void onNext(ApiResult<RemindBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("确认领取金额(成功)---->" + result.getMsg());
                    getView().onSuccessConfirmReceive(Constants.CONFIRM_RECEIVE_INFO, result.getMsg(), result.getResult());
                } else {
                    LogUtils.d("确认领取金额(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.CONFIRM_RECEIVE_INFO, result.getPromptMsg());
                }

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("确认领取金额(异常)---->" + e.getMessage());
                getView().onError(Constants.CONFIRM_RECEIVE_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

}
