package com.zyjr.emergencylending.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.entity.PrecheckResultBean;
import com.zyjr.emergencylending.widget.SelectorImageView;

import java.util.List;

/**
 * Created by neil on 2017/11/20
 * 备注: 续贷产品 适配器
 */
public class ReloanProductAdapter extends BaseQuickAdapter<PrecheckResultBean.LoanProduct, BaseViewHolder> {

    public ReloanProductAdapter(@LayoutRes int layoutResId, @Nullable List<PrecheckResultBean.LoanProduct> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, PrecheckResultBean.LoanProduct item) {
        viewHolder.setText(R.id.tv_item_loan_money, item.getLoan_amount() + "元");
        if ("1".equals(item.getLoan_unit())) {
            // 天
            viewHolder.setText(R.id.tv_item_loan_period, item.getLoan_spac() + "天");
        } else if ("2".equals(item.getLoan_unit())) {
            // 周
            viewHolder.setText(R.id.tv_item_loan_period, item.getLoan_periods() + "周");
        }
        SelectorImageView view = viewHolder.getView(R.id.iv_item_product_selected);
        if (item.getIs_selected()) {
            view.toggle(true);
        } else {
            view.toggle(false);
        }
    }

    public void setSelected(int position, boolean status) {
        for (PrecheckResultBean.LoanProduct reloanProductBean : mData) {
            reloanProductBean.setIs_selected(false);
        }
        mData.get(position).setIs_selected(status);
        notifyDataSetChanged();
    }

    public boolean isSelected(int position) {
        return mData.get(position).getIs_selected();
    }

    public PrecheckResultBean.LoanProduct getSelected(int position) {
        return mData.get(position) == null ? null : mData.get(position);
    }
}
