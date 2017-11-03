package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.WriteInfoBean;

/**
 * 线下件 页面回调
 *
 * @author neil
 * @date 2017/11/2
 */
public interface OfflineApplyView {

    void onSuccessGetLocalStoreList(String apiCode, WriteInfoBean bean);

    void onSuccessSubmit(String apiCode, String msg);

    void onFail(String apiCode, String errorMsg);

    void onError(String apiCode, String errorMsg);

}
