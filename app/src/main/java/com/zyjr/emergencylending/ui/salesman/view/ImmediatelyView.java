package com.zyjr.emergencylending.ui.salesman.view;

import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.ImmediateBean;

/**
 * @author wangyin
 * @date 2017/10/28.
 * @description :
 */

public interface ImmediatelyView {
    void preCheckBook(ImmediateBean baseBean);
    void onlineToOffline(BaseBean baseBean);
}
