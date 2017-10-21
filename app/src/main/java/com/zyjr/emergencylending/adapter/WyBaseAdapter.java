package com.zyjr.emergencylending.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 *
 * @author wangyin
 * @date 2017/5/22
 */

public class WyBaseAdapter extends BaseAdapter {
    public List mList;
    public LayoutInflater mInflater;
    protected Context mContext;

    public WyBaseAdapter(List list, Context context) {
        this.mList = list;
        mInflater = LayoutInflater.from(context);
        this.mContext=context;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
