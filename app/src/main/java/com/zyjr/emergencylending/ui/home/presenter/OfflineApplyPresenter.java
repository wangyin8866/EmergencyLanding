package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.StoreResultBean;
import com.zyjr.emergencylending.model.home.loan.WriteInfoModel;
import com.zyjr.emergencylending.ui.home.View.OfflineApplyView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.Map;

/**
 * 线下件申请
 *
 * @author neil
 * @date 2017/11/2
 */
public class OfflineApplyPresenter extends BasePresenter<OfflineApplyView> {

    public OfflineApplyPresenter(Context context) {
        super(context);
    }

    public void getLocalStoreList(Map<String, String> params) {
        invoke(WriteInfoModel.getInstance().getLocalStoreList(params), new ProgressSubscriber<ApiResult<StoreResultBean>>(new SubscriberOnNextListener<ApiResult<StoreResultBean>>() {
            @Override
            public void onNext(ApiResult<StoreResultBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取支持门店信息成功---->" + result.getResult().toString());
                        getView().onSuccessGetLocalStoreList(Constants.GET_LOCAL_STORE_INFO, result.getResult().getStorePoList());
                    }
                } else {
                    LogUtils.d("获取支持门店信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_LOCAL_STORE_INFO, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取支持门店信息异常---->" + e.getMessage());
                getView().onError(Constants.GET_LOCAL_STORE_INFO, e.getMessage());
            }
        }, mContext));
    }

    public void submitLoanInformation(Map<String, String> params) {
        invoke(WriteInfoModel.getInstance().submitLoanInformation(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("提交借款资料成功---->" + result.getResult().toString());
                    getView().onSuccessSubmit(Constants.SUBMIT_LOAN_INFORMATION, result.getMsg());
                } else {
                    LogUtils.d("提交借款资料失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.SUBMIT_LOAN_INFORMATION, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("提交借款资料异常---->" + e.getMessage());
                getView().onError(Constants.SUBMIT_LOAN_INFORMATION, e.getMessage());
            }
        }, mContext));
    }


}
