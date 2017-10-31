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
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.entity.WriteInfoBean;
import com.zyjr.emergencylending.ui.home.View.WriteInfoView;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.BankcardInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.ContactInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.PersonalInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.WorkInfoActivity;
import com.zyjr.emergencylending.ui.home.presenter.WriteInfoPresenter;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.widget.SettingItemView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * 备注: 填写资料(包含个人信息.工作信息)
 */
public class WriteInfoMainActivity extends BaseActivity<WriteInfoPresenter, WriteInfoView> implements WriteInfoView {
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
    private WriteInfoBean writeInfoBean = null;

    @Override
    protected WriteInfoPresenter createPresenter() {
        return new WriteInfoPresenter(this);
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
        if (BaseApplication.isSalesman.equals(Config.USER_SALESMAN)) {
            //线下
            rlNoSalesman.setVisibility(View.GONE);
        } else {
            rlNoSalesman.setVisibility(View.VISIBLE);
        }
        loadingWriteInfoStatus();
    }

    /**
     * status是否已完成:1完成 0:未完成;isEdit是否可编辑:1是 0:否
     *
     * @param view
     */
    @OnClick({R.id.layout_personal_info, R.id.layout_work_info, R.id.layout_contact_info, R.id.layout_bank_info, R.id.btn_apply_quickly, R.id.btn_submit})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.layout_personal_info:
                if (writeInfoBean == null) {
                    return;
                }
                intent = new Intent(this, PersonalInfoActivity.class);
                intent.putExtra("isEdit", writeInfoBean.getUser_data_edit());
                intent.putExtra("status", writeInfoBean.getUser_data_status());
                startActivity(intent);
                break;
            case R.id.layout_work_info:
                if (writeInfoBean == null) {
                    return;
                }
                intent = new Intent(this, WorkInfoActivity.class);
                intent.putExtra("isEdit", writeInfoBean.getUser_job_edit());
                intent.putExtra("status", writeInfoBean.getUser_job_status());
                startActivity(intent);
                break;
            case R.id.layout_contact_info:
                if (writeInfoBean == null) {
                    return;
                }
                intent = new Intent(this, ContactInfoActivity.class);
                intent.putExtra("isEdit", writeInfoBean.getUser_contact_edit());
                intent.putExtra("status", writeInfoBean.getUser_contact_status());
                startActivity(intent);
                break;
            case R.id.layout_bank_info:
//                if (writeInfoBean == null) {
//                    return;
//                }
//                intent = new Intent(this, BankcardInfoActivity.class);
//                intent.putExtra("isEdit", writeInfoBean.getUser_contact_edit());
//                intent.putExtra("status", writeInfoBean.getUser_contact_status());
//                startActivity(intent);

                intent = new Intent(this, BankcardInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_apply_quickly:
                intent = new Intent(WriteInfoMainActivity.this, LoanMainActivity.class);
                intent.putExtra("flag", "offline");
                startActivity(intent);
                break;
            case R.id.btn_submit:
                startActivity(new Intent(WriteInfoMainActivity.this, AuthCenterActivity.class));
                break;
        }
    }


    private void initData() {
//        intent = getIntent();
//        loanMoney = intent.getStringExtra("loanMoney");
//        loanWeek = intent.getStringExtra("loanWeek");
//        LogUtils.d("WriteInfoMainActivity接收数据---->" + loanMoney + "," + loanWeek);
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
                loadingWriteInfoStatus();
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
            layoutPersonalInfo.setRightContent("未完成", getResources().getColor(R.color.front_text_color_hint));
        }
        if (work.equals("1")) {
            layoutWorkInfo.setRightContent("已完成", getResources().getColor(R.color.mine_center_bg));
        } else {
            layoutWorkInfo.setRightContent("未完成", getResources().getColor(R.color.front_text_color_hint));
        }
        if (contact.equals("1")) {
            layoutContactInfo.setRightContent("已完成", getResources().getColor(R.color.mine_center_bg));
        } else {
            layoutContactInfo.setRightContent("未完成", getResources().getColor(R.color.front_text_color_hint));
        }
        if (bank.equals("1")) {
            layoutBankInfo.setRightContent("已完成", getResources().getColor(R.color.mine_center_bg));
        } else {
            layoutBankInfo.setRightContent("未完成", getResources().getColor(R.color.front_text_color_hint));
        }
        if(personal.equals("1") && work.equals("1") && contact.equals("1") && bank.equals("1")){
            // TODO 根据状态,获取可申请产品
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


    private void loadingWriteInfoStatus() {
        Map<String, String> paramsMap = new HashMap<>();
        mPresenter.getWriteInfo(paramsMap);
    }

    @Override
    public void onSuccessGet(String returnCode, WriteInfoBean model) {
        pullToRefreshScrollView.onRefreshComplete();
        writeInfoBean = model;
        infoFinishStatus(
                StringUtil.isEmpty(model.getUser_data_status()) ? "" : model.getUser_data_status(),
                StringUtil.isEmpty(model.getUser_job_status()) ? "" : model.getUser_job_status(),
                StringUtil.isEmpty(model.getUser_contact_status()) ? "" : model.getUser_contact_status(),
                StringUtil.isEmpty(model.getUser_bank_status()) ? "" : model.getUser_bank_status());

    }

    @Override
    public void onFail(String returnCode, String flag, String errorMsg) {
        writeInfoBean = null;
        pullToRefreshScrollView.onRefreshComplete();
        infoFinishStatus("", "", "", "");
        ToastAlone.showLongToast(this, errorMsg);
    }

    @Override
    public void onError(String returnCode, String errorMsg) {
        writeInfoBean = null;
        pullToRefreshScrollView.onRefreshComplete();
        ToastAlone.showLongToast(this, errorMsg);
    }


}
