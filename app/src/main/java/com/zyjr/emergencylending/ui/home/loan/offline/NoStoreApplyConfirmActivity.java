package com.zyjr.emergencylending.ui.home.loan.offline;

import android.os.Bundle;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.widget.CustomSeekBar;

import butterknife.BindView;

/**
 * Created by neil on 2017/10/14
 * 备注: 资料提交--无门店时页面,校验是否是首贷或续贷,申请确认
 */
public class NoStoreApplyConfirmActivity extends BaseActivity{

    @BindView(R.id.tv_loan_money)
    TextView tvLoanMoney; // 借款金额
    @BindView(R.id.tv_loan_period)
    TextView tvLoanPeriod; // 借款周期
    @BindView(R.id.seekbar_loan_money)
    CustomSeekBar seekbarMoney;
    @BindView(R.id.seekbar_loan_week)
    CustomSeekBar seekbarPeriod;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_no_store_apply_confirm);

        initGetData();
    }

    private void initGetData() {

    }


}
