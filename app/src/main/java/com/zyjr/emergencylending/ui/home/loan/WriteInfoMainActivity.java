package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.ui.home.MessageActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.BankInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.ContactInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.PersonalDataActivity;
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

    @BindView(R.id.layout_personal_info)
    SettingItemView layoutPersionalInfo; // 个人信息
    @BindView(R.id.layout_work_info)
    SettingItemView layoutWorkInfo; // 工作信息
    @BindView(R.id.layout_contact_info)
    SettingItemView layoutContactInfo; // 联系人
    @BindView(R.id.layout_bank_info)
    SettingItemView layoutBankInfo; // 银行卡信息

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_infomation_main);
        ButterKnife.bind(this);

        initData();
    }

    @OnClick({R.id.layout_personal_info, R.id.layout_work_info, R.id.layout_contact_info, R.id.layout_bank_info,R.id.btn_borrow_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_personal_info:
                startActivity(new Intent(WriteInfoMainActivity.this, PersonalDataActivity.class));
                break;
            case R.id.layout_work_info:
                startActivity(new Intent(WriteInfoMainActivity.this, WorkInfoActivity.class));
                break;
            case R.id.layout_contact_info:
                startActivity(new Intent(WriteInfoMainActivity.this, ContactInfoActivity.class));
                break;
            case R.id.layout_bank_info:
//                startActivity(new Intent(WriteInfoMainActivity.this, BankInfoActivity.class));
                startActivity(new Intent(WriteInfoMainActivity.this, SupportBankActivity.class));
                break;
            case R.id.btn_borrow_money:
                Intent intent = new Intent(WriteInfoMainActivity.this, LoanMainActivity.class);
                intent.putExtra("flag","offline");
                startActivity(intent);
                break;
        }
    }


    private void initData() {
        layoutPersionalInfo.setRightContent("未完成",getResources().getColor(R.color.colorAccent));
        layoutWorkInfo.setRightContent("未完成",getResources().getColor(R.color.colorAccent));
        layoutContactInfo.setRightContent("未完成",getResources().getColor(R.color.colorAccent));
        layoutBankInfo.setRightContent("未完成",getResources().getColor(R.color.colorAccent));
    }

}
