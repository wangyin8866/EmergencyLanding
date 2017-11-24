package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 业务员 录件结果
 *
 * @author neil
 * @date 2017/11/6
 */
public class ClerkApplyResultActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.iv_apply_result_icon)
    ImageView ivApplyResultIcon;
    @BindView(R.id.tv_apply_result)
    TextView tvApplyResult;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_apply_result);
        ButterKnife.bind(this);

        init();
        initGetData();

    }

    private void initGetData() {
        Intent intent = getIntent();
        String flag = intent.getStringExtra("flag");
        String errorMsg = intent.getStringExtra("msg");
        if ("API2022".equals(flag)) {
            tvApplyResult.setText(errorMsg);
            ivApplyResultIcon.setBackgroundResource(R.mipmap.emptypage_city);
        } else if ("API0000".equals(flag)) {
            tvApplyResult.setText("申请提交成功");
            ivApplyResultIcon.setBackgroundResource(R.mipmap.emptypage_success);
        }

    }

    private void init() {
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
}
