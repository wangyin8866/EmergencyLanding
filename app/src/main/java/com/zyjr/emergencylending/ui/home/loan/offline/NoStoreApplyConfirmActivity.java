package com.zyjr.emergencylending.ui.home.loan.offline;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.ActivityCollector;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.entity.PrecheckResultBean;
import com.zyjr.emergencylending.entity.StoreResultBean;
import com.zyjr.emergencylending.ui.home.View.OfflineApplyView;
import com.zyjr.emergencylending.ui.home.loan.AuthCenterActivity;
import com.zyjr.emergencylending.ui.home.loan.HandleFailActivity;
import com.zyjr.emergencylending.ui.home.loan.LoanMainActivity;
import com.zyjr.emergencylending.ui.home.loan.ReloanApplyActivity;
import com.zyjr.emergencylending.ui.home.loan.WriteInfoMainActivity;
import com.zyjr.emergencylending.ui.home.presenter.OfflineApplyPresenter;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.WYUtils;
import com.zyjr.emergencylending.utils.permission.ToolPermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/14
 * <p> 线下件提交,无匹配门店,推线上件。校验是否走大风控流程(一般首贷走,续贷不需要走)
 */
public class NoStoreApplyConfirmActivity extends BaseActivity<OfflineApplyPresenter, OfflineApplyView> implements OfflineApplyView {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.tv_product_recommend_title)
    TextView tvProductRecommendTitle;
    @BindView(R.id.layout_product_recommend)
    LinearLayout layoutProductRecommend;
    @BindView(R.id.btn_submit_apply)
    Button btnSubmit;

    private String online_type = "";
    private String product_id = "";


    @Override
    protected OfflineApplyPresenter createPresenter() {
        return new OfflineApplyPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_no_store_apply_confirm);
        ButterKnife.bind(this);

        init();
        initGetData();
    }


    @OnClick({R.id.btn_submit_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_apply:
                ToolPermission.checkPermission(this, new ToolPermission.PermissionCallBack() {
                            @Override
                            public void callBack(int requestCode, boolean isPass) {
                                LogUtils.d("权限检测结果---" + requestCode + "," + isPass);
                                if (isPass) {
                                    operateConfirm("是否确认申请急速借款");
                                } else {
                                    ToastAlone.showLongToast(NoStoreApplyConfirmActivity.this, "通讯录权限被拒绝,请您到设置页面手动授权");
                                }
                            }
                        },
                        2000,
                        Manifest.permission.READ_CONTACTS);
                break;
        }
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

    private void initGetData() {
        LogUtils.d("【需要转走线上】");
    }

    // 提交预检 走线上
    private void submitPrecheck() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("online_type", "0"); // 产品类型
        paramsMap.put("product_id", "0"); // 产品ID
        paramsMap.put("contact_list", new Gson().toJson(CommonUtils.queryContactPhoneNumber(this))); // 通讯录集合
        paramsMap.put("is_run_risk", "");  // 策略标识
        paramsMap.put("phone_equipment", WYUtils.getDeviceFingerprinting(this));  // 手机设备唯一串号
        mPresenter.submitPrecheck(paramsMap);
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
                        submitPrecheck();


                        break;
                }
            }
        }, detail).show();
    }

    @Override
    public void onSuccessGetLocalStoreList(String apiCode, List<StoreResultBean.StoreBean> beanList) {

    }

    @Override
    public void onSuccessSubmit(String apiCode, String msg) {
        ToastAlone.showShortToast(this, msg);
        ActivityCollector.getInstance().popActivity(LoanMainActivity.class);
        ActivityCollector.getInstance().popActivity(WriteInfoMainActivity.class);
        Intent intent = new Intent(this, AuthCenterActivity.class);
        intent.putExtra("online_type", online_type);
        intent.putExtra("product_id", product_id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccessPrecheck(String apiCode, String flag, PrecheckResultBean precheckResultBean) {
        String is_run_risk = precheckResultBean.getIs_run_risk(); // 是否过风控首贷策略 1:是 0:否
        if ("1".equals(is_run_risk)) {
            ActivityCollector.getInstance().popActivity(LoanMainActivity.class);
            ActivityCollector.getInstance().popActivity(WriteInfoMainActivity.class);
            startActivity(new Intent(NoStoreApplyConfirmActivity.this, AuthCenterActivity.class));
            finish();
        } else if ("0".equals(is_run_risk)) {
            // 不走大数据风控,续贷产品选择
            Intent intent = new Intent(this, ReloanApplyActivity.class);
            intent.putExtra("precheckResultBean", precheckResultBean);
            startActivity(intent);
        }
    }

    @Override
    public void onFail(String apiCode, String flag, String failMsg) {
        // 线上借款预检失败
        Intent intent = new Intent(this, HandleFailActivity.class);
        intent.putExtra("jumpFlag", "precheck");
        intent.putExtra("flag", flag);
        intent.putExtra("msg", failMsg);
        startActivity(intent);
    }

    @Override
    public void onError(String apiCode, String errorMsg) {
        ToastAlone.showShortToast(this, errorMsg);
    }
}
