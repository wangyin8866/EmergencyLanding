package com.zyjr.emergencylending.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyjr.emergencylending.R;

import java.util.List;

/**
 * Created by wangyin on 2017/10/20.
 */

public class LineApplyForAdapter extends WyBaseAdapter {

    public LineApplyForAdapter(List list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_apply_for_offline, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView name, phone, applyStatus, applyDate, amount, deadline;

        ViewHolder(View view) {
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            amount = view.findViewById(R.id.amount);
            deadline = view.findViewById(R.id.deadline);
            applyStatus = view.findViewById(R.id.apply_status);
            applyDate = view.findViewById(R.id.apply_date);
        }
    }
}
