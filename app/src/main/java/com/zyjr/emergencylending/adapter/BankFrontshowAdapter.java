package com.zyjr.emergencylending.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.entity.SupportBank;

import java.util.List;

/**
 * Created by neil on 2017/10/12
 * 备注: 添加银行卡信息 展示
 */
public class BankFrontshowAdapter extends BaseQuickAdapter<SupportBank, BaseViewHolder> {

    public BankFrontshowAdapter(@LayoutRes int layoutResId, @Nullable List<SupportBank> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SupportBank item) {
        helper.setText(R.id.tv_bank_name, item.getName_());
    }
}
