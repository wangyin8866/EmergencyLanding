package com.zyjr.emergencylending.ui.home.loan;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.xfqz.xjd.mylibrary.CustomProgressDialog;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.ActivityCollector;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.entity.MayApplyProBean;
import com.zyjr.emergencylending.entity.MobileContactBean;
import com.zyjr.emergencylending.entity.PrecheckResultBean;
import com.zyjr.emergencylending.entity.StoreResultBean;
import com.zyjr.emergencylending.entity.WriteInfoBean;
import com.zyjr.emergencylending.ui.home.View.WriteInfoView;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.BankcardInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.ContactInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.PersonalInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.WorkInfoActivity;
import com.zyjr.emergencylending.ui.home.loan.offline.ApplyConfirmActivity;
import com.zyjr.emergencylending.ui.home.loan.offline.NoStoreApplyConfirmActivity;
import com.zyjr.emergencylending.ui.home.loan.online.ApplyToOfflineConfirmActivity;
import com.zyjr.emergencylending.ui.home.presenter.WriteInfoPresenter;
import com.zyjr.emergencylending.ui.salesman.activity.BorrowActivity;
import com.zyjr.emergencylending.ui.salesman.activity.ImmediatelyBorrowActivity;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.WYUtils;
import com.zyjr.emergencylending.utils.permission.ToolPermission;
import com.zyjr.emergencylending.widget.SettingItemView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/12
 * </br> 填写资料(包含个人信息.工作信息.联系人信息.银行卡)
 * </br> 新增字段strategy_flag,首续策略标识 0否;1是
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
    @BindView(R.id.root_refreshview)
    PullToRefreshScrollView pullToRefreshScrollView;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry; // 网络加载失败时重试

    private Intent intent = null;
    private String apply_amount = ""; // 申请借款金额
    private String apply_periods = ""; // 申请借款周期
    private String apply_zq = ""; // 申请期数间隔
    private String apply_periods_unit = ""; // 借款周期单位
    private String online_type = ""; // 产品类型 0:线上;1:线下
    private String product_id = ""; // 产品id
    private String moneyProgress = ""; // 金额进度
    private String periodProgress = ""; // 借款周期进度

    private WriteInfoBean writeInfoBean = null;
    private static final int CODE_PERMISSION_CONTANCT_LIST = 20000; // 权限请求 获取通讯录
    private static final int INTENT_PERSONAL_INFO_CODE = 20001; // 跳往个人信息
    private static final int INTENT_WORK_INFO_CODE = 20002; // 跳往工作信息
    private static final int INTENT_CONTACT_INFO_CODE = 20003; // 跳往联系人
    private static final int INTENT_BANKCARD_CODE = 20004; // 跳往银行卡
    private CustomProgressDialog loadingDialog = null;
    private String is_run_risk = ""; // 首续贷策略标示

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
        loadingWriteInfoStatus();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (CODE_PERMISSION_CONTANCT_LIST == requestCode) {
            if (ToolPermission.checkPermission(permissions, grantResults)) {
                operateConfirm("您的申请资料一旦提交，将不可修改！");
            } else {
                ToastAlone.showShortToast(WriteInfoMainActivity.this, "通讯录权限被拒绝,请您到设置页面手动授权");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == INTENT_PERSONAL_INFO_CODE
                || requestCode == INTENT_WORK_INFO_CODE
                || requestCode == INTENT_CONTACT_INFO_CODE
                || requestCode == INTENT_BANKCARD_CODE) && resultCode == RESULT_OK) {
            loadingWriteInfoStatus();
        }
    }

    /**
     * status是否已完成:1完成 0:未完成;isEdit是否可编辑:1是 0:否
     *
     * @param view
     */
    @OnClick({R.id.layout_personal_info, R.id.layout_work_info, R.id.layout_contact_info, R.id.layout_bank_info, R.id.btn_submit, R.id.btn_retry})
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
                startActivityForResult(intent, INTENT_PERSONAL_INFO_CODE);
                break;

            case R.id.layout_work_info:
                if (writeInfoBean == null) {
                    return;
                }
                intent = new Intent(this, WorkInfoActivity.class);
                intent.putExtra("isEdit", writeInfoBean.getUser_job_edit());
                intent.putExtra("status", writeInfoBean.getUser_job_status());
                startActivityForResult(intent, INTENT_WORK_INFO_CODE);
                break;

            case R.id.layout_contact_info:
                if (writeInfoBean == null) {
                    return;
                } else {
                    if (writeInfoBean.getUser_data_status().equals("0")) {
                        ToastAlone.showShortToast(this, "请先完善个人信息!");
                        return;
                    }
                }
                intent = new Intent(this, ContactInfoActivity.class);
                intent.putExtra("isEdit", writeInfoBean.getUser_contact_edit());
                intent.putExtra("status", writeInfoBean.getUser_contact_status());
                startActivityForResult(intent, INTENT_CONTACT_INFO_CODE);
                break;

            case R.id.layout_bank_info:
                if (writeInfoBean == null) {
                    return;
                } else {
                    if (writeInfoBean.getUser_data_status().equals("0")) {
                        ToastAlone.showShortToast(this, "请先完善个人信息!");
                        return;
                    }
                }
                intent = new Intent(this, BankcardInfoActivity.class);
                intent.putExtra("isEdit", writeInfoBean.getUser_contact_edit());
                intent.putExtra("status", writeInfoBean.getUser_contact_status());
                startActivityForResult(intent, INTENT_BANKCARD_CODE);
                break;

            case R.id.btn_submit:
                if (writeInfoBean == null) {
                    return;
                } else {
                    if (writeInfoBean.getUser_data_status().equals("0")
                            || writeInfoBean.getUser_job_status().equals("0")
                            || writeInfoBean.getUser_contact_status().equals("0")
                            || writeInfoBean.getUser_bank_status().equals("0")) {
                        ToastAlone.showShortToast(this, "请完善资料信息!");
                        return;
                    }
                }
                if (BaseApplication.isSalesman.equals(Config.USER_SALESMAN)) {
                    LogUtils.d("当前是业务员----走业务员");
                    getClerkStoreInfo();
                } else {
                    // TODO 获取通讯资料
                    ToolPermission.checkPermission(this, new ToolPermission.PermissionCallBack() {
                                @Override
                                public void callBack(int requestCode, boolean isPass) {
                                    LogUtils.d("权限检测结果---" + requestCode + "," + isPass);
                                    if (isPass) {
                                        if (StringUtil.isNotEmpty(product_id) && product_id.equals("1")) {
                                            LogUtils.d("[线下----]");
                                            getClerkStoreInfo();
                                        } else {
                                            LogUtils.d("[线上----]");
                                            operateConfirm("您的申请资料一旦提交，将不可修改！");
                                        }
                                    } else {
                                        ToastAlone.showShortToast(WriteInfoMainActivity.this, "通讯录权限被拒绝,请您到设置页面手动授权");
                                    }
                                }
                            },
                            2000,
                            Manifest.permission.READ_CONTACTS);
                }
                break;

            case R.id.btn_retry:
                loadingWriteInfoStatus();
                break;
        }
    }


    private void initGetData() {
        intent = getIntent();
        apply_amount = intent.getStringExtra("apply_amount"); // 申请金额
        apply_periods = intent.getStringExtra("apply_periods"); // 申请期数 天|周
        apply_zq = intent.getStringExtra("apply_zq"); // 申请期数间隔
        apply_periods_unit = intent.getStringExtra("apply_periods_unit"); // 申请周期单位
        online_type = intent.getStringExtra("online_type"); // 产品类型
        product_id = intent.getStringExtra("product_id"); // 产品类型
        moneyProgress = intent.getStringExtra("moneyProgress"); // 金额进度
        periodProgress = intent.getStringExtra("periodProgress"); // 周期进度
        LogUtils.d("【接收数据】:" +
                "\n申请金额apply_amount:" + apply_amount +
                "\n申请期数apply_periods:" + apply_periods +
                "\n申请期数间隔apply_zq:" + apply_zq +
                "\n申请周期单位apply_periods_unit:" + apply_periods_unit +
                "\n产品id:" + product_id +
                "\n产品类型online_type:" + online_type +
                "\n金额进度moneyProgress:" + moneyProgress +
                "\n周期类型periodProgress:" + periodProgress
        );
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
    }

    /**
     * 操作确认(走大数据风控)
     *
     * @param detail
     */
    private void operateConfirm(String detail) {
        final CustomerDialog customerDialog = new CustomerDialog(this);
        customerDialog.loanOperateConfirm(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_close:
                        customerDialog.dismiss();
                        break;

                    case R.id.btn_comfirm_submit:
                        customerDialog.dismiss();
                        submitPrecheck(BaseApplication.isSalesman, "", "");
                        break;
                }
            }
        }, detail).show();
    }

    private void loadingWriteInfoStatus() {
        loadingDialog = CustomProgressDialog.createDialog(this);
        loadingDialog.show();
        Map<String, String> paramsMap = new HashMap<>();
        mPresenter.getWriteInfo(paramsMap);
    }


    // 提交预检
    private void submitPrecheck(String userFlag, String storeId, String storeName) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("online_type", online_type); // 产品类型
        paramsMap.put("product_id", product_id); // 产品ID
        if (Config.USER_SALESMAN.equals(userFlag)) {
            // 业务员
            paramsMap.put("store", storeId); // 门店iD
            paramsMap.put("store_name", storeName);  // 门店名称
            paramsMap.put("apply_amount", apply_amount); // 申请金额
            paramsMap.put("apply_periods", apply_periods); // 申请期数
            paramsMap.put("apply_zq", apply_zq); // 申请期数间隔
            paramsMap.put("apply_periods_unit", apply_periods_unit); // 申请周期单位
        } else {
            // 普通用户
            List<MobileContactBean> mobileContactsList = CommonUtils.queryContactPhoneNumber(this);
            if (mobileContactsList.size() == 0) {
                ToastAlone.showShortToast(WriteInfoMainActivity.this, "通讯录权限被拒绝,请您到设置页面手动授权");
                return;
            }
            paramsMap.put("contact_list", new Gson().toJson(mobileContactsList)); // 通讯录集合
        }
        paramsMap.put("is_run_risk", is_run_risk);  // 策略标识(新增)
        paramsMap.put("phone_equipment", WYUtils.getDeviceFingerprinting(this));  // 手机设备唯一串号
        mPresenter.submitPrecheck(paramsMap);
    }

    private void getClerkStoreInfo() {
        Map<String, String> params = new HashMap<>();
        mPresenter.getClerkStoreInfo(params);
    }

    @Override
    public void onSuccessGet(String returnCode, WriteInfoBean bean) {
        showSuccess();
        loadingDialog.dismiss();
        pullToRefreshScrollView.onRefreshComplete();
        writeInfoBean = bean;
        infoFinishStatus(
                StringUtil.isEmpty(writeInfoBean.getUser_data_status()) ? "" : writeInfoBean.getUser_data_status(),
                StringUtil.isEmpty(writeInfoBean.getUser_job_status()) ? "" : writeInfoBean.getUser_job_status(),
                StringUtil.isEmpty(writeInfoBean.getUser_contact_status()) ? "" : writeInfoBean.getUser_contact_status(),
                StringUtil.isEmpty(writeInfoBean.getUser_bank_status()) ? "" : writeInfoBean.getUser_bank_status());

    }


    @Override
    public void onFail(String apiCode, String flag, String failMsg) {
        if (Constants.GET_LOCAL_STORE_INFO.equals(apiCode) && "API2022".equals(flag)) {
            // 业务员帮录件获取门店信息(未匹配到支持城市)
            Intent intent = new Intent(this, ClerkApplyResultActivity.class);
            intent.putExtra("flag", flag);
            intent.putExtra("msg", "未匹配到支持城市，请核查资料");
            startActivity(intent);
        } else if (Constants.SUBMIT_LOAN_INFORMATION.equals(apiCode) && "0".equals(product_id)) {
            // 线上借款预检失败
            Intent intent = new Intent(this, HandleFailActivity.class);
            intent.putExtra("jumpFlag", "precheck");
            intent.putExtra("flag", flag);
            intent.putExtra("msg", failMsg);
            startActivity(intent);
        } else {
            ToastAlone.showShortToast(this, failMsg);
            loadingDialog.dismiss();
            pullToRefreshScrollView.onRefreshComplete();
        }
    }

    @Override
    public void onError(String returnCode, String errorMsg) {
        ToastAlone.showShortToast(this, errorMsg);
        loadingDialog.dismiss();
        pullToRefreshScrollView.onRefreshComplete();
        if (Constants.GET_WRITE_INFO.equals(returnCode)) {
            showError();
        }
    }

    /**
     * 线下录件时调用 当时业务员录件时|用户录线下件
     */
    @Override
    public void onSuccessGetClerkStore(String apiCode, List<StoreResultBean.StoreBean> storeList) {
        if (storeList != null && storeList.size() > 0) {
            if (BaseApplication.isSalesman.equals(Config.USER_SALESMAN)) {
                StoreResultBean.StoreBean storeBean = storeList.get(0);
                // TODO 业务员提交线下录件
                submitPrecheck(BaseApplication.isSalesman, storeBean.getStoreId(), storeBean.getStoreName());
            } else {
                // TODO 普通用户操作线下 有门店
                Intent intent = new Intent(WriteInfoMainActivity.this, ApplyConfirmActivity.class);
                intent.putExtra("online_type", online_type); // 产品类型
                intent.putExtra("product_id", product_id); // 产品ID
                intent.putExtra("apply_amount", apply_amount); // 申请金额
                intent.putExtra("apply_periods", apply_periods); // 申请期数
                intent.putExtra("apply_zq", apply_zq); // 申请期数间隔
                intent.putExtra("apply_periods_unit", apply_periods_unit); // 申请周期单位
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(WriteInfoMainActivity.this, NoStoreApplyConfirmActivity.class);
            startActivity(intent);
        }
    }

    // 提交预检成功 2017.11.20
    @Override
    public void onSuccessPrecheck(String apiCode, String flag, PrecheckResultBean precheckResultBean) {
        if (Config.ONLINE.equals(flag)) {
            String is_run_risk = precheckResultBean.getIs_run_risk(); // 是否过风控首贷策略 1:是 0:否
            if ("1".equals(is_run_risk)) {
                // 走风控策略 (认证)
                ActivityCollector.getInstance().popActivity(LoanMainActivity.class);
                ActivityCollector.getInstance().popActivity(WriteInfoMainActivity.class);
                startActivity(new Intent(WriteInfoMainActivity.this, AuthCenterActivity.class));
                finish();
            } else if ("0".equals(is_run_risk)) {
                // 走续贷
                Intent intent = new Intent(this, ReloanApplyActivity.class);
                intent.putExtra("precheckResultBean", precheckResultBean);
                startActivity(intent);
            }
        } else if (Config.OFFLINE_CLERK.equals(flag)) {
            // 线下业务员录件成功
            Intent intent = new Intent(this, ClerkApplyResultActivity.class);
            intent.putExtra("flag", Config.CODE_SUCCESS);
            ActivityCollector.getInstance().popActivity(ImmediatelyBorrowActivity.class);
            ActivityCollector.getInstance().popActivity(WriteInfoMainActivity.class);
            ActivityCollector.getInstance().popActivity(BorrowActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onSuccessGetMayApplyPro(String apiCode, MayApplyProBean bean) {
    }

    @Override
    public void onSuccessSubmit(String apiCode, String flag, String msg) {
    }

    private void showError() {
        llRetry.setVisibility(View.VISIBLE);
        pullToRefreshScrollView.setVisibility(View.GONE);
    }

    private void showSuccess() {
        llRetry.setVisibility(View.GONE);
        pullToRefreshScrollView.setVisibility(View.VISIBLE);
    }


}
