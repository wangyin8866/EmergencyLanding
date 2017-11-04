package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;

import java.util.List;


/**
 * Created by neil on 2017/10/19
 * 备注: 联系人回调
 */
public interface ContactInfoView extends BeanBaseView<List<ContactInfoBean>> {

    void onSuccessGetPersonInfo(String apiCode, PersonalInfoBean bean);

}
