package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.PrecheckResultBean;
import com.zyjr.emergencylending.entity.SupportCityBean;
import com.zyjr.emergencylending.service.home.loan.ProductInfoService;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by neil on 2017/10/25
 * 产品相关 产品介绍|支持城市
 */
public class ProductInfoModel extends BaseModel {

    private ProductInfoService productInfoService;

    private ProductInfoModel() {
        super();
        productInfoService = retrofit.create(ProductInfoService.class);
    }

    private static class SingletonHolder {
        private static final ProductInfoModel productInfoModel = new ProductInfoModel();
    }

    public static ProductInfoModel getInstance() {
        return SingletonHolder.productInfoModel;
    }

    public Observable<ApiResult<PrecheckResultBean.ProIntroduceBean>> getProIntroduce(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_GET_PRODUCT_INTRODUCE);
        return productInfoService.getProIntroduce(params);
    }

    public Observable<ApiResult<List<SupportCityBean>>> getSupportCities(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_GET_SUPPORT_CITIES_LIST);
        return productInfoService.getProSupportCities(params);
    }

}
