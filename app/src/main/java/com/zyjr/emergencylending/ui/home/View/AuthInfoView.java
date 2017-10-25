package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.AuthInfoBean;

import java.util.List;

/**
 * 获取认证信息回调|人脸识别后提交认证
 * Created by neil on 2017/10/25
 */
public interface AuthInfoView {

    void onSuccessGet(String returnCode, List<AuthInfoBean> beanList);

    void onSuccessSubmit(String returnCode, AuthInfoBean bean);

    void onFail(String errorMessage);

}
