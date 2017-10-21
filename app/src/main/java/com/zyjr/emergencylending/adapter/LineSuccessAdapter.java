package com.zyjr.emergencylending.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyjr.emergencylending.R;

import java.util.List;

/**
 * @author wangyin
 * @date 2017/10/20
 */

public class LineSuccessAdapter extends WyBaseAdapter {
    private ViewHolder viewHolder;

    public LineSuccessAdapter(List list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_success, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView name, amount, date, deadline;

        ViewHolder(View view) {
            name = view.findViewById(R.id.name);
            amount = view.findViewById(R.id.amount);
            date = view.findViewById(R.id.date);
            deadline = view.findViewById(R.id.deadline);
        }
    }

}
