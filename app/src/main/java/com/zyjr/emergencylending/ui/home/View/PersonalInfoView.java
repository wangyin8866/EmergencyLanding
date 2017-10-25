package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.IDCardBackBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;


/**
 * Created by neil on 2017/10/19
 * 备注: 工作信息回调
 */
public interface PersonalInfoView extends BeanBaseView<PersonalInfoBean> {

    void onSuccessFrontBean(String returnCode, IDCardFrontBean bean);

    void onSuccessBackBean(String returnCode, IDCardBackBean bean);

    void onSuccessUploadPic(String returnCode);

    void onFail(String errorMessage);

}
