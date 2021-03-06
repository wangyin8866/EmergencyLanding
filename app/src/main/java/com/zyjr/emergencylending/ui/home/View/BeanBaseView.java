package com.zyjr.emergencylending.ui.home.View;

/**
 * Created by neil on 2017/10/24
 * 备注:
 */
public interface BeanBaseView<T> {

    void onSuccessGet(String apiCode, T bean);

    void onSuccessAdd(String apiCode, String msg);

    void onSuccessEdit(String apiCode, String msg);

    void onFail(String apiCode, String errorMsg);

    void onError(String apiCode, String errorMsg);

}
