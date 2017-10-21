package com.zyjr.emergencylending.ui.salesman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.LineSuccessAdapter;
import com.zyjr.emergencylending.base.BaseFragment;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author wangyin
 * @date 2017/10/20
 */

public class SuccessFragmentOffline extends BaseFragment implements XListView.IXListViewListener{
    @BindView(R.id.xlv)
    XListView xlv;
    Unbinder unbinder;
    private int pageNum = 1;
    private int pageSize;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success_offline, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        xlv.setXListViewListener(this);
        xlv.setPullRefreshEnable(true);
        List list = new ArrayList();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        xlv.setAdapter(new LineSuccessAdapter(list,mContext));
        return view;
    }

    private void init() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
