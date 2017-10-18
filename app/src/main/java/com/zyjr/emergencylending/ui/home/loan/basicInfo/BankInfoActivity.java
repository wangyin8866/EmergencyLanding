package com.zyjr.emergencylending.ui.home.loan.basicInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.DialogCustom;
import com.zyjr.emergencylending.ui.home.loan.AddBankcardActivity;
import com.zyjr.emergencylending.ui.home.loan.SupportBankActivity;
import com.zyjr.emergencylending.utils.ToastAlone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 银行信息
 */
public class BankInfoActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.rl_add_bankcard)
    RelativeLayout rlAddBankcard;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bankcard);
        ButterKnife.bind(this);
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {
                final DialogCustom dialogCustom = new DialogCustom(BankInfoActivity.this);
                dialogCustom.deleteBankcard(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.tv_delete_bankcard:
                                ToastAlone.showLongToast(BankInfoActivity.this, "删除");
                                break;
                            case R.id.tv_cancel:
                                ToastAlone.showLongToast(BankInfoActivity.this, "取消");
                                dialogCustom.dismiss();
                                break;
                        }
                    }
                }).show();
            }
        });
    }

    @OnClick({R.id.rl_add_bankcard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_add_bankcard:
                startActivity(new Intent(this, AddBankcardActivity.class));
                break;
        }
    }

}
