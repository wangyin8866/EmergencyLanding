package com.zyjr.emergencylending.ui.home.loan;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/16
 * 备注: 借款订单提交结果 只展示一次?
 */
public class LoanApplyResultActivity extends BaseActivity{

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_apply_result);
        ButterKnife.bind(this);

    }

}
