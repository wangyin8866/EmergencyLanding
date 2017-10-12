package com.zyjr.emergencylending.ui.account;

import android.os.Bundle;
import android.widget.Button;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.ClearEditText;
import com.zyjr.emergencylending.custom.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyin on 2017/10/12.
 */

public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.et_mobile_number)
    ClearEditText etMobileNumber;
    @BindView(R.id.et_sms_code)
    ClearEditText etSmsCode;
    @BindView(R.id.btn_login_code)
    Button btnLoginCode;
    @BindView(R.id.et_new_pwd)
    ClearEditText etNewPwd;
    @BindView(R.id.et_new_pwd_again)
    ClearEditText etNewPwdAgain;
    @BindView(R.id.btn_sure)
    Button btnSure;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd_update);
        ButterKnife.bind(this);
        init();
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
