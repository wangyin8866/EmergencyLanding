package com.zyjr.emergencylending.ui.repayment.View;

import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.entity.EffectiveOrderBean;
import com.zyjr.emergencylending.entity.H5Bean;
import com.zyjr.emergencylending.entity.MyBorrow;
import com.zyjr.emergencylending.entity.RepaymentSuccess;
import com.zyjr.emergencylending.entity.UserInfo;

/**
 * @author wangyin
 * @date 2017/8/9
 */

public interface RepaymentView extends BaseView {
    void getBorrowInfoByUserId(MyBorrow baseBean);

    void getRepaymentLogin(RepaymentSuccess baseBean);

    void getUserInfo(UserInfo userInfo);
    void loadH5(H5Bean h5Bean);
    void isEffectiveOrder(EffectiveOrderBean baseBean);
}
