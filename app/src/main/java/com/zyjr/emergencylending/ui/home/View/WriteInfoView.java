package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.WriteInfoBean;

/**
 * Created by neil on 2017/10/23
 * 备注:  用户借款信息
 */
public interface WriteInfoView {

    void onSuccessGet(String apiCode, WriteInfoBean bean);

    void onSuccessSubmit(String apiCode, String msg);

    void onFail(String apiCode, String flag, String errorMsg);

    void onError(String apiCode, String errorMsg);
}
