package com.zyjr.emergencylending.ui.home.loan.offline;

import android.os.Bundle;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;

/**
 * Created by neil on 2017/10/14
 * 备注: 资料提交--无门店时页面,校验是否是首贷或续贷,申请确认
 */
public class NoStoreApplyConfirmActivity extends BaseActivity{

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_no_store_apply_confirm);
    }
}
