package com.zyjr.emergencylending.ui.home.loan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/16
 * 备注: 处理失败页面 包含审核失败和 申请失败
 */

public class HandleFailActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.iv_apply_status_icon)
    ImageView ivApplyStatusIcon;
    @BindView(R.id.tv_apply_result)
    TextView tvApplyResult;
    @BindView(R.id.tv_apply_result_desc)
    TextView tvApplyResultDesc;
    @BindView(R.id.btn_apply_qunadai)
    Button btnApplyQunadai;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_handle_fail);
        ButterKnife.bind(this);
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });
    }

    @OnClick({R.id.btn_apply_qunadai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_apply_qunadai:  // 去哪贷

                break;
        }
    }

}
