package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.entity.IDCardBackBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.entity.WorkInfoBean;
import com.zyjr.emergencylending.model.home.loan.IDCardModel;
import com.zyjr.emergencylending.model.home.loan.LoanInfoModel;
import com.zyjr.emergencylending.ui.home.View.LoanInfoView;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.Map;

import retrofit2.HttpException;

/**
 * Created by neil on 2017/10/23
 * 备注:
 */
public class LoanInfoPresenter extends BasePresenter<LoanInfoView> {

    public LoanInfoPresenter(Context context) {
        super(context);
    }

    // 获取身份证正面信息
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
        invoke(IDCardModel.getInstance().getIDCardBackInfo(file),new ProgressSubscriber<IDCardBackBean>(new SubscriberOnNextListener<IDCardBackBean>() {
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
        },mContext));
    }

    public void getPersonalInfo(Map<String, String> params) {
        invoke(LoanInfoModel.getInstance().getPersonalInfo(params), new ProgressSubscriber<ApiResult<PersonalInfoBean>>(new SubscriberOnNextListener<ApiResult<PersonalInfoBean>>() {
            @Override
            public void onNext(ApiResult<PersonalInfoBean> result) {
                LogUtils.d("获取个人信息成功---->" + result.getResult().toString());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void savePerosonalInfo(Map<String, String> params) {
        invoke(LoanInfoModel.getInstance().savePersonInfo(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> stringApiResult) {
                LogUtils.d("保存个人信息成功---->" + stringApiResult.getResult());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void getWorkInfo(Map<String, String> params) {
        invoke(LoanInfoModel.getInstance().getWorkInfo(params), new ProgressSubscriber<ApiResult<WorkInfoBean>>(new SubscriberOnNextListener<ApiResult<WorkInfoBean>>() {
            @Override
            public void onNext(ApiResult<WorkInfoBean> result) {
                LogUtils.d("获取工作信息成功---->" + result.getResult().toString());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void saveWorkInfo(Map<String, String> params) {
        invoke(LoanInfoModel.getInstance().saveWorkInfo(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                LogUtils.d("保存工作信息成功---->" + result.getResult());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }


    public void getContantInfo(Map<String, String> params) {
        invoke(LoanInfoModel.getInstance().getContactInfo(params), new ProgressSubscriber<ApiResult<ContactInfoBean>>(new SubscriberOnNextListener<ApiResult<ContactInfoBean>>() {
            @Override
            public void onNext(ApiResult<ContactInfoBean> result) {
                LogUtils.d("获取联系人信息成功---->" + result.getResult().toString());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void saveContactInfo(Map<String, String> params) {
        invoke(LoanInfoModel.getInstance().saveWorkInfo(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                LogUtils.d("保存联系人信息成功---->" + result.getResult());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void getBankcardInfo(Map<String, String> params) {
        invoke(LoanInfoModel.getInstance().getBankcardInfo(params), new ProgressSubscriber<ApiResult<BankcardInfo>>(new SubscriberOnNextListener<ApiResult<BankcardInfo>>() {
            @Override
            public void onNext(ApiResult<BankcardInfo> result) {
                LogUtils.d("获取银行卡信息成功---->" + result.getResult().toString());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void saveBankcardInfo(Map<String, String> params) {
        invoke(LoanInfoModel.getInstance().saveBankcardInfo(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                LogUtils.d("保存银行卡信息成功---->" + result.getResult());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

}
