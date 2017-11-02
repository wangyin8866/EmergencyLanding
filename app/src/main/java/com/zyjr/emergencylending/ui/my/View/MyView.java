package com.zyjr.emergencylending.ui.my.View;

import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.UserInfo;

/**
 *
 * @author wangyin
 * @date 2017/8/9
 */

public interface MyView {
    void getUserInfo(UserInfo userInfo);
    void update(BaseBean baseBean);
}
