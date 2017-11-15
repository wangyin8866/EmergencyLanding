package com.zyjr.emergencylending.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.entity.MyBorrow;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.List;

/**
 * Created by wangyin on 2017/10/16.
 */

public class BorrowLogAdapter extends BaseQuickAdapter<MyBorrow.ResultBean.HisBorrowListBean, BaseViewHolder> {

    public BorrowLogAdapter(@LayoutRes int layoutResId, @Nullable List<MyBorrow.ResultBean.HisBorrowListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyBorrow.ResultBean.HisBorrowListBean item) {
        helper.setText(R.id.item_borrow_amount, item.getLoan_amount())
                .setText(R.id.item_borrow_deadline, item.getLoan_period() + "周")
                .setText(R.id.item_borrow_date, item.getLoan_time())
                .setText(R.id.item_borrow_status, WYUtils.getMyBorrowStatus(Integer.parseInt(item.getStep_status()), Integer.parseInt(item.getLoan_status())));
    }
}
