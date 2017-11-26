package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.entity.MobileBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.entity.ZhimaAuthBean;
import com.zyjr.emergencylending.model.home.loan.AuthHelperModel;
import com.zyjr.emergencylending.model.home.loan.PersonalInfoModel;
import com.zyjr.emergencylending.ui.home.View.AuthHelperView;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.net.SocketTimeoutException;
import java.util.Map;

import retrofit2.HttpException;

/**
 * 提交认证信息
 * Created by neil on 2017/10/25
 */
public class AuthHelperPresenter extends BasePresenter<AuthHelperView> {

    public AuthHelperPresenter(Context context) {
        super(context);
    }

    public void getZhimaAuthUrl(Map<String, String> params) {
        invoke(AuthHelperModel.getInstance().getZhimaAuthUrl(params), new ProgressSubscriber<ApiResult<ZhimaAuthBean>>(new SubscriberOnNextListener<ApiResult<ZhimaAuthBean>>() {
            @Override
            public void onNext(ApiResult<ZhimaAuthBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("获取芝麻信用授权地址成功---->" + result.getMsg());
                    getView().onSuccessGetZhimaAuthUrl(Constants.GET_ZHIMA_AUTH_URL, result.getResult());
                } else {
                    LogUtils.d("获取芝麻信用授权地址失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_ZHIMA_AUTH_URL, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取芝麻信用授权地址异常--->" + e.getMessage());
                getView().onError(Constants.GET_ZHIMA_AUTH_URL, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void getZhimaScore(Map<String, String> params) {
        invoke(AuthHelperModel.getInstance().getZhimaScore(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("获取芝麻信用分成功---->" + result.getMsg());
                    getView().onSuccessGetZhimaScore(Constants.GET_ZHIMA_AUTH_SCORE, result.getPromptMsg());
                } else {
                    LogUtils.d("获取芝麻信用分失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_ZHIMA_AUTH_SCORE, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取芝麻信用分异常--->" + e.getMessage());
                getView().onError(Constants.GET_ZHIMA_AUTH_SCORE, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void submitMobileAuthInfo(Map<String, String> params) {
        invoke(AuthHelperModel.getInstance().submitMobileAuthInfo(params), new ProgressSubscriber<ApiResult<AuthInfoBean>>(new SubscriberOnNextListener<ApiResult<AuthInfoBean>>() {
            @Override
            public void onNext(ApiResult<AuthInfoBean> result) {
                if ("00040004".equals(result.getFlag())) {
                    LogUtils.d("运营商采集认证,获取短信验证码成功---->" + result.getMsg());
                    getView().onSuccessSubmit(Constants.MOBILE_COLLECT_AUTH, result.getFlag(), result.getPromptMsg());
                } else if ("API0000".equals(result.getFlag())) {
                    LogUtils.d("运营商采集认证成功---->" + result.getMsg());
                    getView().onSuccessSubmit(Constants.MOBILE_COLLECT_AUTH, result.getFlag(), result.getPromptMsg());
                } else if ("API3020".equals(result.getFlag())) {
                    LogUtils.d("运营商采集认证失败(大数据那边处理有问题,需要调转第一步操作)---->" + result.getMsg());
                    getView().onSuccessSubmit(Constants.MOBILE_COLLECT_AUTH, result.getFlag(), result.getPromptMsg());
                } else {
                    LogUtils.d("运营商采集认证失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.MOBILE_COLLECT_AUTH, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("运营商采集认证异常--->" + e.toString());
                if (e instanceof SocketTimeoutException) {
                    getView().onError(Constants.MOBILE_COLLECT_AUTH, "请求超时,请稍后再试");
                } else {
                    getView().onError(Constants.MOBILE_COLLECT_AUTH, Config.TIP_NET_ERROR);
                }
            }
        }, mContext));
    }

    public void getPersonalInfo(Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().getPersonalInfo(params), new ProgressSubscriber<ApiResult<PersonalInfoBean>>(new SubscriberOnNextListener<ApiResult<PersonalInfoBean>>() {
            @Override
            public void onNext(ApiResult<PersonalInfoBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取个人信息成功---->" + result.getMsg());
                        getView().onSuccessGetUserInfo(Constants.GET_PERSONAL_INFO, result.getResult());
                    }
                } else {
                    LogUtils.d("获取个人信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_PERSONAL_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取个人信息异常---->" + e.getMessage());
                getView().onError(Constants.GET_PERSONAL_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void judgeMobileCodeValide(Map<String, String> params) {
        invoke(AuthHelperModel.getInstance().judgeMobileCodeValide(params), new ProgressSubscriber<ApiResult<MobileBean>>(new SubscriberOnNextListener<ApiResult<MobileBean>>() {
            @Override
            public void onNext(ApiResult<MobileBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("校验运营商验证是否有效(成功)---->" + result.getMsg());
                        getView().onSuccessJudgeMobileValide(Constants.JUDGE_MOBILE_CODE_VALIDE, result.getResult());
                    }
                } else {
                    LogUtils.d("校验运营商验证是否有效(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.JUDGE_MOBILE_CODE_VALIDE, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("校验运营商验证是否有效(异常)---->" + e.getMessage());
                if (e instanceof SocketTimeoutException) {
                    getView().onError(Constants.JUDGE_MOBILE_CODE_VALIDE, "请求超时,请稍后再试");
                } else {
                    getView().onError(Constants.JUDGE_MOBILE_CODE_VALIDE, Config.TIP_NET_ERROR);
                }
            }
        }, mContext));
    }


}
