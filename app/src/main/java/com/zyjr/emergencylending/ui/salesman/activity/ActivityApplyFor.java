package com.zyjr.emergencylending.ui.salesman.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.TabAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.RxPopupSingleView;
import com.zyjr.emergencylending.entity.ActionItem;
import com.zyjr.emergencylending.ui.salesman.fragment.ApplyForFragmentOffline;
import com.zyjr.emergencylending.ui.salesman.fragment.ApplyForFragmentOnline;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.UIUtils;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wangyin
 * @date 2017/10/20
 */

public class ActivityApplyFor extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_mark)
    TextView tvMark;
    @BindView(R.id.line_tab_layout)
    TabLayout lineTabLayout;
    @BindView(R.id.line_view_pager)
    ViewPager lineViewPager;
    @BindView(R.id.iv_mark)
    ImageView ivMark;
    private List<String> titles;
    private List<Fragment> fragments;
    private RxPopupSingleView titlePopup;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_activity_apply_for);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        titles = new ArrayList<>();
        titles.add("线下");
        titles.add("线上");
        fragments = new ArrayList<>();
        ApplyForFragmentOffline appForFragmentOffline = new ApplyForFragmentOffline();
        ApplyForFragmentOnline appForFragmentOnline = new ApplyForFragmentOnline();
        fragments.add(appForFragmentOffline);
        fragments.add(appForFragmentOnline);
        lineViewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), fragments, titles));
        lineTabLayout.setupWithViewPager(lineViewPager);
        WYUtils.setIndicator(mContext, lineTabLayout, 10, 10);
        //添加分割线
        LinearLayout linearLayout = (LinearLayout) lineTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerPadding(UIUtils.dip2px(15));
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        lineTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    tvMark.setVisibility(View.GONE);
                    ivMark.setVisibility(View.GONE);
                } else {
                    tvMark.setVisibility(View.VISIBLE);
                    ivMark.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_mark})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_mark:
                initPopupView();
                titlePopup.show(tvMark, 0);
                break;
        }
    }

    private void initPopupView() {
        titlePopup = new RxPopupSingleView(mContext, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, R.layout.popupwindow_definition_layout);
        titlePopup.addAction(new ActionItem("全部状态"));
        titlePopup.addAction(new ActionItem("受理中"));
        titlePopup.addAction(new ActionItem("审核中"));
        titlePopup.addAction(new ActionItem("审核失败"));
        titlePopup.addAction(new ActionItem("放款中"));
        titlePopup.addAction(new ActionItem("放款失败"));
        titlePopup.addAction(new ActionItem("待领取"));
        titlePopup.addAction(new ActionItem("领取超时"));
        titlePopup.addAction(new ActionItem("放款成功"));
        titlePopup.addAction(new ActionItem("已结清"));
        titlePopup.setItemOnClickListener(new RxPopupSingleView.OnItemOnClickListener() {

            @Override
            public void onItemClick(ActionItem item, int position) {
                if (titlePopup.getAction(position).mTitle.equals(tvMark.getText())) {
                    ToastAlone.showShortToast(mContext, titlePopup.getAction(position).mTitle.toString());
                } else {
                    ToastAlone.showShortToast(mContext, titlePopup.getAction(position).mTitle.toString());
                }
            }
        });
    }
}
