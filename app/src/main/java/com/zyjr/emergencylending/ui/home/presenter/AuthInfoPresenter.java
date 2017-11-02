package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.entity.AuthResult;
import com.zyjr.emergencylending.model.home.loan.AuthCenterModel;
import com.zyjr.emergencylending.model.home.loan.AuthHelperModel;
import com.zyjr.emergencylending.ui.home.View.AuthInfoView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.List;
import java.util.Map;

/**
 * 获取认证信息
 * Created by neil on 2017/10/25
 */
public class AuthInfoPresenter extends BasePresenter<AuthInfoView> {

    public AuthInfoPresenter(Context context) {
        super(context);
    }

    public void getCurrentAuthInfo(Map<String, String> params) {
        invoke(AuthCenterModel.getInstance().getCurrentAuthInfo(params), new ProgressSubscriber<ApiResult<AuthResult>>(new SubscriberOnNextListener<ApiResult<AuthResult>>() {
            @Override
            public void onNext(ApiResult<AuthResult> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("获取用户认证信息明细成功---->" + result.getResult().getAuth_result());
                    getView().onSuccessGet(Constants.GET_AUTH_STATUS_INFO, result.getResult().getAuth_result());
                } else {
                    LogUtils.d("获取用户认证信息明细失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_AUTH_STATUS_INFO, result.getMsg());
                }

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取用户认证信息明细异常--->" + e.getMessage());
                getView().onError(Constants.GET_AUTH_STATUS_INFO, e.getMessage());
            }
        }, mContext));
    }

    public void submitAuthInfo(Map<String, String> params) {
        invoke(AuthHelperModel.getInstance().submitAuthInfo(params), new ProgressSubscriber<ApiResult<AuthInfoBean>>(new SubscriberOnNextListener<ApiResult<AuthInfoBean>>() {
            @Override
            public void onNext(ApiResult<AuthInfoBean> result) {
                LogUtils.d("提交认证信息成功---->" + result.getResult());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }


}
