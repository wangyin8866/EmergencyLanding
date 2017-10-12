package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.ui.home.MessageActivity;
import com.zyjr.emergencylending.ui.home.QrCodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/11
 * 备注: 借款(急速 和传统 )
 */
public class LoanMainActivity extends BaseActivity {

    @BindView(R.id.btn_apply_quickly)
    Button btnApplyQuicky;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_main);
        ButterKnife.bind(this);
//        init();

    }

    @OnClick({R.id.btn_apply_quickly})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_apply_quickly:
                startActivity(new Intent(LoanMainActivity.this, WriteInfoMainActivity.class));
                break;
        }
    }
}
