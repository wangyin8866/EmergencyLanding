package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.MayApplyProBean;
import com.zyjr.emergencylending.entity.StoreResultBean;
import com.zyjr.emergencylending.entity.WriteInfoBean;

import java.util.List;

/**
 * Created by neil on 2017/10/23
 * 备注:  用户借款信息
 */
public interface WriteInfoView {

    void onSuccessGet(String apiCode, WriteInfoBean bean);

    void onSuccessSubmit(String apiCode, String flag, String msg);

    void onSuccessGetMayApplyPro(String apiCode, MayApplyProBean bean);

    void onFail(String apiCode, String flag, String failMsg);

    void onError(String apiCode, String errorMsg);

    void onSuccessGetClerkStore(String apiCode, List<StoreResultBean.StoreBean> beanList);
}
