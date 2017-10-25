package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.model.home.loan.AuthHelperModel;
import com.zyjr.emergencylending.ui.home.View.AuthHelperView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.Map;

/**
 * 提交认证信息
 * Created by neil on 2017/10/25
 */
public class AuthHelperPresenter extends BasePresenter<AuthHelperView> {

    public AuthHelperPresenter(Context context) {
        super(context);
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
