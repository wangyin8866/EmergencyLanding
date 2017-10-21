package com.zyjr.emergencylending.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.ActivityCollector;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.utils.WYUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyin on 2017/10/13.
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
    private CustomerDialog dialog;

    @Override
    protected BasePresenter createPresenter() {
        return null;
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
        versionName.setText("V" + WYUtils.getAppVersionName(this));
    }

    @OnClick({R.id.modify_password, R.id.about_us, R.id.advice_feedback, R.id.service_call, R.id.exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.modify_password:

                break;
            case R.id.about_us:
                startActivity(new Intent(mContext, AboutUsActivity.class));
                break;
            case R.id.advice_feedback:
                startActivity(new Intent(mContext, AdviceFeedbackActivity.class));
                break;
            case R.id.service_call:
                phoneDialog();
                break;
            case R.id.exit:
                ActivityCollector.finishAll();
                break;
        }
    }
    private void phoneDialog() {
        dialog = new CustomerDialog(mContext);
        dialog.showHotLineDialog(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.cancel) {
                    dialog.dismiss();
                } else if (view.getId() == R.id.tv_title) {
                    WYUtils.callPhone(mContext, "400-077-6667");
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}
