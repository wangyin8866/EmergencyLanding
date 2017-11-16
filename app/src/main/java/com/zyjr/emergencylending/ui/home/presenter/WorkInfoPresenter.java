package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.WorkInfoBean;
import com.zyjr.emergencylending.model.home.loan.WorkInfoModel;
import com.zyjr.emergencylending.ui.home.View.WorkInfoView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.Map;

/**
 * Created by neil on 2017/10/24
 * 备注: 工作信息
 */
public class WorkInfoPresenter extends BasePresenter<WorkInfoView> {

    public WorkInfoPresenter(Context context) {
        super(context);
    }

    public void getWorkInfo(Map<String, String> params) {
        invoke(WorkInfoModel.getInstance().getWorkInfo(params), new ProgressSubscriber<ApiResult<WorkInfoBean>>(new SubscriberOnNextListener<ApiResult<WorkInfoBean>>() {
            @Override
            public void onNext(ApiResult<WorkInfoBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取工作信息(成功)---->" + result.getMsg());
                        getView().onSuccessGet(Constants.GET_WORK_INFO, result.getResult());
                    }
                } else {
                    LogUtils.d("获取工作信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_WORK_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取工作信息(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_WORK_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void addWorkInfo(Map<String, String> params) {
        invoke(WorkInfoModel.getInstance().addWorkInfo(params), new ProgressSubscriber<ApiResult<WorkInfoBean>>(new SubscriberOnNextListener<ApiResult<WorkInfoBean>>() {
            @Override
            public void onNext(ApiResult<WorkInfoBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("添加工作信息(成功)---->" + result.getMsg());
                    getView().onSuccessAdd(Constants.ADD_WORK_INFO, result.getPromptMsg());
                } else {
                    LogUtils.d("添加工作信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.ADD_WORK_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("添加工作信息(异常)---->" + e.getMessage());
                getView().onError(Constants.ADD_WORK_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void editWorkInfo(Map<String, String> params) {
        invoke(WorkInfoModel.getInstance().editWorkInfo(params), new ProgressSubscriber<ApiResult<WorkInfoBean>>(new SubscriberOnNextListener<ApiResult<WorkInfoBean>>() {
            @Override
            public void onNext(ApiResult<WorkInfoBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("修改工作信息(成功)---->" + result.getMsg());
                    getView().onSuccessAdd(Constants.EDIT_WORK_INFO, result.getPromptMsg());
                } else {
                    LogUtils.d("修改工作信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.EDIT_WORK_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("修改工作信息(异常)---->" + e.getMessage());
                getView().onError(Constants.ADD_WORK_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

}
