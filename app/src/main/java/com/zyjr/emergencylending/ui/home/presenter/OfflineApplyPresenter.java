package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.PrecheckResultBean;
import com.zyjr.emergencylending.entity.StoreResultBean;
import com.zyjr.emergencylending.model.home.loan.WriteInfoModel;
import com.zyjr.emergencylending.ui.home.View.OfflineApplyView;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;

import java.util.Map;

/**
 * 线下件申请
 *
 * @author neil
 * @date 2017/11/2
 */
public class OfflineApplyPresenter extends BasePresenter<OfflineApplyView> {

    public OfflineApplyPresenter(Context context) {
        super(context);
    }

    public void getLocalStoreList(Map<String, String> params) {
        invoke(WriteInfoModel.getInstance().getLocalStoreList(params), new ProgressSubscriber<ApiResult<StoreResultBean>>(new SubscriberOnNextListener<ApiResult<StoreResultBean>>() {
            @Override
            public void onNext(ApiResult<StoreResultBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取支持门店信息(成功)---->" + result.getMsg());
                        getView().onSuccessGetLocalStoreList(Constants.GET_LOCAL_STORE_INFO, result.getResult().getStorePoList());
                    }
                } else {
                    LogUtils.d("获取支持门店信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_LOCAL_STORE_INFO, result.getFlag(), result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取支持门店信息(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_LOCAL_STORE_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    // 提交预检 获取首续贷相关信息
    public void submitPrecheck(final Map<String, String> params) {
        invoke(WriteInfoModel.getInstance().submitPrecheck(params), new ProgressSubscriber<ApiResult<PrecheckResultBean>>(new SubscriberOnNextListener<ApiResult<PrecheckResultBean>>() {
            @Override
            public void onNext(ApiResult<PrecheckResultBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("提交预检(成功)---->" + result.getMsg());
                    if (StringUtil.isEmpty(params.get("store"))) {
                        getView().onSuccessPrecheck(Constants.SUBMIT_LOAN_INFORMATION, Config.ONLINE, result.getResult());
                    } else {
                        getView().onSuccessPrecheck(Constants.SUBMIT_LOAN_INFORMATION, Config.OFFLINE_CLERK, result.getResult());
                    }
                } else {
                    LogUtils.d("提交预检(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.SUBMIT_LOAN_INFORMATION, result.getFlag(), result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("提交预检(异常)---->" + e.getMessage());
                getView().onError(Constants.SUBMIT_LOAN_INFORMATION, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

}
