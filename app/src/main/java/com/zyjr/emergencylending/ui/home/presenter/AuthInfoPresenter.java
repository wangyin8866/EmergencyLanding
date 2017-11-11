package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.HttpSubscriber;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.entity.AuthResult;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.model.home.loan.AuthCenterModel;
import com.zyjr.emergencylending.model.home.loan.AuthHelperModel;
import com.zyjr.emergencylending.model.home.loan.PersonalInfoModel;
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

        invoke(AuthCenterModel.getInstance().getCurrentAuthInfo(params), new HttpSubscriber<ApiResult<AuthResult>>() {
            @Override
            public void onRequestError(Throwable e) {
                LogUtils.d("获取用户认证信息明细异常--->" + e.getMessage());
                getView().onError(Constants.GET_AUTH_STATUS_INFO, e.getMessage());
            }

            @Override
            public void onSuccess(ApiResult<AuthResult> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("获取用户认证信息明细成功---->" + result.getResult().getAuth_result());
                    getView().onSuccessGet(Constants.GET_AUTH_STATUS_INFO, result.getResult().getAuth_result(), result.getResult().getUserName(), result.getResult().getIdcard());
                } else {
                    LogUtils.d("获取用户认证信息明细失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_AUTH_STATUS_INFO, result.getMsg());
                }
            }
        });

//        invoke(AuthCenterModel.getInstance().getCurrentAuthInfo(params), new ProgressSubscriber<ApiResult<AuthResult>>(new SubscriberOnNextListener<ApiResult<AuthResult>>() {
//            @Override
//            public void onNext(ApiResult<AuthResult> result) {
//                if (result.getFlag().equals("API0000")) {
//                    LogUtils.d("获取用户认证信息明细成功---->" + result.getResult().getAuth_result());
//                    getView().onSuccessGet(Constants.GET_AUTH_STATUS_INFO, result.getResult().getAuth_result());
//                } else {
//                    LogUtils.d("获取用户认证信息明细失败---->" + result.getFlag() + "," + result.getMsg());
//                    getView().onFail(Constants.GET_AUTH_STATUS_INFO, result.getMsg());
//                }
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtils.d("获取用户认证信息明细异常--->" + e.getMessage());
//                getView().onError(Constants.GET_AUTH_STATUS_INFO, e.getMessage());
//            }
//        }, mContext));
    }

    public void getPersonalInfo(Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().getPersonalInfo(params), new ProgressSubscriber<ApiResult<PersonalInfoBean>>(new SubscriberOnNextListener<ApiResult<PersonalInfoBean>>() {
            @Override
            public void onNext(ApiResult<PersonalInfoBean> result) {
                if (result.getFlag().equals("API0000")) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取个人信息成功---->" + result.getResult());
                        getView().onSuccessGetUserInfo(Constants.GET_PERSONAL_INFO, result.getResult());
                    }
                } else {
                    LogUtils.d("获取个人信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_PERSONAL_INFO, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取个人信息异常---->" + e.getMessage());
                getView().onError(Constants.GET_PERSONAL_INFO, e.getMessage());
            }
        }, mContext));
    }


    public void submitFaceAuth(Map<String, String> params) {
        invoke(AuthCenterModel.getInstance().submitFaceAuthInfo(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("提交人脸认证信息成功---->" + result.getMsg());
                    getView().onSuccessSubmitFace(Constants.SUBMIT_FACE_AUTH, result.getMsg());
                } else {
                    LogUtils.d("提交人脸认证信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.SUBMIT_FACE_AUTH, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("提交人脸认证信息异常---->" + e.getMessage());
                getView().onError(Constants.SUBMIT_FACE_AUTH, e.getMessage());
            }
        }, mContext));
    }

    public void submitAllAuthInfo(Map<String, String> params) {
        invoke(AuthCenterModel.getInstance().submitAllAuthInfo(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("提交所有认证信息成功---->" + result.getMsg());
                    getView().onSuccessSubmitAll(Constants.SUBMIT_ALL_AUTH_INFO, result.getMsg());
                } else {
                    LogUtils.d("提交所有认证信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.SUBMIT_ALL_AUTH_INFO, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("提交所有认证信息异常---->" + e.getMessage());
                getView().onError(Constants.SUBMIT_ALL_AUTH_INFO, e.getMessage());
            }
        }, mContext));
    }

}
