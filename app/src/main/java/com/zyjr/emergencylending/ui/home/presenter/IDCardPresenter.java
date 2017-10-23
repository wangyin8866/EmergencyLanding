package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.HttpSubscriber;
import com.zyjr.emergencylending.entity.IDCardBackBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;
import com.zyjr.emergencylending.model.home.loan.IDCardModel;
import com.zyjr.emergencylending.ui.home.View.IDCardView;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.io.File;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * Created by neil on 2017/10/19
 * 备注: 身份证信息
 */
@Deprecated
public class IDCardPresenter extends BasePresenter<IDCardView> {

    public IDCardPresenter(Context context) {
        super(context);
    }

    public void uploadFileGetIDCardFrontInfo(File file) {
        invoke(IDCardModel.getInstance().getIDCardFrontInfo(file), new ProgressSubscriber(new SubscriberOnNextListener<IDCardFrontBean>() {
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
        invoke(IDCardModel.getInstance().getIDCardBackInfo(file),new ProgressSubscriber(new SubscriberOnNextListener<IDCardBackBean>() {
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


}
