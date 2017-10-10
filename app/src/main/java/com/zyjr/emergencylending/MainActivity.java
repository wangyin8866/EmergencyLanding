package com.zyjr.emergencylending;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.zyjr.emergencylending.base.ActivityCollector;
import com.zyjr.emergencylending.custom.NoScrollViewPager;
import com.zyjr.emergencylending.ui.home.HomeFragment;
import com.zyjr.emergencylending.ui.my.MyFragment;
import com.zyjr.emergencylending.ui.repayment.RepaymentFragment;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wangyin on 2017/5/16.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.id_tab_ll_01)
    LinearLayout idTabLl01;
    @BindView(R.id.id_tab_ll_02)
    LinearLayout idTabLl02;
    @BindView(R.id.id_tab_ll_03)
    LinearLayout idTabLl03;
    @BindView(R.id.id_main_viewPager)
    NoScrollViewPager idMainViewPager;
    @BindView(R.id.id_tab_iv_01)
    ImageView idTabIv01;
    @BindView(R.id.id_tab_tv_01)
    TextView idTabTv01;
    @BindView(R.id.id_tab_iv_02)
    ImageView idTabIv02;
    @BindView(R.id.id_tab_tv_02)
    TextView idTabTv02;
    @BindView(R.id.id_tab_iv_03)
    ImageView idTabIv03;
    @BindView(R.id.id_tab_tv_03)
    TextView idTabTv03;
    private HomeFragment homeFragment;
    private RepaymentFragment repaymentFragment;
    private MyFragment myFragment;
    private List<Fragment> fragments;
    public static int currentPage=0;
    private static final String TAG = "MainActivity";
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();


    }

    private void init() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true).init();
        ActivityCollector.addActivity(this);

        fragments = new ArrayList<>();

        homeFragment = new HomeFragment();
        repaymentFragment = new RepaymentFragment();
        myFragment = new MyFragment();
        fragments.add(homeFragment);
        fragments.add(repaymentFragment);
        fragments.add(myFragment);

        idMainViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        idTabLl01.setOnClickListener(this);
        idTabLl02.setOnClickListener(this);
        idTabLl03.setOnClickListener(this);

        setTabSelection(currentPage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_tab_ll_01:
                currentPage = 0;
                setTabSelection(currentPage);
                break;
            case R.id.id_tab_ll_02:
                currentPage = 1;
                setTabSelection(currentPage);
                break;
            case R.id.id_tab_ll_03:
                currentPage = 2;
                setTabSelection(currentPage);
                break;
        }
    }


    private void setTabSelection(int currentPage) {

        //选中前清除状态
        restView();
        switch (currentPage) {
            case 0://未登录
                idTabIv01.setImageResource(R.mipmap.ic_home_checked);
                idTabTv01.setTextColor(getResources().getColor(R.color.tv_navigate_checked));
                switchPager(currentPage);
                break;
            case 1:
                idTabIv02.setImageResource(R.mipmap.ic_product_checked);
                idTabTv02.setTextColor(getResources().getColor(R.color.tv_navigate_checked));
                switchPager(currentPage);
                break;
            case 2:
                idTabIv03.setImageResource(R.mipmap.ic_my_checked);
                idTabTv03.setTextColor(getResources().getColor(R.color.tv_navigate_checked));
                switchPager(currentPage);
                break;
        }
    }

    private void switchPager(int currentPage) {
        idMainViewPager.setCurrentItem(currentPage, false);
    }

    /**
     * 重置所有状态
     */
    private void restView() {
        idTabIv01.setImageResource(R.mipmap.ic_home);
        idTabIv02.setImageResource(R.mipmap.ic_product);
        idTabIv03.setImageResource(R.mipmap.ic_my);

        idTabTv01.setTextColor(getResources().getColor(R.color.tv_navigate));
        idTabTv02.setTextColor(getResources().getColor(R.color.tv_navigate));
        idTabTv03.setTextColor(getResources().getColor(R.color.tv_navigate));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAG, currentPage);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPage = savedInstanceState.getInt(TAG);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WYUtils.clickBack(keyCode, event, MainActivity.this);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImmersionBar.destroy();
    }
}
