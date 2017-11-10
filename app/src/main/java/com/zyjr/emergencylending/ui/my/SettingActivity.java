package com.zyjr.emergencylending.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.ui.account.ForgetPasswordActivity;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wangyin
 * @date 2017/10/13
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.modify_password)
    TextView modifyPassword;
    @BindView(R.id.about_us)
    TextView aboutUs;
    @BindView(R.id.advice_feedback)
    TextView adviceFeedback;
    @BindView(R.id.service_call)
    RelativeLayout serviceCall;
    @BindView(R.id.exit)
    TextView exit;
    @BindView(R.id.version_name)
    TextView versionName;
    @BindView(R.id.version)
    RelativeLayout version;
    private CustomerDialog dialog;


    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter(SettingActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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

        init();
    }

    private void init() {
        versionName.setText("V" + WYUtils.getAppVersionName(this));
        dialog = new CustomerDialog(mContext);
    }

    @OnClick({R.id.modify_password, R.id.about_us, R.id.advice_feedback, R.id.service_call, R.id.exit, R.id.version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.modify_password:
                Intent intent = new Intent(mContext, ForgetPasswordActivity.class);
                intent.putExtra("title", "修改密码");
                startActivity(intent);
                break;
            case R.id.about_us:
                mPresenter.getH5Url(Config.H5_URL_ABOUTUS, "关于我们");
                break;
            case R.id.advice_feedback:
                mPresenter.getH5Url(Config.H5_URL_FEEDBACK, "意见反馈");
                break;
            case R.id.service_call:
                phoneDialog();
                break;
            case R.id.exit:
                SPUtils.clear(BaseApplication.getContext());

                break;
            case R.id.version:
                dialog.versionUpdate(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.cancel:
                                dialog.dismiss();
                                break;
                            case R.id.update:
                                dialog.dismiss();
                                WYUtils.upDateVersion(mContext, NetConstantValues.VERSION_UPDATE);
                                break;
                        }
                    }
                }, "版本更新！").show();
                break;
        }
    }

    private void phoneDialog() {

        dialog.showHotLineDialog(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.left) {
                    dialog.dismiss();
                } else if (view.getId() == R.id.right) {
                    WYUtils.callPhone(mContext, "400-077-6667");
                    dialog.dismiss();
                }
            }
        }).show();
    }
}
