package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.IDCardBackBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;


/**
 * Created by neil on 2017/10/19
 * 备注:
 */
public interface IDCardView {

    void onSuccessFrontBean(String returnCode, IDCardFrontBean model);

    void onSuccessBackBean(String returnCode, IDCardBackBean model);

    void onFail(String errorMessage);

}
