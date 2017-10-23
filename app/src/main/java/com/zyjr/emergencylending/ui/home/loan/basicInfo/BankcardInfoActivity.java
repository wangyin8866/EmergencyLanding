package com.zyjr.emergencylending.ui.home.loan.basicInfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.ui.home.loan.AddBankcardActivity;
import com.zyjr.emergencylending.utils.ToastAlone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 银行卡信息
 * 当没有银行卡时,管理 按钮消失;有银行卡信息时展示
 */
public class BankcardInfoActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.rl_add_bankcard)
    RelativeLayout rlAddBankcard;  // 添加银行卡布局
    @BindView(R.id.rl_edit_bankcard)
    RelativeLayout rlEditBankcard;  // 银行卡
    @BindView(R.id.iv_bankcard_icon)
    ImageView ivBankcardIcon; // 银行卡logo
    @BindView(R.id.tv_bankcard_name)
    TextView tvBankcardName;  // 银行卡名称
    @BindView(R.id.tv_bankcard_type)
    TextView tvBankcardType;  // 银行卡类型
    @BindView(R.id.tv_bankcard_number)
    TextView tvBankcardNumber; // 银行卡号码
    @BindView(R.id.root_refreshview)
    PullToRefreshScrollView pullToRefreshScrollView;

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
        showEditDialog();
        return true;
    }


    private void showEditDialog(){
        final CustomerDialog customerDialog = new CustomerDialog(BankcardInfoActivity.this);
        customerDialog.editBankcard(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_edit_bankcard:
                        customerDialog.dismiss();
                        showOperateConfirm();
                        break;
                    case R.id.tv_cancel:
                        ToastAlone.showLongToast(BankcardInfoActivity.this, "取消");
                        customerDialog.dismiss();
                        break;
                }
            }
        }).show();
    }

    private void showOperateConfirm(){
        final CustomerDialog customerDialog = new CustomerDialog(BankcardInfoActivity.this);
        customerDialog.operateComfirm(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.message_left:
                        ToastAlone.showLongToast(BankcardInfoActivity.this, "取消");
                        customerDialog.dismiss();

                        break;
                    case R.id.message_right:
                        ToastAlone.showLongToast(BankcardInfoActivity.this, "确认");
                        // TODO: 调编辑银行卡信息借口
                        customerDialog.dismiss();
                        rlEditBankcard.setVisibility(View.GONE);
                        rlAddBankcard.setVisibility(View.VISIBLE);
                        break;
                }
            }
        },"您确定要保存修改信息吗",0,"取消",0,"确定",0).show();
    }

    private void init(){
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {
                showEditDialog();
            }
        });

        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                pullToRefreshScrollView.onRefreshComplete();
                pullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                rlEditBankcard.setVisibility(View.GONE);
                rlAddBankcard.setVisibility(View.VISIBLE);
            }
        });
        topBar.setRightButtonVisible(View.INVISIBLE); //
    }

}
