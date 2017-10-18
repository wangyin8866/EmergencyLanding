package com.zyjr.emergencylending.ui.salesman.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.DialogCustom;
import com.zyjr.emergencylending.utils.ToastAlone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyin on 2017/10/18.
 */

public class ImmediatelyBorrowActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.get_verification_code)
    Button getVerificationCode;
    @BindView(R.id.next_step)
    Button nextStep;

    @Override
    protected BasePresenter createPresenter() {
        return null;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_activity_immediately_borrow);
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

    @OnClick({R.id.get_verification_code, R.id.next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_verification_code:
                break;
            case R.id.next_step:
                final DialogCustom dialogCustom = new DialogCustom(mContext);
                dialogCustom.borrowSkip(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.iv_close:
                                dialogCustom.dismiss();
                                break;
                            case R.id.btn_confirm_submit:
                                ToastAlone.showShortToast(mContext, "下一步！！！！");
                                break;
                        }
                    }
                }).show();
                break;
        }
    }
}
