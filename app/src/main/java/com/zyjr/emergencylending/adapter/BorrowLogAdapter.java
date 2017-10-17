package com.zyjr.emergencylending.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.entity.BorrowLog;

import java.util.List;

/**
 * Created by wangyin on 2017/10/16.
 */

public class BorrowLogAdapter extends BaseQuickAdapter<BorrowLog, BaseViewHolder> {

    public BorrowLogAdapter(@LayoutRes int layoutResId, @Nullable List<BorrowLog> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BorrowLog item) {
        helper.setText(R.id.item_borrow_amount, item.getAmount())
                .setText(R.id.item_borrow_deadline, item.getDeadline())
                .setText(R.id.item_borrow_date, item.getDate())
                .setText(R.id.item_borrow_status, item.getStatus());
    }
}
