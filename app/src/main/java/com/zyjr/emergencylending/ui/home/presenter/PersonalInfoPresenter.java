package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.IDCardBackBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.model.home.loan.IDCardModel;
import com.zyjr.emergencylending.model.home.loan.PersonalInfoModel;
import com.zyjr.emergencylending.ui.home.View.PersonalInfoView;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.Map;

import retrofit2.HttpException;

/**
 * Created by neil on 2017/10/19
 * 备注: 个人信息
 */
public class PersonalInfoPresenter extends BasePresenter<PersonalInfoView> {

    public PersonalInfoPresenter(Context context) {
        super(context);
    }

    public void uploadFileGetIDCardFrontInfo(File file) {
        invoke(IDCardModel.getInstance().getIDCardFrontInfo(file), new ProgressSubscriber<IDCardFrontBean>(new SubscriberOnNextListener<IDCardFrontBean>() {
            @Override
            public void onNext(IDCardFrontBean result) {
                LogUtils.d("正面照返回数据结果集:---->" + new Gson().toJson(result));
                if (result != null) {
                    getView().onSuccessFrontBean(result.getSide(), result);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof SocketTimeoutException) {
                    ToastAlone.showShortToast(BaseApplication.getContext(), "请求超时，稍后再试");
                    return;
                }
                if (e instanceof HttpException) {
                    String message = ((HttpException) e).message();
                    if (StringUtil.isEmpty(message)) {
                        ToastAlone.showShortToast(BaseApplication.getContext(), "请求超时，稍后再试");
                    } else {
                        LogUtils.e("请求返回信息处理失败:---->" + e.getMessage());
                        ToastAlone.showShortToast(BaseApplication.getContext(), e.getMessage());
                    }
                }
            }
        }, mContext));
    }

    public void uploadFileGetIDCardBackInfo(File file) {
        invoke(IDCardModel.getInstance().getIDCardBackInfo(file), new ProgressSubscriber<IDCardBackBean>(new SubscriberOnNextListener<IDCardBackBean>() {
            @Override
            public void onNext(IDCardBackBean result) {
                LogUtils.d("反面照返回数据结果集:---->" + new Gson().toJson(result));
                if (result != null) {
                    getView().onSuccessBackBean(result.getSide(), result);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof SocketTimeoutException) {
                    ToastAlone.showShortToast(BaseApplication.getContext(), "请求超时，稍后再试");
                    return;
                }
                if (e instanceof HttpException) {
                    String message = ((HttpException) e).message();
                    if (StringUtil.isEmpty(message)) {
                        ToastAlone.showShortToast(BaseApplication.getContext(), "请求超时，稍后再试");
                    } else {
                        LogUtils.e("请求返回信息处理失败:---->" + e.getMessage());
                        ToastAlone.showShortToast(BaseApplication.getContext(), e.getMessage());
                    }
                }
            }
        }, mContext));
    }

    public void getPersonalInfo(Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().getPersonalInfo(params), new ProgressSubscriber<ApiResult<PersonalInfoBean>>(new SubscriberOnNextListener<ApiResult<PersonalInfoBean>>() {
            @Override
            public void onNext(ApiResult<PersonalInfoBean> result) {
                if (result.getResult() != null) {
                    LogUtils.d("获取个人信息成功---->" + result.getResult().toString());
                    getView().onSuccessGet(Constants.GET_PERSONAL_INFO, result.getResult());
                } else {
                    LogUtils.d("获取个人信息---->" + result.getResult());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void addPersonalInfo(Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().addPersonalInfo(params), new ProgressSubscriber<ApiResult<PersonalInfoBean>>(new SubscriberOnNextListener<ApiResult<PersonalInfoBean>>() {
            @Override
            public void onNext(ApiResult<PersonalInfoBean> result) {
                if (result.getResult() != null) {
                    LogUtils.d("添加个人信息成功---->" + result.getResult().toString());
                    getView().onSuccessAdd(Constants.ADD_PERSONAL_INFO, result.getResult());
                } else {
                    LogUtils.d("添加个人信息---->" + result.getResult());
                }

            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }


    public void editPersonalInfo(Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().editPersonalInfo(params), new ProgressSubscriber<ApiResult<PersonalInfoBean>>(new SubscriberOnNextListener<ApiResult<PersonalInfoBean>>() {
            @Override
            public void onNext(ApiResult<PersonalInfoBean> result) {
                LogUtils.d("更新个人信息成功---->" + result.getResult().toString());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void uploadFile(Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().uploadFile(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {

            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

}
