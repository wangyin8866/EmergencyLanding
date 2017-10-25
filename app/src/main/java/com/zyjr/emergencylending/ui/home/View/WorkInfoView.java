package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.IDCardFrontBean;


/**
 * Created by neil on 2017/10/19
 * 备注: 工作信息回调
 */
public interface WorkInfoView extends BeanBaseView<IDCardFrontBean>{

    void onFail(String errorMessage);

}
