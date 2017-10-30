package com.zyjr.emergencylending.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.entity.BankBean;
import com.zyjr.emergencylending.entity.ProIntroduceBean;

import java.util.List;

/**
 * Created by neil on 2017/10/12
 * 备注: 产品描述 适配器
 */

public class ProIntroduceAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ProIntroduceAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String info) {
        helper.setText(R.id.tv_item_product_desc, info);

    }
}
