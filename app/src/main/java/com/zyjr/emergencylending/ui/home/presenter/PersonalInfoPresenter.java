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
                if (result != null) {
                    LogUtils.d("正面照返回数据结果集:---->" + new Gson().toJson(result));
                    getView().onSuccessFrontBean(result.getSide(), result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void uploadFileGetIDCardBackInfo(File file) {
        invoke(IDCardModel.getInstance().getIDCardBackInfo(file), new ProgressSubscriber<IDCardBackBean>(new SubscriberOnNextListener<IDCardBackBean>() {
            @Override
            public void onNext(IDCardBackBean result) {
                if (result != null) {
                    LogUtils.d("反面照返回数据结果集:---->" + new Gson().toJson(result));
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
                if (result.getFlag().equals("API0000")) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取个人信息成功---->" + result.getResult());
                        getView().onSuccessGet(Constants.GET_PERSONAL_INFO, result.getResult());
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

    public void addPersonalInfo(Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().addPersonalInfo(params), new ProgressSubscriber<ApiResult<PersonalInfoBean>>(new SubscriberOnNextListener<ApiResult<PersonalInfoBean>>() {
            @Override
            public void onNext(ApiResult<PersonalInfoBean> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("添加个人信息成功---->" + result.getMsg());
                    getView().onSuccessAdd(Constants.ADD_PERSONAL_INFO, result.getMsg());
                } else {
                    LogUtils.d("添加个人信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.ADD_PERSONAL_INFO, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("添加个人信息异常---->" + e.getMessage());
                getView().onError(Constants.ADD_PERSONAL_INFO, e.getMessage());
            }
        }, mContext));
    }


    public void editPersonalInfo(Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().editPersonalInfo(params), new ProgressSubscriber<ApiResult<PersonalInfoBean>>(new SubscriberOnNextListener<ApiResult<PersonalInfoBean>>() {
            @Override
            public void onNext(ApiResult<PersonalInfoBean> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("更新个人信息成功---->" + result.getMsg());
                    getView().onSuccessAdd(Constants.EDIT_PERSONAL_INFO, result.getMsg());
                } else {
                    LogUtils.d("更新个人信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.EDIT_PERSONAL_INFO, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("更新个人信息异常---->" + e.getMessage());
                getView().onError(Constants.ADD_PERSONAL_INFO, e.getMessage());
            }
        }, mContext));
    }

    public void uploadFile(final Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().uploadFile(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("上传图片成功---->" + result.getMsg());
                    getView().onSuccessUploadPic(Constants.UPLOAD_IDCARD_FILE, params.get("fileType"), result.getMsg());
                } else {
                    LogUtils.d("上传图片失败---->" + result.getMsg());
                    getView().onFail(Constants.UPLOAD_IDCARD_FILE, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("上传图片异常---->" + e.getMessage());
                getView().onError(Constants.ADD_PERSONAL_INFO, e.getMessage());
            }
        }, mContext));
    }


}
