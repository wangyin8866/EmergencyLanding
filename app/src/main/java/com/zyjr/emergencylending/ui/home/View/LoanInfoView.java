package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.IDCardBackBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;

/**
 * Created by neil on 2017/10/23
 * 备注:
 */

public interface LoanInfoView {

    void onSuccessFrontBean(String returnCode, IDCardFrontBean model);

    void onSuccessBackBean(String returnCode, IDCardBackBean model);

    void successResult(String code, String value);

    void errorResult(String errorCode, String errorMsg);

}
