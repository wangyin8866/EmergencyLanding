package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.CustomApiResult;
import com.zyjr.emergencylending.base.HttpSubscriber;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.SupportBank;
import com.zyjr.emergencylending.model.home.loan.BankcardInfoModel;
import com.zyjr.emergencylending.ui.home.View.BankcardInfoView;
import com.zyjr.emergencylending.utils.LogUtils;

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
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取支持银行信息(成功)---->" + result.getMsg());
                        getView().onSuccessGetSupportBanks(Constants.GET_SUPPORT_BANK_INFO, result.getResult());
                    }
                } else {
                    LogUtils.d("获取支持银行信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_SUPPORT_BANK_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取支持银行信息(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_SUPPORT_BANK_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void getBindBankcardInfo(Map<String, String> params) {
        invoke(BankcardInfoModel.getInstance().getBankcardInfo(params), new HttpSubscriber<CustomApiResult<BankcardInfo, BankcardInfo>>() {
            @Override
            public void onSuccess(CustomApiResult<BankcardInfo, BankcardInfo> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null ) {
                        LogUtils.d("获取绑定银行卡信息(成功:存在银行卡)---->" + result.getMsg());
                        getView().onSuccessGet(Constants.GET_BIND_BANKCARD_INFO, result.getResult());
                    } else if (result.getExt() != null) {
                        LogUtils.d("获取绑定银行卡信息(成功:没有银行卡)---->" + result.getExt().toString());
                        getView().onSuccessGetNoCard(Constants.GET_BIND_BANKCARD_INFO, result.getExt().getBank_username(), result.getExt().getId_card());
                    }
                } else {
                    LogUtils.d("获取银行卡信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_BIND_BANKCARD_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onRequestError(Throwable e) {
                LogUtils.d("获取绑定银行卡信息(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_BIND_BANKCARD_INFO, Config.TIP_NET_ERROR);
            }
        });

    }

    public void addBindBankcardInfo(Map<String, String> params) {
        invoke(BankcardInfoModel.getInstance().addBankcardInfo(params), new ProgressSubscriber<ApiResult<BankcardInfo>>(new SubscriberOnNextListener<ApiResult<BankcardInfo>>() {
            @Override
            public void onNext(ApiResult<BankcardInfo> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("添加银行卡信息(成功)---->" + result.getMsg());
                    getView().onSuccessAdd(Constants.ADD_BIND_BANKCARD_INFO, result.getPromptMsg());
                } else {
                    LogUtils.d("添加银行卡信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.ADD_BIND_BANKCARD_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("添加银行卡信息(异常)---->" + e.getMessage());
                getView().onError(Constants.ADD_BIND_BANKCARD_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void editBindBankcardInfo(Map<String, String> params) {
        invoke(BankcardInfoModel.getInstance().editBankcardInfo(params), new ProgressSubscriber<ApiResult<BankcardInfo>>(new SubscriberOnNextListener<ApiResult<BankcardInfo>>() {
            @Override
            public void onNext(ApiResult<BankcardInfo> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("修改银行卡信息(成功)---->" + result.getMsg());
                    getView().onSuccessEdit(Constants.EDIT_BIND_BANKCARD_INFO, result.getPromptMsg());
                } else {
                    LogUtils.d("修改银行卡信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.EDIT_BIND_BANKCARD_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("修改银行卡信息(异常)---->" + e.getMessage());
                getView().onError(Constants.EDIT_BIND_BANKCARD_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

}
