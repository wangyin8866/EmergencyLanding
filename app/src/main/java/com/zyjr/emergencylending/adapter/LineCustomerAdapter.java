package com.zyjr.emergencylending.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.entity.WaitApplyBean;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.List;

/**
 * @author wangyin
 * @date 2017/10/20
 */

public class LineCustomerAdapter extends WyBaseAdapter {
    private ViewHolder viewHolder;

    public LineCustomerAdapter(List list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WaitApplyBean.ResultBean.ClerkRecordListBean resultBean = (WaitApplyBean.ResultBean.ClerkRecordListBean) mList.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_customer, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(resultBean.getCust_name());
        if (TextUtils.isEmpty(resultBean.getLoan_amount())) {
            viewHolder.amount.setText("- -元");
        } else {
            viewHolder.amount.setText(resultBean.getLoan_amount() + "元");
        }
        if (TextUtils.isEmpty(resultBean.getLoan_period())) {
            viewHolder.deadline.setText("- -"+ resultBean.getLoan_periods_unit());
        } else {
            viewHolder.deadline.setText(resultBean.getLoan_period() + resultBean.getLoan_periods_unit());
        }
        viewHolder.status.setText(WYUtils.getWaitFollowStatus(Integer.valueOf(resultBean.getStep_status()), Integer.valueOf(resultBean.getOrder_status())));
        return convertView;
    }

    private static class ViewHolder {
        TextView name, amount, deadline, status;

        ViewHolder(View view) {
            name = view.findViewById(R.id.name);
            amount = view.findViewById(R.id.amount);
            deadline = view.findViewById(R.id.deadline);
            status = view.findViewById(R.id.status);
        }
    }


}
