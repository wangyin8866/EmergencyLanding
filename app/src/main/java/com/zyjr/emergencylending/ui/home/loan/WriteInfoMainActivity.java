package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.BankInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.ContactInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.PersonalInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.WorkInfoActivity;
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
                startActivity(new Intent(WriteInfoMainActivity.this, BankInfoActivity.class));
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
        infoFinishStatus("0","1","1","0");
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
    }


    /**
     * 设置资料完成状态 0:代表未完成,1:代表完成
     * @param personal 个人信息
     * @param work 工作信息
     * @param contact 联系人
     * @param bank 银行卡
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

}