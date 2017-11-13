package com.zyjr.emergencylending.ui.home.View;

/**
 * 申请失败
 *
 * @author neil
 * @date 2017/11/13
 */
public interface HandleFailView {

    void onSuccessDeleteLoanOrder(String api, String result);

    void onFail(String apiCode, String failMsg);

    void onError(String apiCode, String errorMsg);

}
