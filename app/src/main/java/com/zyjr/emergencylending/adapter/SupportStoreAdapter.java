package com.zyjr.emergencylending.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.entity.ProIntroduceBean;
import com.zyjr.emergencylending.entity.StoreBean;

import java.util.List;

/**
 * Created by neil on 2017/10/12
 * 备注: 支持门店 适配器
 */

public class SupportStoreAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> {

    public SupportStoreAdapter(@LayoutRes int layoutResId, @Nullable List<StoreBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreBean item) {
        helper.setText(R.id.tv_item_store_name, item.getStoreName());
        helper.setText(R.id.tv_item_store_address, item.getStoreAddress());
    }
}
