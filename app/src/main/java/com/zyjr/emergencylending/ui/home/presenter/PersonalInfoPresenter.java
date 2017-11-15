package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.megvii.livenesslib.util.Constant;
import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
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
                ToastAlone.showShortToast(BaseApplication.getContext(), Config.TIP_NET_ERROR);
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
                ToastAlone.showShortToast(BaseApplication.getContext(), Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void getPersonalInfo(Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().getPersonalInfo(params), new ProgressSubscriber<ApiResult<PersonalInfoBean>>(new SubscriberOnNextListener<ApiResult<PersonalInfoBean>>() {
            @Override
            public void onNext(ApiResult<PersonalInfoBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取个人信息(成功)---->" + result.getMsg());
                        getView().onSuccessGet(Constants.GET_PERSONAL_INFO, result.getResult());
                    }
                } else {
                    LogUtils.d("获取个人信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_PERSONAL_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取个人信息(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_PERSONAL_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void addPersonalInfo(Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().addPersonalInfo(params), new ProgressSubscriber<ApiResult<PersonalInfoBean>>(new SubscriberOnNextListener<ApiResult<PersonalInfoBean>>() {
            @Override
            public void onNext(ApiResult<PersonalInfoBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("添加个人信息(成功)---->" + result.getMsg());
                    getView().onSuccessAdd(Constants.ADD_PERSONAL_INFO, result.getPromptMsg());
                } else {
                    LogUtils.d("添加个人信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.ADD_PERSONAL_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("添加个人信息(异常)---->" + e.getMessage());
                getView().onError(Constants.ADD_PERSONAL_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void editPersonalInfo(Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().editPersonalInfo(params), new ProgressSubscriber<ApiResult<PersonalInfoBean>>(new SubscriberOnNextListener<ApiResult<PersonalInfoBean>>() {
            @Override
            public void onNext(ApiResult<PersonalInfoBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("更新个人信息(成功)---->" + result.getMsg());
                    getView().onSuccessAdd(Constants.EDIT_PERSONAL_INFO, result.getPromptMsg());
                } else {
                    LogUtils.d("更新个人信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.EDIT_PERSONAL_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("更新个人信息(异常)---->" + e.getMessage());
                getView().onError(Constants.EDIT_PERSONAL_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void uploadFile(final Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().uploadFile(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("上传图片(成功)---->" + result.getMsg());
                    getView().onSuccessUploadPic(Constants.UPLOAD_IDCARD_FILE, params.get("fileType"), result.getPromptMsg());
                } else {
                    LogUtils.d("上传图片(失败)---->" + result.getMsg());
                    getView().onFail(Constants.UPLOAD_IDCARD_FILE, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("上传图片(异常)---->" + e.getMessage());
                getView().onError(Constants.ADD_PERSONAL_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }


}
