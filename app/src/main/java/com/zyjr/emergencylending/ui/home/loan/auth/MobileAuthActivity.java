package com.zyjr.emergencylending.ui.home.loan.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.utils.ToastAlone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/17
 * 备注: 手机运营商认证
 * 验证码 根据手机运营商 进行显示和隐藏
 */
public class MobileAuthActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.tv_mobile_number)
    TextView tvMobileNumber;
    @BindView(R.id.et_service_password)
    TextView etServicePassword;
    @BindView(R.id.ll_mobile_validate_code)
    LinearLayout llMobileValidateCode; // 手机验证码
    @BindView(R.id.cb_check)
    CheckBox cbCheck;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_auth);
        ButterKnife.bind(this);
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

    @OnClick({R.id.cb_check, R.id.iv_service_password,R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_check:
                if (cbCheck.isChecked()) {
                    ToastAlone.showLongToast(this, "勾选协议");
                } else {
                    ToastAlone.showLongToast(this, "未勾选");
                }
                break;
            case R.id.iv_service_password:
                final CustomerDialog customerDialog = new CustomerDialog(this);
                customerDialog.mobileAuthNotice(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.iv_close_pop:
                                customerDialog.dismiss();
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.btn_submit:

                break;
        }
    }

}
