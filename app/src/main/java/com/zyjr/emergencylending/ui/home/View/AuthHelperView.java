package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.entity.ZhimaAuthBean;

/**
 * 提交认证信息回调
 * Created by neil on 2017/10/25
 */
public interface AuthHelperView {

    void onSuccessSubmit(String apiCode, AuthInfoBean bean);

    void onSuccessGetUserInfo(String apiCode, PersonalInfoBean bean);

    void onSuccessGetZhimaAuthUrl(String apiCode, ZhimaAuthBean bean);

    void onSuccessGetZhimaScore(String apiCode, String bean);

    void onFail(String apiCode, String failMsg);

    void onError(String apiCode, String errorMsg);

}
