package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.BankcardInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.ContactInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.PersonalInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.WorkInfoActivity;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.widget.SettingItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 填写资料(包含个人信息.工作信息)
 */
public class WriteInfoMainActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.layout_personal_info)
    SettingItemView layoutPersonalInfo; // 个人信息
    @BindView(R.id.layout_work_info)
    SettingItemView layoutWorkInfo; // 工作信息
    @BindView(R.id.layout_contact_info)
    SettingItemView layoutContactInfo; // 联系人
    @BindView(R.id.layout_bank_info)
    SettingItemView layoutBankInfo; // 银行卡信息
    @BindView(R.id.btn_submit)
    Button btnSubmit; // 提交信息
    @BindView(R.id.rl_no_salesman)
    RelativeLayout rlNoSalesman;
    @BindView(R.id.root_refreshview)
    PullToRefreshScrollView pullToRefreshScrollView;

    private Intent intent;
    private String loanMoney = ""; // 借款金额
    private String loanWeek = ""; // 借款周期

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_infomation_main);
        ButterKnife.bind(this);

        init();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BaseApplication.isSalesman) {
            //线下
            rlNoSalesman.setVisibility(View.GONE);

        } else {
            rlNoSalesman.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.layout_personal_info, R.id.layout_work_info, R.id.layout_contact_info, R.id.layout_bank_info, R.id.btn_borrow_money, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_personal_info:
                startActivity(new Intent(WriteInfoMainActivity.this, PersonalInfoActivity.class));
                break;
            case R.id.layout_work_info:
                startActivity(new Intent(WriteInfoMainActivity.this, WorkInfoActivity.class));
                break;
            case R.id.layout_contact_info:
                startActivity(new Intent(WriteInfoMainActivity.this, ContactInfoActivity.class));
                break;
            case R.id.layout_bank_info:
                startActivity(new Intent(WriteInfoMainActivity.this, BankcardInfoActivity.class));
//                startActivity(new Intent(WriteInfoMainActivity.this, SupportBankActivity.class));
//                startActivity(new Intent(WriteInfoMainActivity.this, AddBankcardActivity.class));
                break;
            case R.id.btn_borrow_money:
                Intent intent = new Intent(WriteInfoMainActivity.this, LoanMainActivity.class);
                intent.putExtra("flag", "offline");
                startActivity(intent);
                break;
            case R.id.btn_submit:
                startActivity(new Intent(WriteInfoMainActivity.this, AuthCenterActivity.class));
                break;
        }
    }


    private void initData() {
        intent = getIntent();
        loanMoney = intent.getStringExtra("loanMoney");
        loanWeek = intent.getStringExtra("loanWeek");
        LogUtils.d("WriteInfoMainActivity接收数据---->" + loanMoney + "," + loanWeek);
        infoFinishStatus("0", "1", "1", "0");
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
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                ToastAlone.showLongToast(WriteInfoMainActivity.this, "刷新");
            }
        });
    }


    /**
     * 设置资料完成状态 0:代表未完成,1:代表完成
     *
     * @param personal 个人信息
     * @param work     工作信息
     * @param contact  联系人
     * @param bank     银行卡
     */
    private void infoFinishStatus(String personal, String work, String contact, String bank) {
        if (personal.equals("1")) {
            layoutPersonalInfo.setRightContent("已完成", getResources().getColor(R.color.mine_center_bg));
        } else {
            layoutPersonalInfo.setRightContentVisible(false);
        }
        if (work.equals("1")) {
            layoutWorkInfo.setRightContent("已完成", getResources().getColor(R.color.mine_center_bg));
        } else {
            layoutWorkInfo.setRightContentVisible(false);
        }
        if (contact.equals("1")) {
            layoutContactInfo.setRightContent("已完成", getResources().getColor(R.color.mine_center_bg));
        } else {
            layoutContactInfo.setRightContentVisible(false);
        }
        if (bank.equals("1")) {
            layoutBankInfo.setRightContent("已完成", getResources().getColor(R.color.mine_center_bg));
        } else {
            layoutBankInfo.setRightContentVisible(false);
        }
    }

    private void judgeMatchProInfo(String userFlag, boolean second, String money, String week) {
        final CustomerDialog customerDialog = new CustomerDialog(this);
        customerDialog.loanProductMatchInfo(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_close:
                        customerDialog.dismiss();
                        break;

                    case R.id.btn_comfirm_submit:
                        ToastAlone.showLongToast(WriteInfoMainActivity.this, "确认提交");
                        break;
                }
            }
        }, userFlag, second, money, week).show();
    }


}
