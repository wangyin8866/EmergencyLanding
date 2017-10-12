package com.zyjr.emergencylending.ui.home.loan;

import android.os.Bundle;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;

/**
 * Created by neil on 2017/10/12
 * 备注: 认证中心
 */
public class AuthCenterActivity extends BaseActivity {


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_center);
    }
}
