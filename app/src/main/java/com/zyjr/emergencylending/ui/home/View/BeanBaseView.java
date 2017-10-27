package com.zyjr.emergencylending.ui.home.View;

/**
 * Created by neil on 2017/10/24
 * 备注:
 */
public interface BeanBaseView<T> {

    void onSuccessGet(String returnCode, T bean);

    void onSuccessAdd(String returnCode, T bean);

    void onSuccessEdit(String returnCode, T bean);

    void onFail(String returnCode, String flag, String errorMsg);

    void onError(String returnCode, String errorMsg);

}
