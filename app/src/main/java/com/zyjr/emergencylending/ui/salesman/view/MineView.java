package com.zyjr.emergencylending.ui.salesman.view;

import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.H5Bean;

/**
 * @author wangyin
 * @date 2017/10/28.
 * @description :
 */

public interface MineView  {
    /**
     * 我的收入
     * @param baseBean
     */
    void myIncome(BaseBean baseBean);

    /**
     * 帮助说明
     * @param baseBean
     */
    void helpPage(H5Bean baseBean);
}
