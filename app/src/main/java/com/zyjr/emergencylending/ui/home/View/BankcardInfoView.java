package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.BankcardInfo;
import com.zyjr.emergencylending.entity.SupportBank;

import java.util.List;

/**
 * Created by neil on 2017/10/24
 * 备注: 银行卡相关回调
 */
public interface BankcardInfoView extends BeanBaseView<BankcardInfo> {

    void onSuccessGetSupportBanks(String returnCode, List<SupportBank> supportBanks);

}
