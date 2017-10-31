package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.WriteInfoBean;
import com.zyjr.emergencylending.model.home.loan.WriteInfoModel;
import com.zyjr.emergencylending.ui.home.View.WriteInfoView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.Map;

/**
 * Created by neil on 2017/10/23
 * 备注: 用户 借款资料信息(当前完成资料情况)
 */
public class WriteInfoPresenter extends BasePresenter<WriteInfoView> {

    public WriteInfoPresenter(Context context) {
        super(context);
    }

    public void getWriteInfo(Map<String, String> params) {
        invoke(WriteInfoModel.getInstance().getWriteInfo(params), new ProgressSubscriber<ApiResult<WriteInfoBean>>(new SubscriberOnNextListener<ApiResult<WriteInfoBean>>() {
            @Override
            public void onNext(ApiResult<WriteInfoBean> result) {
                if (result.getFlag().equals("API0000")) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取填写信息(资料完成情况)成功---->" + result.getResult().toString());
                        getView().onSuccessGet(Constants.GET_WRITE_INFO, result.getResult());
                    }
                } else {
                    LogUtils.d("获取填写信息(资料完成情况)失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_WRITE_INFO, result.getFlag(), result.getMsg());
                }

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取填写信息(资料完成情况)异常---->" + e.getMessage());
                getView().onError(Constants.GET_WRITE_INFO, e.getMessage());
            }
        }, mContext));
    }

    public void submitLoanInformation(Map<String, String> params) {
        invoke(WriteInfoModel.getInstance().submitLoanInformation(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                if (result.getFlag().equals("API0000")) {
                    if (result.getResult() != null) {
                        LogUtils.d("提交借款资料成功---->" + result.getResult().toString());
                        getView().onSuccessSubmit(Constants.SUBMIT_LOAN_INFORMATION, result.getMsg());
                    }
                } else {
                    LogUtils.d("提交借款资料失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.SUBMIT_LOAN_INFORMATION, result.getFlag(), result.getMsg());
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
