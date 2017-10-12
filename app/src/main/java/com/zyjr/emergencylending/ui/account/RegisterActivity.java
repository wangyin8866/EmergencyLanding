package com.zyjr.emergencylending.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyin on 2017/10/12.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.et_phone_number)
    ClearEditText etPhoneNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @BindView(R.id.et_sms_code)
    ClearEditText etSmsCode;
    @BindView(R.id.btn_login_code)
    Button btnLoginCode;
    @BindView(R.id.et_invite_code)
    ClearEditText etInviteCode;
    @BindView(R.id.cb_check)
    CheckBox cbCheck;
    @BindView(R.id.tv_agreed)
    TextView tvAgreed;
    @BindView(R.id.tv_agreement_register)
    TextView tvAgreementRegister;
    @BindView(R.id.tv_agreement_infomation)
    TextView tvAgreementInfomation;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_close, R.id.iv_show_pwd, R.id.btn_login_code, R.id.cb_check, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_show_pwd:
                break;
            case R.id.btn_login_code:
                break;
            case R.id.cb_check:
                break;
            case R.id.btn_register:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                break;
        }
    }
}
