package com.zyjr.emergencylending.ui.salesman.view;

import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.entity.MessageBean;
import com.zyjr.emergencylending.entity.NoticeBean;

/**
 * @author wangyin
 * @date 2017/10/28.
 * @description :
 */

public interface MessageView extends BaseView<NoticeBean> {
    void getMessage(MessageBean messageBean);
}
