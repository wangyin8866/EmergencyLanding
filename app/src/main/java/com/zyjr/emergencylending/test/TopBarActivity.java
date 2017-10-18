package com.zyjr.emergencylending.test;

import android.os.Bundle;

import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.custom.TopBar;

/**
 * @author wangyin
 * @date 2017/10/17
 */

public abstract class TopBarActivity extends BaseActivity {

    public TopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        TopBarHelper mTopBarHelper = new TopBarHelper(this, layoutResID);
        topBar = mTopBarHelper.getTopBar();

        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });
        setContentView(mTopBarHelper.getContentView());
    }


}
