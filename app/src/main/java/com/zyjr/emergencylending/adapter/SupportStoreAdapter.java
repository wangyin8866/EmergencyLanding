package com.zyjr.emergencylending.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.entity.StoreResultBean;
import com.zyjr.emergencylending.widget.SelectorImageView;

import java.util.List;

/**
 * Created by neil on 2017/10/12
 * 备注: 支持门店 适配器
 */

public class SupportStoreAdapter extends BaseQuickAdapter<StoreResultBean.StoreBean, BaseViewHolder> {

    public SupportStoreAdapter(@LayoutRes int layoutResId, @Nullable List<StoreResultBean.StoreBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, StoreResultBean.StoreBean item) {
        viewHolder.setText(R.id.tv_item_store_name, item.getStoreName());
        viewHolder.setText(R.id.tv_item_store_address, item.getStoreAddr());
        SelectorImageView view = viewHolder.getView(R.id.iv_item_store_selected);
        if (item.getSelected()) {
            view.toggle(true);
        } else {
            view.toggle(false);
        }
    }

    public void setSelected(int position, boolean status) {
        for (StoreResultBean.StoreBean storeBean : mData) {
            storeBean.setSelected(false);
        }
        mData.get(position).setSelected(status);
        notifyDataSetChanged();
    }

    public boolean isSelected(int position){
        return mData.get(position).getSelected();
    }

    public StoreResultBean.StoreBean getSelected(int position) {
        return mData.get(position) == null ? null : mData.get(position);
    }
}
