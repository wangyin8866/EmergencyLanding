package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;

import java.util.List;

/**
 * 获取认证信息回调|人脸识别后提交认证
 * Created by neil on 2017/10/25
 */
public interface AuthInfoView {

    void onSuccessGet(String apiCode, List<AuthInfoBean> beanList);

    void onSuccessSubmit(String apiCode, String msg);

    void onSuccessGetUserInfo(String apiCode, PersonalInfoBean bean);

    void onFail(String apiCode, String failMsg);

    void onError(String apiCode, String errorMsg);

}
