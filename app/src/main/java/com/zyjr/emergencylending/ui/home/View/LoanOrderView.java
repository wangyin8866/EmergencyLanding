package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.LoanOrderBean;

/**
 * 借款订单相关
 * Created by neil on 2017/10/24
 */
public interface LoanOrderView {

    void onSuccessGet(String returnCode, LoanOrderBean bean);

    void onFail(String errorMessage);

}