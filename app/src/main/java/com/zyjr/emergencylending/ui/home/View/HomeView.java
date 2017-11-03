package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.Banner;
import com.zyjr.emergencylending.entity.EffectiveOrderBean;
import com.zyjr.emergencylending.entity.UserInfo;

/**
 * Created by wangyin on 2017/8/9.
 */

public interface HomeView {
    void getBanner(Banner banner);
    void getBasicInfo(UserInfo userInfo);
    void isEffectiveOrder(EffectiveOrderBean baseBean);
}
