package com.zyjr.emergencylending.ui.home.loan.basicInfo;

import android.os.Bundle;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;

/**
 * Created by neil on 2017/10/12
 * 备注: 银行信息
 */
public class BankInfoActivity extends BaseActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bankcard);
    }

}
