package com.zyjr.emergencylending.ui.home.View;

import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.MessageBean;

/**
 * @author wangyin
 * @date 2017/10/28.
 * @description :
 */

public interface MessageView  {
    void getMessage(MessageBean messageBean);
    void getMessageMore(MessageBean messageBean);
    void updateMessage(BaseBean baseBean);
}
