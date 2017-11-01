package com.zyjr.emergencylending.ui.my.View;

import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.CardBean;

/**
 *
 * @author wangyin
 * @date 2017/8/9
 */

public interface MyView {
    void myCard(CardBean cardBean);
    void update(BaseBean baseBean);
}
