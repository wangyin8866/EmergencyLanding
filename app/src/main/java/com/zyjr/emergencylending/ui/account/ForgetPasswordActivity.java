package com.zyjr.emergencylending.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.ui.account.presenter.ForgetPresenter;
import com.zyjr.emergencylending.utils.DateUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.UIUtils;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 *
 * @author wangyin
 * @date 2017/10/12
 */

public class ForgetPasswordActivity extends BaseActivity<ForgetPresenter, BaseView<BaseBean>> implements BaseView<BaseBean> {
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
    private String phone, inviteCode, pwd, pwdAgain, sms;
    private Subscription subscription;

    @Override
    protected ForgetPresenter createPresenter() {
        return new ForgetPresenter(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd_update);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        String title = getIntent().getStringExtra("title");
        topBar.setTitle(title);
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });

        //发送验证码按钮
        subscription = RxView.clicks(btnLoginCode).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                phone = etMobileNumber.getText().toString().trim();
                pwd = etNewPwd.getText().toString().trim();
                pwdAgain = etNewPwdAgain.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastAlone.showShortToast(mContext, "请输入手机号码");
                } else if (!WYUtils.checkPhone(phone)) {
                    ToastAlone.showShortToast(mContext, "请输入正确的手机号码");
                } else {
                    mPresenter.sendSMS(btnLoginCode,NetConstantValues.SMS, phone);
                }
            }
        });
        //提交按钮
        subscription = RxView.clicks(btnSure).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                phone = etMobileNumber.getText().toString().trim();
                pwd = etNewPwd.getText().toString().trim();
                pwdAgain = etNewPwdAgain.getText().toString().trim();
                sms = etSmsCode.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastAlone.showShortToast(mContext, "请输入手机号码");
                } else if (!WYUtils.checkPhone(phone)) {
                    ToastAlone.showShortToast(mContext, "请输入正确的手机号码");
                } else if (TextUtils.isEmpty(sms)) {
                    ToastAlone.showShortToast(mContext, "请输入验证码");
                } else if (TextUtils.isEmpty(pwd)) {
                    ToastAlone.showShortToast(mContext, "请输入新密码");
                } else if (!WYUtils.checkPass(pwd)) {
                    ToastAlone.showShortToast(mContext, "密码必须由6-16位字母和数字组成");
                } else if (TextUtils.isEmpty(pwdAgain)) {
                    ToastAlone.showShortToast(mContext, "请再次输入新密码");
                }else if (!pwd.equals(pwdAgain)) {
                    ToastAlone.showShortToast(mContext, "两次输入的密码不一致！");
                } else {
                    mPresenter.forgetPassword(NetConstantValues.REST_PASSWORD, phone, sms, pwd);
                }

            }
        });
    }

    @OnClick({R.id.btn_login_code, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_code:
                // TODO: 2017/10/14 调短信验证码的接口，下面代码写在成功后的回调中
                btnLoginCode.setEnabled(false);
                DateUtil.countDown(btnLoginCode, "重新发送");
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
                } else if (TextUtils.isEmpty(pwd)) {
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

    @Override
    public void getCommonData(BaseBean baseBean) {
        if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
            ToastAlone.showShortToast(mContext, "找回密码成功");
            startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            ToastAlone.showShortToast(mContext, baseBean.getMsg());
        }
    }

    @Override
    public void requestError() {

    }

    @Override
    public void requestSuccess() {

    }

    @Override
    protected void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }
}
