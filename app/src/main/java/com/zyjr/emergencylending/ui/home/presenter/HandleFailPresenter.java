package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.model.home.loan.LoanOrderModel;
import com.zyjr.emergencylending.ui.home.View.HandleFailView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.Map;

/**
 * 订单失败 作废件
 *
 * @author neil
 * @date 2017/11/13
 */
public class HandleFailPresenter extends BasePresenter<HandleFailView> {

    public HandleFailPresenter(Context context) {
        super(context);
    }

    public void deleteLoanOrder(Map<String, String> params) {
        invoke(LoanOrderModel.getInstance().deleteLoanOrder(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("进件做废件处理成功---->" + result.getPromptMsg());
                    getView().onSuccessDeleteLoanOrder(Constants.DELETE_LOAN_ORDER_INFO, result.getPromptMsg());
                } else {
                    LogUtils.d("进件做废件处理失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.DELETE_LOAN_ORDER_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("进件做废件处理异常---->" + e.getMessage());
                getView().onError(Constants.DELETE_LOAN_ORDER_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

}
