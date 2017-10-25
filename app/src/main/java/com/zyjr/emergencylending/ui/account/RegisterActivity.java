package com.zyjr.emergencylending.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.account.RegisterBean;
import com.zyjr.emergencylending.ui.account.presenter.RegisterPresenter;
import com.zyjr.emergencylending.ui.account.view.RegisterView;
import com.zyjr.emergencylending.utils.DateUtil;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.UIUtils;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by wangyin on 2017/10/12.
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
    private String phone, inviteCode, pwd, invite_code;

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
        RxCompoundButton.checkedChanges(cbCheck).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                LogUtils.e(aBoolean + "");
                if (aBoolean) {
                    btnRegister.setEnabled(true);
                } else {
                    btnRegister.setEnabled(false);
                }
            }
        });


//        String temp = "我已阅读并同意《出借风险告知书》《投资人服务协议》《借款协议》。";
        String temp = "我已阅读并同意急借通《用户使用及安全隐私协议》《用户信息授权服务协议》";
        mutilclick(temp);

    }

    //三个协议点击事件监控
    private void mutilclick(String str) {
        SpannableString spannableString = new SpannableString(str);//设置需要监听的字符串位置
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View v) {
                ToastAlone.showShortToast(mContext, "用户使用及安全隐私协议");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.front_text_color_agreement));
            }
        }, str.indexOf("用") - 1, str.indexOf("议") + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View v) {
                ToastAlone.showShortToast(mContext, "用户信息授权服务协议");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.front_text_color_agreement));
            }
        }, str.lastIndexOf("《"), str.lastIndexOf("》") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvAgreed.setText(spannableString);  //将处理过的数据set到View里
        tvAgreed.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick({R.id.iv_close, R.id.iv_show_pwd, R.id.btn_login_code, R.id.cb_check, R.id.btn_register})
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
            case R.id.btn_login_code:
                // TODO: 2017/10/14 调短信验证码的接口，下面代码写在成功后的回调中
                btnLoginCode.setEnabled(false);
                DateUtil.countDown(btnLoginCode, "重新发送");
                break;
            case R.id.cb_check:

                break;
            case R.id.btn_register:

                phone = etPhoneNumber.getText().toString().trim();
                inviteCode = etInviteCode.getText().toString().trim();
                pwd = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phone) || !WYUtils.checkPhone(phone) || TextUtils.isEmpty(pwd) || !WYUtils.checkPass(pwd)) {
                    UIUtils.showToastCommon(mContext, Config.TIP_ALL);
                    return;
                } else if (TextUtils.isEmpty(inviteCode)) {
                    UIUtils.showToastCommon(mContext, "验证码不能为空");
                    return;
                }
                // TODO: 2017/10/14 调注册接口
                mPresenter.fetch(NetConstantValues.REGISTER);

                break;
        }
    }



    @Override
    public void showData(RegisterBean registerBean) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}
