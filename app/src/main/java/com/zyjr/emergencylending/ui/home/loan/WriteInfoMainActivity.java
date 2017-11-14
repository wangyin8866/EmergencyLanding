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
import com.zyjr.emergencylending.entity.StoreResultBean;
import com.zyjr.emergencylending.entity.WriteInfoBean;
import com.zyjr.emergencylending.ui.home.View.HandleFailView;
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
    @BindView(R.id.rl_recommend_product)
    RelativeLayout rlRecommend;
    @BindView(R.id.root_refreshview)
    PullToRefreshScrollView pullToRefreshScrollView;

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
    private String renew_loan_type = ""; // 是否是首贷 0:首贷;3:续贷
    private String is_view = ""; // 是否显示推荐产品 1:是  0：否

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
                judgeMatchProInfo("", renew_loan_type, apply_amount, apply_periods, apply_zq, apply_periods_unit);
            } else {
                ToastAlone.showLongToast(WriteInfoMainActivity.this, "请允许通讯录权限!");
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
                        ToastAlone.showLongToast(this, "请先完善个人信息!");
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
                        ToastAlone.showLongToast(this, "请先完善个人信息!");
                        return;
                    }
                }
                intent = new Intent(this, BankcardInfoActivity.class);
                intent.putExtra("isEdit", writeInfoBean.getUser_contact_edit());
                intent.putExtra("status", writeInfoBean.getUser_contact_status());
                startActivityForResult(intent, INTENT_BANKCARD_CODE);
                break;

            case R.id.btn_apply_quickly:
                intent = new Intent(WriteInfoMainActivity.this, ApplyToOfflineConfirmActivity.class);
                intent.putExtra("renew_loan_type", renew_loan_type);  // 首续贷
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
                // 缺少首续贷标识
                if (StringUtil.isEmpty(renew_loan_type)) {

                } else {
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
                                            judgeMatchProInfo("", renew_loan_type, apply_amount, apply_periods, apply_zq, apply_periods_unit);
                                        } else {
                                            ToastAlone.showLongToast(WriteInfoMainActivity.this, "通讯录权限被拒绝,请您到设置页面手动授权");
                                        }
                                    }
                                },
                                2000,
                                Manifest.permission.READ_CONTACTS);
                    }
                }
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
        if (personal.equals("1") && work.equals("1") && contact.equals("1") && bank.equals("1")) {
            getMayApplyProductType();
        }
    }

    /**
     * 匹配金额 pop
     *
     * @param userFlag       用户标识
     * @param renewLoanType  是否首贷(0首,3续)
     * @param money          借款金额
     * @param period         借款周期
     * @param periodDistance 期数间隔
     * @param periodUnit     周期单位
     */
    private void judgeMatchProInfo(String userFlag, final String renewLoanType, String money, String period, String periodDistance, String periodUnit) {

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
                        if (product_id.equals("1")) {
                            LogUtils.d("当前是普通商户");
                            Intent intent = null;
                            if (is_view.equals("1")) {
                                // 如果有门店
                                intent = new Intent(WriteInfoMainActivity.this, ApplyConfirmActivity.class);
                            } else if (is_view.equals("0")) {
                                intent = new Intent(WriteInfoMainActivity.this, NoStoreApplyConfirmActivity.class);
                                intent.putExtra("moneyProgress", moneyProgress); // 金额进度
                                intent.putExtra("periodProgress", periodProgress); // 周期进度
                            }
                            intent.putExtra("online_type", online_type); // 产品类型
                            intent.putExtra("product_id", product_id); // 产品ID
                            intent.putExtra("apply_amount", apply_amount); // 申请金额
                            intent.putExtra("apply_periods", apply_periods); // 申请期数
                            intent.putExtra("apply_zq", apply_zq); // 申请期数间隔
                            intent.putExtra("apply_periods_unit", apply_periods_unit); // 申请周期单位
                            // 首续贷
                            if (renewLoanType.equals("0")) {
                                intent.putExtra("renew_loan_type", "0");  // 0.首贷
                            } else {
                                intent.putExtra("renew_loan_type", "3"); // 0.续贷
                            }
                            startActivity(intent);
                        } else if (product_id.equals("0")) {
                            // 线上件
                            submitLoanInfo("", "");
                        }
                        break;
                }
            }
        }, userFlag, renewLoanType, money, period, periodDistance, periodUnit).show();
    }


    private void loadingWriteInfoStatus() {
        loadingDialog = CustomProgressDialog.createDialog(this);
        loadingDialog.show();
        Map<String, String> paramsMap = new HashMap<>();
        mPresenter.getWriteInfo(paramsMap);
    }

    private void submitLoanInfo(String storeId, String storeName) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("online_type", online_type); // 产品类型
        paramsMap.put("product_id", product_id); // 产品ID
        paramsMap.put("apply_amount", apply_amount); // 申请金额
        paramsMap.put("apply_periods", apply_periods); // 申请期数
        paramsMap.put("apply_zq", apply_zq); // 申请期数间隔
        paramsMap.put("apply_periods_unit", apply_periods_unit); // 申请周期单位
        if (!BaseApplication.isSalesman.equals(Config.USER_SALESMAN)) {
            paramsMap.put("contact_list", new Gson().toJson(CommonUtils.queryContactPhoneNumber(this))); // 通讯录集合
        }
        paramsMap.put("store", storeId); // 门店iD
        paramsMap.put("store_name", storeName);  // 门店名称
        paramsMap.put("renew_loan_type", renew_loan_type);  // 首续贷标识
        mPresenter.submitLoanInformation(paramsMap);
    }

    private void getMayApplyProductType() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("product_id", product_id); // 产品ID
        mPresenter.getMayApplyProductType(paramsMap);
    }

    private void getClerkStoreInfo() {
        Map<String, String> params = new HashMap<>();
        mPresenter.getClerkStoreInfo(params);
    }

    @Override
    public void onSuccessGet(String returnCode, WriteInfoBean bean) {
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
    public void onSuccessSubmit(String apiCode, String flag, String msg) {
        ToastAlone.showLongToast(this, msg);
        if (Config.ONLINE.equals(flag)) {
            // TODO 预检ok后 调往认证 注意 此时需要清空栈内的堆积
            ActivityCollector.getInstance().popActivity(LoanMainActivity.class);
            ActivityCollector.getInstance().popActivity(WriteInfoMainActivity.class);
            startActivity(new Intent(this, AuthCenterActivity.class));
        } else if (Config.OFFLINE_CLERK.equals(flag)) {
            // 线下业务员录件成功
            Intent intent = new Intent(this, ClerkApplyResultActivity.class);
            intent.putExtra("flag", Config.CODE_SUCCESS);
            intent.putExtra("msg", msg);
            ActivityCollector.getInstance().popActivity(ImmediatelyBorrowActivity.class);
            ActivityCollector.getInstance().popActivity(WriteInfoMainActivity.class);
            ActivityCollector.getInstance().popActivity(BorrowActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onSuccessGetMayApplyPro(String apiCode, MayApplyProBean bean) {
        is_view = bean.getIs_view();
        if (online_type.equals("0")) {
            if (is_view.equals("1")) {
                rlRecommend.setVisibility(View.VISIBLE);
            } else {
                rlRecommend.setVisibility(View.GONE);
            }
        }
        if (bean.getRenew_loan_flag().equals("0")) {
            renew_loan_type = "0";
            // 首贷
            apply_amount = bean.getLoan_amount();
            apply_periods = bean.getLoan_periods();
            apply_zq = bean.getLoan_zq();
            apply_periods_unit = bean.getLoan_unit();
        } else {
            // 续贷
            renew_loan_type = "3";
        }
    }

    @Override
    public void onFail(String apiCode, String flag, String failMsg) {
        if (apiCode.equals(Constants.GET_LOCAL_STORE_INFO) && "API2022".equals(flag)) {
            // 获取业务员门店信息(未匹配到支持城市)
            Intent intent = new Intent(this, ClerkApplyResultActivity.class);
            intent.putExtra("flag", flag);
            intent.putExtra("msg", failMsg);
            startActivity(intent);
        } else if (apiCode.equals(Constants.SUBMIT_LOAN_INFORMATION) && product_id.equals("0")) {
            // 线上存款提交 失败
            Intent intent = new Intent(this, HandleFailActivity.class);
            intent.putExtra("flag", flag);
            intent.putExtra("msg", failMsg);
            startActivity(intent);
        } else {
            renew_loan_type = "";
            ToastAlone.showLongToast(this, failMsg);
            loadingDialog.dismiss();
            pullToRefreshScrollView.onRefreshComplete();
        }
    }

    @Override
    public void onError(String returnCode, String errorMsg) {
        renew_loan_type = "";
        ToastAlone.showLongToast(this, errorMsg);
        loadingDialog.dismiss();
        pullToRefreshScrollView.onRefreshComplete();
    }

    /**
     * 当时业务员录件时,回调
     */
    @Override
    public void onSuccessGetClerkStore(String apiCode, List<StoreResultBean.StoreBean> storeList) {
        if (storeList != null && storeList.size() > 0) {
            StoreResultBean.StoreBean storeBean = storeList.get(0);
            // TODO 提交业务员线下录件
            submitLoanInfo(storeBean.getStoreId(), storeBean.getStoreName());
        }
    }

}
