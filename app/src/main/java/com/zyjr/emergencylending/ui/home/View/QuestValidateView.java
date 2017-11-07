package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.ContactInfoBean;

import java.util.List;

/**
 * 问题验证
 * @author neil
 * @date 2017/11/6
 */
public interface QuestValidateView {

    void onSuccessGetContactInfo(String apiCode, List<ContactInfoBean> contactInfoList);

    void onSuccessSubmit(String apiCode, String msg);

    void onFail(String apiCode, String failMsg);

    void onError(String apiCode, String errorMsg);
}
