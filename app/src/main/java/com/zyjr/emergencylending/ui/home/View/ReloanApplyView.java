package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.PrecheckResultBean;

/**
 * 续贷申请 页面回调
 *
 * @author neil
 * @date 2017/11/2
 */
public interface ReloanApplyView {

    void onSuccessPrecheck(String apiCode, String flag, PrecheckResultBean precheckResultBean);

    void onFail(String apiCode, String flag, String failMsg);

    void onError(String apiCode, String errorMsg);

}
