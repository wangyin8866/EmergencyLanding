package com.zyjr.emergencylending.ui.my.View;

import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.entity.MyBorrow;

/**
 * @author wangyin
 * @date 2017/11/21.
 * @description :
 */

public interface MyBorrowView extends BaseView<MyBorrow>{
    void getMoreData(MyBorrow myBorrow);
}
