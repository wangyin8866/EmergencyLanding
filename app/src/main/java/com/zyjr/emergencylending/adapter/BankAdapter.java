package com.zyjr.emergencylending.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.entity.BankBean;

import java.util.List;

/**
 * Created by neil on 2017/10/12
 * 备注:
 */

public class BankAdapter extends BaseQuickAdapter<BankBean, BaseViewHolder> {

    public BankAdapter(@LayoutRes int layoutResId, @Nullable List<BankBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankBean item) {
        if (item.getBankName().contains("中国工商")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.ic_launcher);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if(item.getBankName().contains("中国农业")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.ic_launcher);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        }

    }
}
