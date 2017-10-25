package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.entity.SupportCityBean;
import com.zyjr.emergencylending.model.home.loan.ProductInfoModel;
import com.zyjr.emergencylending.ui.home.View.ProductInfoView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.List;
import java.util.Map;

/**
 * 产品相关
 * Created by neil on 2017/10/25
 */
public class ProductInfoPresenter extends BasePresenter<ProductInfoView> {

    public ProductInfoPresenter(Context context) {
        super(context);
    }

    public void getSupportCities(Map<String, String> params) {
        invoke(ProductInfoModel.getInstance().getSupportCities(params), new ProgressSubscriber<ApiResult<List<SupportCityBean>>>(new SubscriberOnNextListener<ApiResult<List<SupportCityBean>>>() {
            @Override
            public void onNext(ApiResult<List<SupportCityBean>> list) {
                LogUtils.d("查询支持城市信息成功---->" + list);
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void getProIntroduce(Map<String, String> params) {
        invoke(ProductInfoModel.getInstance().getProIntroduce(params), new ProgressSubscriber<ApiResult<List<String>>>(new SubscriberOnNextListener<ApiResult<List<String>>>() {
            @Override
            public void onNext(ApiResult<List<String>> list) {
                LogUtils.d("查询产品介绍成功---->" + list);
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

}
