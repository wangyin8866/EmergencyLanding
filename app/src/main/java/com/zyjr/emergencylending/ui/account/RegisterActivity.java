package com.zyjr.emergencylending.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.account.RegisterBean;
import com.zyjr.emergencylending.ui.account.presenter.RegisterPresenter;
import com.zyjr.emergencylending.utils.NetworkUtils;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * @author wangyin
 * @date 2017/10/12
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter, BaseView<RegisterBean>> implements BaseView<RegisterBean> {
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.btn_login_code)
    Button btnLoginCode;
    @BindView(R.id.et_invite_code)
    EditText etInviteCode;
    @BindView(R.id.cb_check)
    CheckBox cbCheck;
    @BindView(R.id.tv_agreed)
    TextView tvAgreed;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private boolean isShow;
    private String phone, inviteCode, pwd;
    private Subscription subscription;
    private String sms;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //处理协议
        WYUtils.processProtocol(mContext, tvAgreed);

        RxCompoundButton.checkedChanges(cbCheck).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    btnRegister.setEnabled(true);
                } else {
                    btnRegister.setEnabled(false);
                }
            }
        });
        //发送验证码按钮
        subscription = RxView.clicks(btnLoginCode).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                phone = etPhoneNumber.getText().toString().trim();
                pwd = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastAlone.showShortToast(mContext, "请输入手机号码");
                } else if (!WYUtils.checkPhone(phone)) {
                    ToastAlone.showShortToast(mContext, "请输入正确的手机号码");
                } else {
                    mPresenter.sendSMS(btnLoginCode, NetConstantValues.SMS, phone);
                }
            }
        });
        //注册按钮
        subscription = RxView.clicks(btnRegister).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                phone = etPhoneNumber.getText().toString().trim();
                inviteCode = etInviteCode.getText().toString().trim();
                pwd = etPassword.getText().toString().trim();
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
                } else {
                    mPresenter.register(NetConstantValues.REGISTER, phone, SPUtils.getString(mContext,Config.KEY_CLIENT_ID,""), sms, pwd, inviteCode, Constants.getPlatform(1), NetworkUtils.getIp(mContext), Constants.getDeviceCode());
                }
            }
        });

    }

    @OnClick({R.id.iv_close, R.id.iv_show_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_show_pwd:
                if (!isShow) {
                    //可见
                    ivShowPwd.setImageResource(R.mipmap.eye_on);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //不可见
                    ivShowPwd.setImageResource(R.mipmap.eye_off);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isShow = !isShow;
                etPassword.postInvalidate();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void callBack(RegisterBean registerBean) {
        if (Config.CODE_SUCCESS.equals(registerBean.getFlag())) {
            SPUtils.saveString(mContext, Config.KEY_JUID, registerBean.getResult().getJuid());
            ToastAlone.showShortToast(mContext, "注册成功");
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        } else {
            ToastAlone.showShortToast(mContext, registerBean.getMsg());
        }
    }
}
