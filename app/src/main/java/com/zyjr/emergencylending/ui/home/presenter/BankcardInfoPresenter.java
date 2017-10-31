package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.CustomApiResult;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.SupportBank;
import com.zyjr.emergencylending.model.home.loan.BankcardInfoModel;
import com.zyjr.emergencylending.ui.home.View.BankcardInfoView;
import com.zyjr.emergencylending.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by neil on 2017/10/24
 * 备注: 银行卡相关
 */
public class BankcardInfoPresenter extends BasePresenter<BankcardInfoView> {


    public BankcardInfoPresenter(Context context) {
        super(context);
    }

    public void getSupportBankList(Map<String, String> params) {
        invoke(BankcardInfoModel.getInstance().getSupportBankList(params), new ProgressSubscriber<ApiResult<List<SupportBank>>>(new SubscriberOnNextListener<ApiResult<List<SupportBank>>>() {
            @Override
            public void onNext(ApiResult<List<SupportBank>> result) {
                if (result.getFlag().equals("API0000")) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取支持银行信息成功---->" + result.getResult().toString());
                        getView().onSuccessGetSupportBanks(Constants.GET_SUPPORT_BANK_INFO, result.getResult());
                    }
                } else {
                    LogUtils.d("获取支持银行信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_SUPPORT_BANK_INFO, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取支持银行信息异常---->" + e.getMessage());
                getView().onError(Constants.GET_SUPPORT_BANK_INFO, e.getMessage());
            }
        }, mContext));
    }

    public void getBindBankcardInfo(Map<String, String> params) {
        invoke(BankcardInfoModel.getInstance().getBankcardInfo(params), new ProgressSubscriber<CustomApiResult<BankcardInfo, BankcardInfo>>(new SubscriberOnNextListener<CustomApiResult<BankcardInfo, BankcardInfo>>() {
            @Override
            public void onNext(CustomApiResult<BankcardInfo, BankcardInfo> result) {
                if (result.getFlag().equals("API0000")) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取绑定银行卡信息成功(存在银行卡)---->" + result.getResult().toString());
                        getView().onSuccessGet(Constants.GET_BIND_BANKCARD_INFO, result.getResult());
                    } else if (result.getExt() != null) {
                        LogUtils.d("获取绑定银行卡信息成功(没有银行卡)---->" + result.getExt().toString());
                        getView().onSuccessGetNoCard(Constants.GET_BIND_BANKCARD_INFO, result.getExt().getBank_username(), result.getExt().getId_card());
                    }
                } else {
                    LogUtils.d("获取银行卡信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_BIND_BANKCARD_INFO, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取银行卡信息异常---->" + e.getMessage());
                getView().onError(Constants.GET_BIND_BANKCARD_INFO, e.getMessage());
            }
        }, mContext));
    }

    public void addBindBankcardInfo(Map<String, String> params) {
        invoke(BankcardInfoModel.getInstance().addBankcardInfo(params), new ProgressSubscriber<ApiResult<BankcardInfo>>(new SubscriberOnNextListener<ApiResult<BankcardInfo>>() {
            @Override
            public void onNext(ApiResult<BankcardInfo> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("添加银行卡信息成功---->" + result.getMsg());
                    getView().onSuccessAdd(Constants.ADD_BIND_BANKCARD_INFO, result.getMsg());
                } else {
                    LogUtils.d("添加银行卡信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.ADD_BIND_BANKCARD_INFO, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("添加银行卡信息异常---->" + e.getMessage());
                getView().onError(Constants.ADD_BIND_BANKCARD_INFO, e.getMessage());
            }
        }, mContext));
    }

    public void editBindBankcardInfo(Map<String, String> params) {
        invoke(BankcardInfoModel.getInstance().editBankcardInfo(params), new ProgressSubscriber<ApiResult<BankcardInfo>>(new SubscriberOnNextListener<ApiResult<BankcardInfo>>() {
            @Override
            public void onNext(ApiResult<BankcardInfo> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("修改银行卡信息成功---->" + result.getMsg());
                    getView().onSuccessEdit(Constants.EDIT_BIND_BANKCARD_INFO, result.getMsg());
                } else {
                    LogUtils.d("修改银行卡信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.EDIT_BIND_BANKCARD_INFO, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("修改银行卡信息异常---->" + e.getMessage());
                getView().onError(Constants.EDIT_BIND_BANKCARD_INFO, e.getMessage());
            }
        }, mContext));
    }

}
