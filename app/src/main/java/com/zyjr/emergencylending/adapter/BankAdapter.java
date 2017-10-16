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
        if (item.getBankName().contains("中国工商") || item.getBankName().contains("工商")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_a);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if (item.getBankName().contains("中国农业")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_b);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if (item.getBankName().contains("中国银行")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_c);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if (item.getBankName().contains("中国建设银行") || item.getBankName().contains("建设")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_d);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if (item.getBankName().contains("中国交通银行") || item.getBankName().contains("交通")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_e);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if (item.getBankName().contains("光大银行") || item.getBankName().contains("光大")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_f);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if (item.getBankName().contains("中信银行") || item.getBankName().contains("中信")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_g);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if (item.getBankName().contains("兴业银行") || item.getBankName().contains("兴业")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_h);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if (item.getBankName().contains("华夏银行") || item.getBankName().contains("华夏")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_i);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if (item.getBankName().contains("平安银行") || item.getBankName().contains("平安")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_j);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if (item.getBankName().contains("广东发展银行") || item.getBankName().contains("广东发展")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_k);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if (item.getBankName().contains("中国民生银行") || item.getBankName().contains("民生")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_l);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else if (item.getBankName().contains("上海浦东发展银行") || item.getBankName().contains("浦东发展")) {
            helper.setImageResource(R.id.iv_bank_icon, R.mipmap.banklogo_m);
            helper.setText(R.id.tv_bank_name, item.getBankName());
        } else {
            helper.setText(R.id.tv_bank_name, item.getBankName());
        }

    }
}
