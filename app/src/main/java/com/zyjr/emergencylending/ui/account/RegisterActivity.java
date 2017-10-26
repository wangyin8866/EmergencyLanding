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
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.account.RegisterBean;
import com.zyjr.emergencylending.ui.account.presenter.RegisterPresenter;
import com.zyjr.emergencylending.ui.account.view.RegisterView;
import com.zyjr.emergencylending.utils.DateUtil;
import com.zyjr.emergencylending.utils.NetworkUtils;
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

public class RegisterActivity extends BaseActivity<RegisterPresenter, RegisterView> implements RegisterView {
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
                if (TextUtils.isEmpty(phone) || !WYUtils.checkPhone(phone) || TextUtils.isEmpty(pwd) || !WYUtils.checkPass(pwd)) {
                    UIUtils.showToastCommon(mContext, Config.TIP_ALL);
                } else {
                    mPresenter.sendSMS(NetConstantValues.SMS, phone, Constants.getAppType(1), Constants.getVersionCode(mContext));
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
                if (TextUtils.isEmpty(phone) || !WYUtils.checkPhone(phone) || TextUtils.isEmpty(pwd) || !WYUtils.checkPass(pwd)||TextUtils.isEmpty(sms)) {
                    UIUtils.showToastCommon(mContext, Config.TIP_ALL);
                }  else {
                    mPresenter.register(NetConstantValues.REGISTER,phone, BaseApplication.clientId,sms,pwd,inviteCode,Constants.getAppType(1), NetworkUtils.getIp(mContext),Constants.getDeviceCode());
                }
            }
        });

    }

    @OnClick({R.id.iv_close, R.id.iv_show_pwd, R.id.cb_check, R.id.btn_register})
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
    public void showData(RegisterBean registerBean) {

        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    @Override
    public void getSendSMS(BaseBean baseBean) {
        if (baseBean.getFlag().equals(Config.TIP_SUCCESS)) {
            btnLoginCode.setEnabled(false);
            DateUtil.countDown(btnLoginCode, "重新发送");
        } else {
            ToastAlone.showShortToast(mContext, baseBean.getMsg());
        }
    }
    @Override
    protected void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }
}
