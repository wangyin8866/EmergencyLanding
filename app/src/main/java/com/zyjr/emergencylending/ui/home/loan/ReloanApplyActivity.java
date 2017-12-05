package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.ReloanProductAdapter;
import com.zyjr.emergencylending.base.ActivityCollector;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.MobileContactBean;
import com.zyjr.emergencylending.entity.PrecheckResultBean;
import com.zyjr.emergencylending.ui.home.View.ReloanApplyView;
import com.zyjr.emergencylending.ui.home.loan.offline.NoStoreApplyConfirmActivity;
import com.zyjr.emergencylending.ui.home.presenter.ReloanApplyPresenter;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author neil
 * @date 2017/11/17
 * </br> 续贷相关 产品选择|预检
 * 1.针对续贷用户
 */
public class ReloanApplyActivity extends BaseActivity<ReloanApplyPresenter, ReloanApplyView> implements ReloanApplyView {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.rl_reloan_product)
    RecyclerView rlReloanProduct;
    @BindView(R.id.btn_apply)
    Button btnApply;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry; // 网络加载失败时重试
    @BindView(R.id.ll_main)
    LinearLayout llMain;  // 主布局
    @BindView(R.id.layout_bottom)
    RelativeLayout layoutBottom;  // 底部布局

    private List<PrecheckResultBean.LoanProduct> reloanProductList = new ArrayList<>();
    private ReloanProductAdapter reloanProductAdapter = null;
    private PrecheckResultBean.LoanProduct loanProduct = null;
    private PrecheckResultBean precheckResultBean = null;


    @Override
    protected ReloanApplyPresenter createPresenter() {
        return new ReloanApplyPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reloan_product_choose);
        ButterKnife.bind(this);

        init();
        initGetData();
    }

    @OnClick({R.id.btn_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_apply:
                if (loanProduct == null) {
                    ToastAlone.showShortToast(this, "请选择产品");
                    break;
                }
                submitPrecheck();
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
        Intent intent = getIntent();
        precheckResultBean = (PrecheckResultBean) intent.getSerializableExtra("precheckResultBean");
        LogUtils.d("预检数据:" + new Gson().toJson(precheckResultBean.getLoan_products()));
        fillUpData(precheckResultBean.getLoan_products());
    }

    private void fillUpData(List<PrecheckResultBean.LoanProduct> resultData) {
        if (resultData != null && resultData.size() > 0) {
            reloanProductList = resultData;
        }
        reloanProductAdapter = new ReloanProductAdapter(R.layout.rv_item_reloan_product_choose, reloanProductList);
        rlReloanProduct.setLayoutManager(new LinearLayoutManager(this));
        rlReloanProduct.setAdapter(reloanProductAdapter);
        reloanProductAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                reloanProductAdapter.setSelected(position, true);
                loanProduct = reloanProductAdapter.getSelected(position);
            }
        });
    }

    // 提交预检
    private void submitPrecheck() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("online_type", "0"); // 产品类型
        paramsMap.put("product_id", "0"); // 产品ID
        List<MobileContactBean> mobileContactsList = CommonUtils.queryContactPhoneNumber(this);
        if (mobileContactsList.size() == 0) {
            ToastAlone.showShortToast(ReloanApplyActivity.this, "通讯录权限被拒绝,请您到设置页面手动授权");
            return;
        }
        paramsMap.put("contact_list", new Gson().toJson(mobileContactsList)); // 通讯录集合
        paramsMap.put("is_run_risk", precheckResultBean.getIs_run_risk());  // 策略标识
        paramsMap.put("phone_equipment", WYUtils.getDeviceFingerprinting(this));  // 手机设备唯一串号
        paramsMap.put("renew_loans_grade", precheckResultBean.getRenew_loans_grade());  // 提交标示
        paramsMap.put("renew_loan_type", precheckResultBean.getRenew_loans());  // 首贷续贷标识
        paramsMap.put("loan_amount", loanProduct.getLoan_amount()); //审批金额
        paramsMap.put("loan_periods", loanProduct.getLoan_periods()); //审批期数
        paramsMap.put("loan_unit", loanProduct.getLoan_unit()); //审批期数单位
        paramsMap.put("loan_spac", loanProduct.getLoan_spac()); //审批期数间隔
        paramsMap.put("submitFlag", precheckResultBean.getSubmitFlag());  // 提交标示
        mPresenter.submitPrecheck(paramsMap);
    }

    @Override
    public void onSuccessPrecheck(String apiCode, String flag, PrecheckResultBean precheckResultBean) {
        ActivityCollector.getInstance().popActivity(WriteInfoMainActivity.class);
        ActivityCollector.getInstance().popActivity(NoStoreApplyConfirmActivity.class);
        Intent intent = new Intent(this, LoanApplyResultActivity.class);
        intent.putExtra("online_type", "0");
        intent.putExtra("product_id", "0");
        startActivity(intent);
        finish();
    }

    @Override
    public void onFail(String apiCode, String flag, String failMsg) {
        ToastAlone.showShortToast(this, failMsg);
    }

    @Override
    public void onError(String apiCode, String errorMsg) {
        ToastAlone.showShortToast(this, errorMsg);
    }
}
