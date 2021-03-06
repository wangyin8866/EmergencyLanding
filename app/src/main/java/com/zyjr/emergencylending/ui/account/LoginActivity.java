package com.zyjr.emergencylending.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
import com.zyjr.emergencylending.custom.ClearEditText;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.account.LoginBean;
import com.zyjr.emergencylending.ui.account.presenter.LoginPresenter;
import com.zyjr.emergencylending.ui.salesman.activity.LineMainActivity;
import com.zyjr.emergencylending.utils.LogUtils;
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

public class LoginActivity extends BaseActivity<LoginPresenter, BaseView<LoginBean>> implements BaseView<LoginBean> {


    @BindView(R.id.et_phone_number)
    ClearEditText etPhoneNumber;
    @BindView(R.id.et_password)
    ClearEditText etPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.textView)
    TextView textView;
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
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("index", 0);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });
        //登录按钮
        subscription = RxView.clicks(btnLogin).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                phone = etPhoneNumber.getText().toString();
                pwd = etPassword.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastAlone.showShortToast(mContext, "请输入手机号码");
                } else if (!WYUtils.checkPhone(phone)) {
                    ToastAlone.showShortToast(mContext, "请输入正确的手机号码");
                } else if (TextUtils.isEmpty(pwd)) {
                    ToastAlone.showShortToast(mContext, "请输入密码");
                }
//                else if (!WYUtils.checkPass(pwd)) {
//                    ToastAlone.showShortToast(mContext, "密码由6-16位字母和数字组成");
//                }
                else {
                    LogUtils.e("tokenkey", WYUtils.getDeviceFingerprinting(mContext));
                    mPresenter.login(NetConstantValues.LOGIN, phone, pwd,
                            SPUtils.getWyString(mContext, Config.KEY_CLIENT_ID),
                            Constants.getNetIp(mContext), Constants.getPlatform(1),
                            Constants.getDeviceCode(), WYUtils.getDeviceFingerprinting(mContext)
                    );
                }
            }
        });
    }

    /**
     * 清楚数据
     */
    private void clearData() {
        SPUtils.clear(mContext);
    }

    @OnClick({R.id.iv_show_pwd, R.id.tv_forget, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {

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
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                intent.putExtra("title", "找回密码");
                intent.putExtra("type", "1");
                startActivity(intent);
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
    public void getCommonData(LoginBean loginBean) {
        if (Config.CODE_SUCCESS.equals(loginBean.getFlag())) {
            SPUtils.saveBoolean(mContext, Config.KEY_LOGIN);
            SPUtils.saveString(mContext, Config.KEY_TOKEN, loginBean.getResult().getLogin_token());
            SPUtils.saveString(mContext, Config.KEY_USER_TYPE, loginBean.getResult().getUser_type());
            SPUtils.saveString(mContext, Config.KEY_RECOMMEND_CODE, loginBean.getResult().getRecommendCode());
            SPUtils.saveString(mContext, Config.KEY_JUID, loginBean.getResult().getJuid());
            SPUtils.saveString(mContext, Config.KEY_PHONE, phone);
            BaseApplication.isSalesman = SPUtils.getString(mContext, Config.KEY_USER_TYPE, Config.USER_COMMON);
            if (Config.USER_SALESMAN.equals(loginBean.getResult().getUser_type())) {
                startActivity(new Intent(mContext, LineMainActivity.class));
            } else {
                startActivity(new Intent(mContext, MainActivity.class));
            }

        } else {
            ToastAlone.showShortToast(mContext, loginBean.getMsg());
        }
    }

    @Override
    public void requestError() {

    }

    @Override
    public void requestSuccess() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("index", 0);
            intent1.putExtras(bundle);
            startActivity(intent1);
            return true;
        }
        return false;
    }
}
