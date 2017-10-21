package com.zyjr.emergencylending.ui.salesman.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.LineCustomerAdapter;
import com.zyjr.emergencylending.base.BaseFragment;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.CircleView;
import com.zyjr.emergencylending.custom.XListView;
import com.zyjr.emergencylending.ui.salesman.activity.ActivityApplyFor;
import com.zyjr.emergencylending.ui.salesman.activity.ActivityClient;
import com.zyjr.emergencylending.ui.salesman.activity.ActivitySuccess;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author wangyin
 * @date 2017/8/9
 */

public class CustomerFragment extends BaseFragment implements XListView.IXListViewListener {
    @BindView(R.id.rg_title)
    RadioGroup rgTitle;
    @BindView(R.id.circle_client)
    CircleView circleClient;
    @BindView(R.id.circle_apply_for)
    CircleView circleApplyFor;
    @BindView(R.id.circle_success)
    CircleView circleSuccess;
    Unbinder unbinder;
    @BindView(R.id.bg_line_1)
    View bgLine1;
    @BindView(R.id.xlv)
    XListView xlv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_fragment_customer_main, container, false);
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
        xlv.setAdapter(new LineCustomerAdapter(list, mContext));
        return view;
    }

    private void init() {
        circleClient.setData(1000, 1000);
        circleApplyFor.setData(800, 1000);
        circleSuccess.setData(500, 1000);
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.circle_client, R.id.circle_apply_for, R.id.circle_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circle_client:
                startActivity(new Intent(mContext, ActivityClient.class));
                break;
            case R.id.circle_apply_for:
                startActivity(new Intent(mContext, ActivityApplyFor.class));
                break;
            case R.id.circle_success:
                startActivity(new Intent(mContext, ActivitySuccess.class));
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
