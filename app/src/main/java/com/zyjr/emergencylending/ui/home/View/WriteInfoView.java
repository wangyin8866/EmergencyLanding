package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.WriteInfoBean;

/**
 * Created by neil on 2017/10/23
 * 备注:  用户借款信息
 */
public interface WriteInfoView {

    void onSuccessGet(String returnCode, WriteInfoBean bean);

    void onFail(String errorMessage);
}
