package com.zyjr.emergencylending.ui.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.GuideAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wy
 * @date 2016/9/9
 */
public class GuideAtActivity extends BaseActivity {
    private ViewPager mViewPager;
    private List<View> views = new ArrayList<>();
    private Button mButton;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_guide);
        initView();
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideAtActivity.this, SplashActivity.class));
                finish();

            }
        });
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.welcome_pager);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.guide1, null);
        View view2 = inflater.inflate(R.layout.guide2, null);
        View view3 = inflater.inflate(R.layout.guide3, null);

        mButton =view3.findViewById(R.id.btn_travel_join);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        mViewPager.setAdapter(new GuideAdapter(views));
    }


}
