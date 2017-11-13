package com.zyjr.emergencylending.ui.home.loan.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xfqz.xjd.mylibrary.CustomProgressDialog;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.entity.AuthInfoBean;
import com.zyjr.emergencylending.entity.MobileBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.entity.ZhimaAuthBean;
import com.zyjr.emergencylending.ui.home.View.AuthHelperView;
import com.zyjr.emergencylending.ui.home.presenter.AuthHelperPresenter;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/17
 * 备注: 手机运营商认证
 * 验证码 根据手机运营商 进行显示和隐藏
 */
public class MobileAuthActivity extends BaseActivity<AuthHelperPresenter, AuthHelperView> implements AuthHelperView {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.tv_mobile_number)
    TextView tvMobileNumber;
    @BindView(R.id.et_service_password)
    TextView etServicePassword;  // 运营商服务密码
    @BindView(R.id.ll_mobile_validate_code)
    LinearLayout llMobileValidateCode; // 手机验证码
    @BindView(R.id.et_mobile_validate_code)
    EditText etMobileValidateCode; // 手机验证码
    @BindView(R.id.cb_check)
    CheckBox cbCheck;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private MobileBean mobileBean = null; // status 1有效 0无效
    private String flag = "1";  // 1为普通提交，2为短信验证码提交

    @Override
    protected AuthHelperPresenter createPresenter() {
        return new AuthHelperPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_auth);
        ButterKnife.bind(this);
        init();
        judgeMobileCodeValide();
    }

    @OnClick({R.id.cb_check, R.id.iv_service_password, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_service_password:
                final CustomerDialog customerDialog = new CustomerDialog(this);
                customerDialog.mobileAuthNotice(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.iv_close_pop:
                                customerDialog.dismiss();
                                break;
                        }
                    }
                }).show();
                break;

            case R.id.btn_submit:
                validate(flag);
                break;
        }
    }

    private void validate(String flag) {
        String account = tvMobileNumber.getText().toString().trim();
        if (StringUtil.isEmpty(account)) {
            ToastAlone.showLongToast(this, "资料信息有误");
            return;
        }
        String servicePasswd = etServicePassword.getText().toString().trim();
        if (StringUtil.isEmpty(servicePasswd)) {
            ToastAlone.showLongToast(this, "请输入运营商服务密码");
            return;
        }
        String mobileValidateCode = etMobileValidateCode.getText().toString().trim();
        if (!cbCheck.isChecked()) {
            ToastAlone.showLongToast(this, "请确认阅读并同意");
            return;
        }
        if (flag.equals("1")) {
            submitMobileAuth(account, flag, servicePasswd, "");
        } else {
            if (StringUtil.isEmpty(mobileValidateCode)) {
                ToastAlone.showLongToast(this, "请输入验证码");
                return;
            }
            submitMobileAuth(account, flag, servicePasswd, mobileValidateCode);
        }

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
        tvMobileNumber.setText(SPUtils.getString(BaseApplication.getContext(), Config.KEY_PHONE, ""));
    }

    private void judgeMobileCodeValide() {
        Map<String, String> params = new HashMap<>();
        mPresenter.judgeMobileCodeValide(params);
    }

    /**
     * @param account  手机号
     * @param type     1为普通提交，2为短信验证码提交
     * @param password 服务密码
     * @param captcha  短信验证码
     */
    public void submitMobileAuth(String account, String type, String password, String captcha) {
        Map<String, String> params = new HashMap<>();
        params.put("auth_type", "1");
        params.put("account", account);
        params.put("type", type);
        params.put("password", password);
        params.put("captcha", captcha);
        mPresenter.submitMobileAuthInfo(params);
    }

    @Override
    public void onSuccessSubmit(String apiCode, String returnCode, String msg) {
        ToastAlone.showLongToast(this, msg);
        if (returnCode.equals("00040004")) {
            // 短信发送成功
            llMobileValidateCode.setVisibility(View.VISIBLE);
            flag = "2";
            etMobileValidateCode.setText("");
        } else if (returnCode.equals("API0000")) {
            // 提交成功
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } else if (returnCode.equals("API3020")) {
            // 需要返回第一步,重新开始操作
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onSuccessJudgeMobileValide(String apiCode, MobileBean bean) {
        mobileBean = bean;
//        if (mobileBean.getStatus().equals("0")) {
//            llMobileValidateCode.setVisibility(View.GONE);
//            flag = "1";
//        } else if (mobileBean.getStatus().equals("1")) {
//            llMobileValidateCode.setVisibility(View.VISIBLE);
//            flag = "2";
//            etMobileValidateCode.setText("");
//        }
    }

    @Override
    public void onFail(String apiCode, String failMsg) {
        ToastAlone.showLongToast(this, failMsg);
    }

    @Override
    public void onError(String apiCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
    }

    @Override
    public void onSuccessGetUserInfo(String apiCode, PersonalInfoBean bean) {

    }

    @Override
    public void onSuccessGetZhimaAuthUrl(String apiCode, ZhimaAuthBean bean) {

    }

    @Override
    public void onSuccessGetZhimaScore(String apiCode, String bean) {

    }

}
