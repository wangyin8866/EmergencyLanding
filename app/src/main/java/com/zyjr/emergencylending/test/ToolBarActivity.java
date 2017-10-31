package com.zyjr.emergencylending.test;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;

/**
 * @author wangyin
 * @date 2017/10/17
 */

public abstract class ToolBarActivity extends BaseActivity {

    private ToolBarHelper mToolBarHelper;
    private Toolbar toolbar;
    public TextView toolbarTitle;
    private View toolBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        mToolBarHelper = new ToolBarHelper(this, layoutResID);
        toolbar = mToolBarHelper.getToolBar();
        toolBarView = mToolBarHelper.getView();
        setContentView(mToolBarHelper.getContentView());
        /*把 tool_bar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
    }

    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
        toolbarTitle = toolBarView.findViewById(R.id.title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
