package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.ProIntroduceBean;
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
            public void onNext(ApiResult<List<SupportCityBean>> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("查询支持城市信息(成功)---->" + result.getResult());
                        getView().onSuccessGetSupportCity(Constants.GET_SUPPORT_CITIES_LIST, result.getResult());
                    }
                } else {
                    LogUtils.d("查询支持城市信息(失败)---->" + result.getFlag() + "," + result.getPromptMsg());
                    getView().onFail(Constants.GET_SUPPORT_CITIES_LIST, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("查询支持城市信息(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_SUPPORT_CITIES_LIST, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void getProIntroduce(Map<String, String> params) {
        invoke(ProductInfoModel.getInstance().getProIntroduce(params), new ProgressSubscriber<ApiResult<ProIntroduceBean>>(new SubscriberOnNextListener<ApiResult<ProIntroduceBean>>() {
            @Override
            public void onNext(ApiResult<ProIntroduceBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("查询产品介绍信息成功---->" + result.getResult());
                        getView().onSuccessGetIntro(Constants.GET_PRODUCT_INTRODUCE, result.getResult().getProduct_info());
                    }
                } else {
                    LogUtils.d("查询产品介绍信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_PRODUCT_INTRODUCE, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("查询产品介绍信息异常---->" + e.getMessage());
                getView().onError(Constants.GET_PRODUCT_INTRODUCE, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

}
