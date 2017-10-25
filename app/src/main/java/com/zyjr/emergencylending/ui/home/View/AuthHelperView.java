package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.AuthInfoBean;

/**
 * 提交认证信息回调
 * Created by neil on 2017/10/25
 */
public interface AuthHelperView {

    void onSuccessSubmit(String returnCode, AuthInfoBean bean);

    void onFail(String errorMessage);

}
