package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.PrecheckResultBean;
import com.zyjr.emergencylending.model.home.loan.WriteInfoModel;
import com.zyjr.emergencylending.ui.home.View.ProductInfoView;
import com.zyjr.emergencylending.ui.home.View.ReloanApplyView;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;

import java.util.Map;

/**
 * 续贷 产品相关
 * Created by neil on 2017/10/25
 */
public class ReloanApplyPresenter extends BasePresenter<ReloanApplyView> {

    public ReloanApplyPresenter(Context context) {
        super(context);
    }

    public void submitPrecheck(final Map<String, String> params) {
        invoke(WriteInfoModel.getInstance().submitPrecheck(params), new ProgressSubscriber<ApiResult<PrecheckResultBean>>(new SubscriberOnNextListener<ApiResult<PrecheckResultBean>>() {
            @Override
            public void onNext(ApiResult<PrecheckResultBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("续贷提交预检(成功)---->" + result.getMsg());
                    getView().onSuccessPrecheck(Constants.SUBMIT_LOAN_INFORMATION, Config.ONLINE, result.getResult());
                } else {
                    LogUtils.d("续贷提交预检(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.SUBMIT_LOAN_INFORMATION, result.getFlag(), result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("续贷提交预检(异常)---->" + e.getMessage());
                getView().onError(Constants.SUBMIT_LOAN_INFORMATION, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

}
