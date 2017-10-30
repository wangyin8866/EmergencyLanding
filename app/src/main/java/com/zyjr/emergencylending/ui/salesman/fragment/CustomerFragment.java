package com.zyjr.emergencylending.ui.salesman.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.LineCustomerAdapter;
import com.zyjr.emergencylending.base.BaseFragment;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.CircleView;
import com.zyjr.emergencylending.custom.XListView;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.H5Bean;
import com.zyjr.emergencylending.ui.h5.H5WebView;
import com.zyjr.emergencylending.ui.salesman.activity.ActivityApplyFor;
import com.zyjr.emergencylending.ui.salesman.activity.ActivityClient;
import com.zyjr.emergencylending.ui.salesman.activity.ActivitySuccess;
import com.zyjr.emergencylending.ui.salesman.presenter.CustomerPresenter;
import com.zyjr.emergencylending.ui.salesman.view.CustomerView;
import com.zyjr.emergencylending.utils.LogUtils;

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

public class CustomerFragment extends BaseFragment<CustomerPresenter, CustomerView> implements CustomerView, XListView.IXListViewListener {
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
    @BindView(R.id.ll_rank_list)
    LinearLayout llRankList;
    @BindView(R.id.type1)
    TextView type1;
    @BindView(R.id.type2)
    TextView type2;
    @BindView(R.id.type3)
    TextView type3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_fragment_customer_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        LogUtils.e("onCreateView", "onCreateView");
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

        selectTv(1);
        circleClient.setData(1000, 1000);
        circleApplyFor.setData(800, 1000);
        circleSuccess.setData(500, 1000);
    }


    @Override
    protected CustomerPresenter createPresenter() {
        return new CustomerPresenter(mContext);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.circle_client, R.id.circle_apply_for, R.id.circle_success, R.id.ll_rank_list, R.id.type1, R.id.type2, R.id.type3})
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
            case R.id.ll_rank_list:
                H5WebView.skipH5WebView(mContext, "龙虎榜");
                break;
            case R.id.type1:
                selectTv(1);
                break;
            case R.id.type2:
                selectTv(2);
                break;
            case R.id.type3:
                selectTv(3);
                break;
        }
    }

    public void initTextView() {
        type1.setBackgroundResource(R.drawable.tv_type_shape_gradient_mine);
        type1.setTextColor(Color.parseColor("#FFFFFF"));
        type2.setBackgroundResource(R.drawable.tv_type_shape_gradient_mine);
        type2.setTextColor(Color.parseColor("#FFFFFF"));
        type3.setBackgroundResource(R.drawable.tv_type_shape_gradient_mine);
        type3.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public void selectTv(int currentTv) {
        initTextView();
        switch (currentTv) {
            case 1:
                type1.setBackgroundResource(R.drawable.tv_type_shape_gradient_mine_select);
                type1.setTextColor(Color.parseColor("#FFA200"));
                break;
            case 2:
                type2.setBackgroundResource(R.drawable.tv_type_shape_gradient_mine_select);
                type2.setTextColor(Color.parseColor("#FFA200"));
                break;
            case 3:
                type3.setBackgroundResource(R.drawable.tv_type_shape_gradient_mine_select);
                type3.setTextColor(Color.parseColor("#FFA200"));
                break;
        }
        mPresenter.myPerformance(NetConstantValues.MY_PERFORMANCE, currentTv + "");
        mPresenter.waitApply(NetConstantValues.WAIT_APPLY, currentTv + "");
        mPresenter.rankList(NetConstantValues.RANKING_LIST);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void myPerformance(BaseBean baseBean) {

    }

    @Override
    public void rankList(H5Bean baseBean) {

    }

    @Override
    public void waitApply(H5Bean baseBean) {

    }

}
