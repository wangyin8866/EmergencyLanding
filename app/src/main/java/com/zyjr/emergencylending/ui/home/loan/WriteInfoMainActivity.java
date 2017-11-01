package com.zyjr.emergencylending.ui.home.loan;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.ActivityCollector;
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
import com.zyjr.emergencylending.ui.home.loan.online.ApplyToOfflineConfirmActivity;
import com.zyjr.emergencylending.ui.home.presenter.WriteInfoPresenter;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.permission.ToolPermission;
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

    private Intent intent = null;
    private String apply_amount = ""; // 申请借款金额
    private String apply_zq = ""; // 申请借款周期
    private String apply_periods_unit = ""; // 借款周期单位
    private String online_type = ""; // 产品类型
    private String product_id = ""; // 产品id
    private WriteInfoBean writeInfoBean = null;
    private static final int CODE_PERMISSION_CONTANCT_LIST = 20000; // 权限请求 获取通讯录

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
        initGetData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingWriteInfoStatus();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (CODE_PERMISSION_CONTANCT_LIST == requestCode) {
            if (ToolPermission.checkPermission(permissions, grantResults)) {
                judgeMatchProInfo("", false, apply_amount, apply_zq);
            } else {
//                CommonUtils.jumpAppInfoSetting(WriteInfoMainActivity.this, "请允许读取权限!");
                ToastAlone.showLongToast(WriteInfoMainActivity.this, "请允许通讯录权限!");
            }
        }
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
                if (writeInfoBean == null) {
                    return;
                } else {
                    if (writeInfoBean.getUser_data_status().equals("0")
                            || writeInfoBean.getUser_job_status().equals("0")
                            || writeInfoBean.getUser_contact_status().equals("0")) {
                        ToastAlone.showLongToast(this, "请先完善个人信息!");
                        return;
                    }
                }
                intent = new Intent(this, BankcardInfoActivity.class);
                intent.putExtra("isEdit", writeInfoBean.getUser_contact_edit());
                intent.putExtra("status", writeInfoBean.getUser_contact_status());
                startActivity(intent);
                break;
            case R.id.btn_apply_quickly:
                intent = new Intent(WriteInfoMainActivity.this, ApplyToOfflineConfirmActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_submit:
                if (writeInfoBean == null) {
                    return;
                } else {
                    if (writeInfoBean.getUser_data_status().equals("0")
                            || writeInfoBean.getUser_job_status().equals("0")
                            || writeInfoBean.getUser_contact_status().equals("0")) {
                        ToastAlone.showLongToast(this, "请先完善个人信息!");
                        return;
                    } else if (writeInfoBean.getUser_bank_status().equals("0")) {
                        ToastAlone.showLongToast(this, "请完善资料信息!");
                        return;
                    }
                }
                // TODO 获取通讯资料
                if (ToolPermission.checkSelfPermission(this, null, Manifest.permission.READ_CONTACTS, "请允许读取权限!", CODE_PERMISSION_CONTANCT_LIST)) {
                    judgeMatchProInfo("", false, apply_amount, apply_zq);
                }
                break;
        }
    }


    private void initGetData() {
        intent = getIntent();
        apply_amount = intent.getStringExtra("loanMoney"); // 金额
        apply_zq = intent.getStringExtra("loanPeriod"); // 申请周期
        apply_periods_unit = intent.getStringExtra("loanPeriodUnit"); // 申请周期单位
        online_type = intent.getStringExtra("online_type"); // 产品类型
        product_id = intent.getStringExtra("product_id"); // 产品类型
        LogUtils.d("【接收数据】:" + "\n申请金额apply_amount:" + apply_amount + "\n申请周期apply_zq:" + apply_zq + "\n申请周期单位apply_periods_unit:"
                + apply_periods_unit + "\n产品id:" + product_id + "\n产品类型online_type:" + online_type);
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
        if (personal.equals("1") && work.equals("1") && contact.equals("1") && bank.equals("1")) {
            // TODO 根据状态,获取可申请产品
            rlNoSalesman.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 匹配金额 pop
     *
     * @param userFlag
     * @param second
     * @param money
     * @param week
     */
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
                        customerDialog.dismiss();
//                        submitLoanInfo();
                        startActivity(new Intent(WriteInfoMainActivity.this, AuthCenterActivity.class));
                        ActivityCollector.getInstance().popActivity(LoanMainActivity.class);
                        ActivityCollector.getInstance().popActivity(WriteInfoMainActivity.class);
                        break;
                }
            }
        }, userFlag, second, money, week).show();
    }


    private void loadingWriteInfoStatus() {
        Map<String, String> paramsMap = new HashMap<>();
        mPresenter.getWriteInfo(paramsMap);
    }

    private void submitLoanInfo() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("online_type", online_type); // 产品类型
        paramsMap.put("product_id", product_id); // 产品ID
        paramsMap.put("apply_amount", apply_amount); // 申请金额
        paramsMap.put("apply_periods", "1"); // 申请期数
        paramsMap.put("apply_zq", apply_zq); // 申请周期
        paramsMap.put("apply_periods_unit", apply_periods_unit); // 申请周期单位
        if (!BaseApplication.isSalesman.equals(Config.USER_SALESMAN)) {
            paramsMap.put("contact_list", new Gson().toJson(CommonUtils.queryContactPhoneNumber(this))); // 通讯录集合
        }
        mPresenter.submitLoanInformation(paramsMap);
    }


    @Override
    public void onSuccessGet(String returnCode, WriteInfoBean bean) {
        pullToRefreshScrollView.onRefreshComplete();
        writeInfoBean = bean;
        infoFinishStatus(
                StringUtil.isEmpty(writeInfoBean.getUser_data_status()) ? "" : writeInfoBean.getUser_data_status(),
                StringUtil.isEmpty(writeInfoBean.getUser_job_status()) ? "" : writeInfoBean.getUser_job_status(),
                StringUtil.isEmpty(writeInfoBean.getUser_contact_status()) ? "" : writeInfoBean.getUser_contact_status(),
                StringUtil.isEmpty(writeInfoBean.getUser_bank_status()) ? "" : writeInfoBean.getUser_bank_status());

    }

    @Override
    public void onSuccessSubmit(String apiCode, String msg) {
        ToastAlone.showLongToast(this, msg);
        // TODO 预检ok后 调往认证 注意 此时需要清空栈内的堆积
        ActivityCollector.getInstance().popActivity(LoanMainActivity.class);
        ActivityCollector.getInstance().popActivity(WriteInfoMainActivity.class);
        startActivity(new Intent(this, AuthCenterActivity.class));
    }

    @Override
    public void onFail(String returnCode, String flag, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
        pullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onError(String returnCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
        pullToRefreshScrollView.onRefreshComplete();
    }


}
