package com.zyjr.emergencylending.ui.my.View;

import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.EffectiveOrderBean;
import com.zyjr.emergencylending.entity.H5Bean;
import com.zyjr.emergencylending.entity.MyBorrow;
import com.zyjr.emergencylending.entity.RepaymentSuccess;
import com.zyjr.emergencylending.entity.UserInfo;

/**
 *
 * @author wangyin
 * @date 2017/8/9
 */

public interface MyView extends BaseView{

    void getUserInfo(UserInfo userInfo);

    void update(BaseBean baseBean);

    void getBorrowInfoByUserId(MyBorrow baseBean);

    void getRepaymentLogin(RepaymentSuccess baseBean);

    void isEffectiveOrder(EffectiveOrderBean baseBean);

    void loadRepayH5(H5Bean h5Bean);
}
