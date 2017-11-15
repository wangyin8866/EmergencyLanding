package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.HttpSubscriber;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.MayApplyProBean;
import com.zyjr.emergencylending.entity.StoreResultBean;
import com.zyjr.emergencylending.entity.WriteInfoBean;
import com.zyjr.emergencylending.model.home.loan.WriteInfoModel;
import com.zyjr.emergencylending.ui.home.View.WriteInfoView;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;

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
        invoke(WriteInfoModel.getInstance().getWriteInfo(params), new HttpSubscriber<ApiResult<WriteInfoBean>>() {
            @Override
            public void onSuccess(ApiResult<WriteInfoBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取填写信息(资料完成情况)成功---->" + result.getMsg());
                        getView().onSuccessGet(Constants.GET_WRITE_INFO, result.getResult());
                    }
                } else {
                    LogUtils.d("获取填写信息(资料完成情况)失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_WRITE_INFO, result.getFlag(), result.getPromptMsg());
                }
            }

            @Override
            public void onRequestError(Throwable e) {
                LogUtils.d("获取填写信息(资料完成情况)异常---->" + e.getMessage());
                getView().onError(Constants.GET_WRITE_INFO, Config.TIP_NET_ERROR);
            }
        });
    }

    public void submitLoanInformation(final Map<String, String> params) {
        invoke(WriteInfoModel.getInstance().submitLoanInformation(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("提交借款资料(成功)---->" + result.getMsg());
                    if (StringUtil.isEmpty(params.get("store"))) {
                        getView().onSuccessSubmit(Constants.SUBMIT_LOAN_INFORMATION, Config.ONLINE, result.getPromptMsg());
                    } else {
                        getView().onSuccessSubmit(Constants.SUBMIT_LOAN_INFORMATION, Config.OFFLINE_CLERK, result.getPromptMsg());
                    }
                } else {
                    LogUtils.d("提交借款资料(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.SUBMIT_LOAN_INFORMATION, result.getFlag(), result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("提交借款资料(异常)---->" + e.getMessage());
                getView().onError(Constants.SUBMIT_LOAN_INFORMATION, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void getMayApplyProductType(Map<String, String> params) {
        invoke(WriteInfoModel.getInstance().getMayApplayProductType(params), new ProgressSubscriber<ApiResult<MayApplyProBean>>(new SubscriberOnNextListener<ApiResult<MayApplyProBean>>() {
            @Override
            public void onNext(ApiResult<MayApplyProBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取可申请产品类型(成功)---->" + result.getMsg());
                        getView().onSuccessGetMayApplyPro(Constants.GET_MAYAPPLY_PRODUCT_TYPE, result.getResult());
                    }
                } else {
                    LogUtils.d("获取可申请产品类型(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_MAYAPPLY_PRODUCT_TYPE, result.getFlag(), result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取可申请产品类型(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_MAYAPPLY_PRODUCT_TYPE, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void getClerkStoreInfo(Map<String, String> params) {
        invoke(WriteInfoModel.getInstance().getLocalStoreList(params), new ProgressSubscriber<ApiResult<StoreResultBean>>(new SubscriberOnNextListener<ApiResult<StoreResultBean>>() {
            @Override
            public void onNext(ApiResult<StoreResultBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取业务员门店信息(成功)---->" + result.getMsg());
                        getView().onSuccessGetClerkStore(Constants.GET_LOCAL_STORE_INFO, result.getResult().getStorePoList());
                    }
                } else if (result.getFlag().equals("API2022")) {
                    LogUtils.d("获取业务员门店信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_LOCAL_STORE_INFO, result.getFlag(), result.getPromptMsg());
                } else {
                    LogUtils.d("获取业务员门店信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_LOCAL_STORE_INFO, result.getFlag(), result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取业务员门店信息(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_LOCAL_STORE_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

}
