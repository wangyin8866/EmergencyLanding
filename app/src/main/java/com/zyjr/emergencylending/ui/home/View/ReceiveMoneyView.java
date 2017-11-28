package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.ReceiveMoneyBean;
import com.zyjr.emergencylending.entity.RemindBean;

/**
 * 领取金额回调
 * Created by neil on 2017/10/24
 */
public interface ReceiveMoneyView {

    void onSuccessGet(String apiCode, ReceiveMoneyBean bean);

    void onFail(String apiCode, String failMsg);

    void onError(String apiCode, String errorMsg);

    void onSuccessConfirmReceive(String apiCode, String msg, RemindBean remindBean);

}
