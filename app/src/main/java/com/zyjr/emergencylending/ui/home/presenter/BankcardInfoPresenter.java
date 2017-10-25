package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
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

    public void getSupportBankList(Map<String, String> params){
        invoke(BankcardInfoModel.getInstance().getSupportBankList(params), new ProgressSubscriber<ApiResult<List<SupportBank>>>(new SubscriberOnNextListener<ApiResult<List<SupportBank>>>() {
            @Override
            public void onNext(ApiResult<List<SupportBank>> result) {
                LogUtils.d("获取支持银行卡信息成功---->" + result.getResult().toString());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void getBankcardInfo(Map<String, String> params) {
        invoke(BankcardInfoModel.getInstance().getBankcardInfo(params), new ProgressSubscriber<ApiResult<BankcardInfo>>(new SubscriberOnNextListener<ApiResult<BankcardInfo>>() {
            @Override
            public void onNext(ApiResult<BankcardInfo> result) {
                LogUtils.d("获取银行卡信息成功---->" + result.getResult().toString());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void addBankcardInfo(Map<String, String> params) {
        invoke(BankcardInfoModel.getInstance().addBankcardInfo(params), new ProgressSubscriber<ApiResult<BankcardInfo>>(new SubscriberOnNextListener<ApiResult<BankcardInfo>>() {
            @Override
            public void onNext(ApiResult<BankcardInfo> result) {
                LogUtils.d("添加银行卡信息成功---->" + result.getResult().toString());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void editBankcardInfo(Map<String, String> params) {
        invoke(BankcardInfoModel.getInstance().editBankcardInfo(params), new ProgressSubscriber<ApiResult<BankcardInfo>>(new SubscriberOnNextListener<ApiResult<BankcardInfo>>() {
            @Override
            public void onNext(ApiResult<BankcardInfo> result) {
                LogUtils.d("更新银行卡信息成功---->" + result.getResult().toString());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

}
