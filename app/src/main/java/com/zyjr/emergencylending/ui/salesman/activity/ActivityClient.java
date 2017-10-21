package com.zyjr.emergencylending.ui.salesman.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.TabAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.ui.salesman.fragment.NewClientFragment;
import com.zyjr.emergencylending.ui.salesman.fragment.NonArrivalClientFragment;
import com.zyjr.emergencylending.utils.UIUtils;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangyin
 * @date 2017/10/21
 */

public class ActivityClient extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.line_tab_layout)
    TabLayout lineTabLayout;
    @BindView(R.id.line_view_pager)
    ViewPager lineViewPager;
    private List<String> titles;
    private List<Fragment> fragments;
    private int newCount=20;
    private int nonArrivalCount=123;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_activity_client);
        ButterKnife.bind(this);
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });
        init();
    }

    private void init() {
        titles = new ArrayList<>();
        titles.add("新用户 (" + newCount + "人)");
        titles.add("未到店 (" + nonArrivalCount + "人)");
        fragments = new ArrayList<>();

        NewClientFragment newClientFragment = new NewClientFragment();
        NonArrivalClientFragment nonArrivalClientFragment = new NonArrivalClientFragment();
        fragments.add(newClientFragment);
        fragments.add(nonArrivalClientFragment);
        lineViewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), fragments, titles));
        lineTabLayout.setupWithViewPager(lineViewPager);
        WYUtils.setIndicator(mContext, lineTabLayout, 10, 10);
        //添加分割线
        LinearLayout linearLayout = (LinearLayout) lineTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerPadding(UIUtils.dip2px(15));
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
    }
}
