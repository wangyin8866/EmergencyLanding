package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.ContactInfoBean;


/**
 * Created by neil on 2017/10/19
 * 备注: 联系人回调
 */
public interface ContactInfoView extends BeanBaseView<ContactInfoBean>{

    void onFail(String errorMessage);

}
