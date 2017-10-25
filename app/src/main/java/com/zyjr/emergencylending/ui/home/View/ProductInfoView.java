package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.SupportCityBean;

import java.util.List;

/**
 * Created by neil on 2017/10/25
 */

public interface ProductInfoView {

    void onSuccessGetSupportCity(String returnCode, List<SupportCityBean> cityList);

    void onSuccessGetIntro(String returnCode, List<String> introList);

    void onFail(String errorMessage);

}
