package com.zyjr.emergencylending.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zyjr.emergencylending.MainActivity;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.account.LoginBean;
import com.zyjr.emergencylending.ui.account.presenter.LoginPresenter;
import com.zyjr.emergencylending.ui.salesman.activity.LineMainActivity;
import com.zyjr.emergencylending.utils.SPUtils;
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

public class LoginActivity extends BaseActivity<LoginPresenter, BaseView<LoginBean>> implements BaseView<LoginBean>{


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    private String phone;
    private String pwd;
    private boolean isShow;
    private Subscription subscription;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //登录按钮
        subscription = RxView.clicks(btnLogin).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                phone = etPhoneNumber.getText().toString();
                pwd = etPassword.getText().toString();
                if (TextUtils.isEmpty(phone) || !WYUtils.checkPhone(phone) || TextUtils.isEmpty(pwd) || !WYUtils.checkPass(pwd)) {
                    UIUtils.showToastCommon(mContext, Config.TIP_ALL);
                } else {
                    mPresenter.login(NetConstantValues.LOGIN, phone, pwd, BaseApplication.clientId, Constants.getNetIp(mContext), Constants.getPlatform(1), Constants.getDeviceCode()
                    );
                }
            }
        });
    }

    @OnClick({R.id.iv_close, R.id.iv_show_pwd, R.id.tv_forget, R.id.tv_register})
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
            case R.id.tv_forget:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

        }
    }


    @Override
    protected void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void callBack(LoginBean loginBean) {
        if (Config.CODE_SUCCESS.equals(loginBean.getFlag())) {
            SPUtils.saveString(mContext, Config.KEY_TOKEN, loginBean.getResult().getLogin_token());
            SPUtils.saveString(mContext, Config.KEY_USER_TYPE, loginBean.getResult().getUser_type());
            SPUtils.saveString(mContext, Config.KEY_RECOMMEND_CODE, loginBean.getResult().getRecommendCode());
            if (Config.USER_SALESMAN.equals(loginBean.getResult().getUser_type())) {
                startActivity(new Intent(mContext, LineMainActivity.class));
            } else {
                startActivity(new Intent(mContext, MainActivity.class));
            }

        } else {
            ToastAlone.showShortToast(mContext, loginBean.getMsg());
        }
    }


}
