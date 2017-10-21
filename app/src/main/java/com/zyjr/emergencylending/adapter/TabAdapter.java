package com.zyjr.emergencylending.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wangyin on 2017/6/27.
 */

public class TabAdapter extends FragmentPagerAdapter {
    private List<Fragment> mDatas;
    private List<String> titles;
    public TabAdapter(FragmentManager fm, List<Fragment> mDatas, List<String> titles) {
        super(fm);
        this.mDatas = mDatas;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
    //配置标题的方法
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
