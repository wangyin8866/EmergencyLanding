package com.zyjr.emergencylending.ui.home.loan.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.DialogCustom;
import com.zyjr.emergencylending.utils.ToastAlone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/17
 * 备注: 芝麻信用认证
 */
public class ZhimaAuthActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_idcard_number)
    TextView tvIdcardNumber;
    @BindView(R.id.btn_authorise)
    Button btnAuthorise;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhima_auth);
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
        tvName.setText("张三");
        tvIdcardNumber.setText("123456789");
    }

    @OnClick({R.id.btn_authorise})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_authorise:
                ToastAlone.showLongToast(this, "前往授权");
                DialogCustom dialogCustom = new DialogCustom(this);
                dialogCustom.loanProductMatchInfo(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }, 3200 + "", 5 + "").show();
                break;
        }
    }
}



