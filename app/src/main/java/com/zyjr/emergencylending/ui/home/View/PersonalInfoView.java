package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.IDCardBackBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;


/**
 * Created by neil on 2017/10/19
 * 备注: 工作信息回调
 */
public interface PersonalInfoView extends BeanBaseView<PersonalInfoBean> {

    void onSuccessFrontBean(String apiCode, IDCardFrontBean bean);

    void onSuccessBackBean(String apiCode, IDCardBackBean bean);

    void onSuccessUploadPic(String apiCode, String fileFlag, String msg);

}
