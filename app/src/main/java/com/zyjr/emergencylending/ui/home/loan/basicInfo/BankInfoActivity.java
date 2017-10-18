package com.zyjr.emergencylending.ui.home.loan.basicInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import butterknife.OnItemLongClick;
import butterknife.OnLongClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 银行信息
 */
public class BankInfoActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.rl_add_bankcard)
    RelativeLayout rlAddBankcard;
    @BindView(R.id.rl_edit_bankcard)
    RelativeLayout rlEditBankcard;
    @BindView(R.id.iv_bankcard_icon)
    ImageView ivBankcardIcon; // 银行卡logo
    @BindView(R.id.tv_bankcard_name)
    TextView tvBankcardName;  // 银行卡名称
    @BindView(R.id.tv_bankcard_type)
    TextView tvBankcardType;  // 银行卡类型
    @BindView(R.id.tv_bankcard_number)
    TextView tvBankcardNumber; // 银行卡号码

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bankcard);
        ButterKnife.bind(this);
        init();
        tvBankcardNumber.setText("1234    4567    4567    7897");
    }

    @OnClick({R.id.rl_add_bankcard})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_add_bankcard:
                startActivity(new Intent(this, AddBankcardActivity.class));
                break;
        }
    }

    @OnLongClick(R.id.rl_edit_bankcard)
    boolean onLongClickBindButton(){
        showDeleteDialog();
        return true;
    }


    private void showDeleteDialog(){
        final DialogCustom dialogCustom = new DialogCustom(BankInfoActivity.this);
        dialogCustom.deleteBankcard(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_delete_bankcard:
                        ToastAlone.showLongToast(BankInfoActivity.this, "删除");
                        dialogCustom.dismiss();
                        // TODO: 调删除银行卡信息借口
                        rlEditBankcard.setVisibility(View.GONE);
                        rlAddBankcard.setVisibility(View.VISIBLE);
                        break;
                    case R.id.tv_cancel:
                        ToastAlone.showLongToast(BankInfoActivity.this, "取消");
                        dialogCustom.dismiss();
                        break;
                }
            }
        }).show();
    }

    private void init(){
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {
                showDeleteDialog();
            }
        });
    }

}