package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.PrecheckResultBean;
import com.zyjr.emergencylending.entity.StoreResultBean;

import java.util.List;

/**
 * 线下件 页面回调
 *
 * @author neil
 * @date 2017/11/2
 */
public interface OfflineApplyView {

    void onSuccessGetLocalStoreList(String apiCode, List<StoreResultBean.StoreBean> beanList);

    void onSuccessSubmit(String apiCode, String msg);

    void onSuccessPrecheck(String apiCode, String flag, PrecheckResultBean precheckResultBean);

    void onFail(String apiCode, String flag, String failMsg);

    void onError(String apiCode, String errorMsg);

}
