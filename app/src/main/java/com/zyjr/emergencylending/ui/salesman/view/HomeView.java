package com.zyjr.emergencylending.ui.salesman.view;

import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.entity.BaseBean;

/**
 * @author wangyin
 * @date 2017/10/28.
 * @description :
 */

public interface HomeView extends BaseView<BaseBean> {
    /**
     * 我的名片
     * @param baseBean
     */
    void myCard(BaseBean baseBean);

    /**
     * 活动
     * @param baseBean
     */
    void getActivity(BaseBean baseBean);
}
