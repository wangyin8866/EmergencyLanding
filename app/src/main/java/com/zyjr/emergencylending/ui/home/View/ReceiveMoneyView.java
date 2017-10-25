package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.ReceiveMoneyBean;

/**
 * 领取金额回调
 * Created by neil on 2017/10/24
 */
public interface ReceiveMoneyView {

    void onSuccessGet(String returnCode, ReceiveMoneyBean bean);

    void onFail(String errorMessage);

}
