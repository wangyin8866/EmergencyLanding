package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.model.home.loan.AuthCenterModel;
import com.zyjr.emergencylending.model.home.loan.AuthHelperModel;
import com.zyjr.emergencylending.ui.home.View.AuthInfoView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.List;
import java.util.Map;

/**
 * 获取认证信息
 * Created by neil on 2017/10/25
 */
public class AuthInfoPresenter extends BasePresenter<AuthInfoView> {

    public AuthInfoPresenter(Context context) {
        super(context);
    }

    public void getCurrentAuthInfo(Map<String, String> params) {
        invoke(AuthCenterModel.getInstance().getCurrentAuthInfo(params), new ProgressSubscriber<ApiResult<List<AuthInfoBean>>>(new SubscriberOnNextListener<ApiResult<List<AuthInfoBean>>>() {
            @Override
            public void onNext(ApiResult<List<AuthInfoBean>> result) {
                LogUtils.d("用户认证信息明细---->" + result.getResult());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void submitAuthInfo(Map<String, String> params) {
        invoke(AuthHelperModel.getInstance().submitAuthInfo(params), new ProgressSubscriber<ApiResult<AuthInfoBean>>(new SubscriberOnNextListener<ApiResult<AuthInfoBean>>() {
            @Override
            public void onNext(ApiResult<AuthInfoBean> result) {
                LogUtils.d("提交认证信息成功---->" + result.getResult());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }


}
