package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.entity.WriteInfoBean;
import com.zyjr.emergencylending.model.home.loan.WriteInfoModel;
import com.zyjr.emergencylending.ui.home.View.WriteInfoView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.Map;

/**
 * Created by neil on 2017/10/23
 * 备注: 用户 借款资料信息
 */
public class WriteInfoPresenter extends BasePresenter<WriteInfoView> {

    public WriteInfoPresenter(Context context) {
        super(context);
    }

    public void getWriteInfo(Map<String, String> params) {
        invoke(WriteInfoModel.getInstance().getWriteInfo(params), new ProgressSubscriber<ApiResult<WriteInfoBean>>(new SubscriberOnNextListener<ApiResult<WriteInfoBean>>() {
            @Override
            public void onNext(ApiResult<WriteInfoBean> result) {
                LogUtils.d("获取工作信息成功---->" + result.getResult().toString());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

}
