package com.zyjr.emergencylending.ui.salesman.view;

import com.zyjr.emergencylending.entity.BaseBean;

/**
 * @author wangyin
 * @date 2017/10/28.
 * @description :
 */

public interface CustomerView {
    /**
     * 我的业绩
     * @param baseBean
     */
    void myPerformance(BaseBean baseBean);

    /**
     * 当月榜单
     * @param baseBean
     */
    void rankList(BaseBean baseBean);
    /**
     * 等待申请
     * @param baseBean
     */
    void waitApply(BaseBean baseBean);
}
