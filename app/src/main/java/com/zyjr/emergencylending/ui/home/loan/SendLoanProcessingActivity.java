package com.zyjr.emergencylending.ui.home.loan;

import android.os.Bundle;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/16
 * 备注: 放款中
 */

public class SendLoanProcessingActivity extends BaseActivity{


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_loan_processing);
        ButterKnife.bind(this);
    }
}
