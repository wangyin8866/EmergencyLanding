package com.zyjr.emergencylending.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.utils.DateUtil;
import com.zyjr.emergencylending.utils.UIUtils;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyin on 2017/10/12.
 */

public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.et_mobile_number)
    EditText etMobileNumber;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.btn_login_code)
    Button btnLoginCode;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_pwd_again)
    EditText etNewPwdAgain;
    @BindView(R.id.btn_sure)
    Button btnSure;
    private String phone, inviteCode, pwd,pwdAgain, invite_code;
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

    @OnClick({R.id.btn_login_code, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_code:
                // TODO: 2017/10/14 调短信验证码的接口，下面代码写在成功后的回调中
                btnLoginCode.setEnabled(false);
                DateUtil.countDown(btnLoginCode,"重新发送");
                break;
            case R.id.btn_sure:
                phone = etMobileNumber.getText().toString().trim();
                inviteCode = etSmsCode.getText().toString().trim();
                pwd = etNewPwd.getText().toString().trim();
                pwdAgain = etNewPwdAgain.getText().toString().trim();
                if (TextUtils.isEmpty(phone) || !WYUtils.checkPhone(phone)) {
                    UIUtils.showToastCommon(mContext, "手机号输入错误");
                    return;
                } else if (TextUtils.isEmpty(inviteCode)) {
                    UIUtils.showToastCommon(mContext, "验证码不能为空");
                    return;
                } else if (TextUtils.isEmpty(pwd) ) {
                    UIUtils.showToastCommon(mContext, "密码不能为空");
                    return;
                } else if (!pwd.equals(pwdAgain)) {
                    UIUtils.showToastCommon(mContext, "密码不一致");
                    return;
                }
                // TODO: 2017/10/14 调找回密码接口
                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                break;
        }
    }
}
