package com.zyjr.emergencylending.ui.home.loan.offline;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.SupportStoreAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.MobileContactBean;
import com.zyjr.emergencylending.entity.PrecheckResultBean;
import com.zyjr.emergencylending.entity.StoreResultBean;
import com.zyjr.emergencylending.ui.home.View.OfflineApplyView;
import com.zyjr.emergencylending.ui.home.loan.LoanApplyResultActivity;
import com.zyjr.emergencylending.ui.home.loan.WriteInfoMainActivity;
import com.zyjr.emergencylending.ui.home.presenter.OfflineApplyPresenter;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.WYUtils;
import com.zyjr.emergencylending.utils.permission.ToolPermission;
import com.zyjr.emergencylending.widget.recyc.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/14
 * 备注: 线下申请确认
 */
public class ApplyConfirmActivity extends BaseActivity<OfflineApplyPresenter, OfflineApplyView> implements OfflineApplyView {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.rv_store_supported)
    RecyclerView rvStoreSupported;
    @BindView(R.id.pb_store_supported_loading)
    ProgressBar pbLoadingStore;
    @BindView(R.id.btn_submit_apply)
    Button btnSubmitApply;
    @BindView(R.id.tv_offline_borrow_money)
    TextView tvOfflineBorrowMoney; // 申请金额
    @BindView(R.id.tv_offline_borrow_period)
    TextView tvOfflineBorrowPeriod; // 周期
    @BindView(R.id.ll_retry)
    LinearLayout llRetry; // 网络加载失败时重试
    @BindView(R.id.sv_main)
    ScrollView svMain;  // 主布局
    @BindView(R.id.layout_bottom)
    RelativeLayout layoutBottom;  // 底部布局

    private List<StoreResultBean.StoreBean> storeBeanList = new ArrayList<>();
    private SupportStoreAdapter adapter = null;
    private StoreResultBean.StoreBean storeBean = null;
    private String online_type = "";
    private String product_id = "";
    private String apply_amount = "";
    private String apply_periods = "";
    private String apply_zq = "";
    private String apply_periods_unit = "";

    @Override
    protected OfflineApplyPresenter createPresenter() {
        return new OfflineApplyPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_apply_confirm);
        ButterKnife.bind(this);

        init();
        initGetData();
    }

    @OnClick({R.id.btn_submit_apply, R.id.btn_retry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_apply:
                ToolPermission.checkPermission(this, new ToolPermission.PermissionCallBack() {
                            @Override
                            public void callBack(int requestCode, boolean isPass) {
                                LogUtils.d("权限检测结果---" + requestCode + "," + isPass);
                                if (isPass) {
                                    validate();
                                } else {
                                    ToastAlone.showShortToast(ApplyConfirmActivity.this, "通讯录权限被拒绝,请您到设置页面手动授权");
                                }
                            }
                        },
                        2000,
                        Manifest.permission.READ_CONTACTS);
                break;

            case R.id.btn_retry:
                getLocalStoreList();
                break;
        }
    }

    private void validate() {
        if (storeBean == null) {
            ToastAlone.showShortToast(this, "请选择门店!");
            return;
        }
        submitPrecheck();
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
        Intent intent = getIntent();
        online_type = intent.getStringExtra("online_type"); // 产品类型
        product_id = intent.getStringExtra("product_id");  // 产品ID
        apply_amount = intent.getStringExtra("apply_amount"); // 申请金额
        apply_periods = intent.getStringExtra("apply_periods"); // 申请期数
        apply_zq = intent.getStringExtra("apply_zq"); // 申请期数间隔
        apply_periods_unit = intent.getStringExtra("apply_periods_unit"); // 申请周期单位
        LogUtils.d("【接收数据】:" +
                "\n申请金额apply_amount:" + apply_amount +
                "\n申请期数apply_periods:" + apply_periods +
                "\n申请期数间隔apply_zq:" + apply_zq +
                "\n申请周期单位apply_periods_unit:" + apply_periods_unit +
                "\n产品id:" + product_id +
                "\n产品类型online_type:" + online_type);
        tvOfflineBorrowMoney.setText(apply_amount + "元");
        if ("1".equals(apply_periods_unit)) {
            // 天 作为期
            tvOfflineBorrowPeriod.setText(apply_zq + "天");
        } else if ("2".equals(apply_periods_unit)) {
            tvOfflineBorrowPeriod.setText(apply_periods + "周");
        }
        getLocalStoreList();
    }

    private void getLocalStoreList() {
        Map<String, String> params = new HashMap<>();
        mPresenter.getLocalStoreList(params);
    }

    private void submitPrecheck() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("online_type", online_type); // 产品类型
        paramsMap.put("product_id", product_id); // 产品ID
        paramsMap.put("apply_amount", apply_amount); // 申请金额
        paramsMap.put("apply_periods", apply_periods); // 申请期数
        paramsMap.put("apply_zq", apply_zq); // 申请期数间隔
        paramsMap.put("apply_periods_unit", apply_periods_unit); // 申请周期单位
        if (!BaseApplication.isSalesman.equals(Config.USER_SALESMAN)) {
            // 普通用户
            List<MobileContactBean> mobileContactsList = CommonUtils.queryContactPhoneNumber(this);
            if (mobileContactsList.size() == 0) {
                ToastAlone.showShortToast(ApplyConfirmActivity.this, "通讯录权限被拒绝,请您到设置页面手动授权");
                return;
            }
            paramsMap.put("contact_list", new Gson().toJson(mobileContactsList)); // 通讯录集合
        }
        paramsMap.put("store", storeBean.getStoreId()); // 门店iD
        paramsMap.put("store_name", storeBean.getStoreName());  // 门店名称
        paramsMap.put("phone_equipment", WYUtils.getDeviceFingerprinting(this));  // 手机设备唯一串号
        mPresenter.submitPrecheck(paramsMap);
    }

    private void showError() {
        llRetry.setVisibility(View.VISIBLE);
        svMain.setVisibility(View.GONE);
        layoutBottom.setVisibility(View.GONE);
    }

    private void showSuccess() {
        llRetry.setVisibility(View.GONE);
        svMain.setVisibility(View.VISIBLE);
        layoutBottom.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessGetLocalStoreList(String apiCode, List<StoreResultBean.StoreBean> beanList) {
        showSuccess();
        storeBeanList = beanList;
        adapter = new SupportStoreAdapter(R.layout.rv_item_store_info, storeBeanList);
        rvStoreSupported.setLayoutManager(new LinearLayoutManager(this));
        rvStoreSupported.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.auth_success)));
        rvStoreSupported.setAdapter(adapter);
        rvStoreSupported.setVisibility(View.VISIBLE);
        adapter.bindToRecyclerView(rvStoreSupported);
        adapter.setSelected(0, true);
        storeBean = adapter.getSelected(0);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                adapter.setSelected(position, true);
                storeBean = adapter.getSelected(position);
            }
        });
    }

    @Override
    public void onSuccessSubmit(String apiCode, String msg) {

    }

    @Override
    public void onSuccessPrecheck(String apiCode, String flag, PrecheckResultBean precheckResultBean) {
        Intent intent = new Intent(this, LoanApplyResultActivity.class);
        intent.putExtra("online_type", online_type);
        intent.putExtra("product_id", product_id);
        startActivity(intent);
    }

    @Override
    public void onFail(String apiCode, String flag, String failMsg) {
        ToastAlone.showShortToast(this, failMsg);
    }

    @Override
    public void onError(String apiCode, String errorMsg) {
        showError();
    }
}
