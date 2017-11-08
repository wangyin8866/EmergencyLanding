package com.zyjr.emergencylending.ui.home.loan.offline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.StoreResultBean;
import com.zyjr.emergencylending.entity.WriteInfoBean;
import com.zyjr.emergencylending.ui.home.View.OfflineApplyView;
import com.zyjr.emergencylending.ui.home.loan.LoanApplyResultActivity;
import com.zyjr.emergencylending.ui.home.presenter.OfflineApplyPresenter;
import com.zyjr.emergencylending.utils.Arithmetic;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.widget.CustomSeekBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/14
 * 备注: 资料提交--无门店时页面,转线上件---->校验是否是首贷或续贷,申请确认
 */
public class NoStoreApplyConfirmActivity extends BaseActivity<OfflineApplyPresenter, OfflineApplyView> implements OfflineApplyView {

    @BindView(R.id.tv_loan_money)
    TextView tvLoanMoney; // 借款金额
    @BindView(R.id.tv_loan_period)
    TextView tvLoanPeriod; // 借款周期
    @BindView(R.id.seekbar_loan_money)
    CustomSeekBar seekbarMoney;
    @BindView(R.id.seekbar_loan_week)
    CustomSeekBar seekbarPeriod;
    @BindView(R.id.tv_loan_money_min)
    TextView tvMinLoadMoney;
    @BindView(R.id.tv_loan_money_max)
    TextView tvMaxLoadMoney;
    @BindView(R.id.tv_loan_week_min)
    TextView tvMinLoadWeek;
    @BindView(R.id.tv_loan_week_max)
    TextView tvMaxLoadWeek;

    @BindView(R.id.btn_submit_apply)
    Button btnSubmit;
    @BindView(R.id.ll_first_finance)
    LinearLayout llFirstFinance;  // 首贷
    @BindView(R.id.ll_refinance)
    LinearLayout llRefinance; // 续贷

    private String online_type = "";
    private String product_id = "";
    private String apply_amount = "";
    private String apply_periods = "";
    private String apply_zq = "";
    private String apply_periods_unit = "";
    private String renew_loan_type = "";
    private String moneyProgress = "";
    private String periodProgress = "";
    // 急速贷款
    private int minLoanPeriod = 0; // 最低借款期限
    private int minLoanPeriodUint = 0; // 最低借款期限
    private int maxLoanPeriod = 0; // 最高借款期限
    private int minLoanMoney = 0; // 最低借款金额
    private int maxLoanMoney = 0; // 最高借款金额
    private int loanMoney = 0; // 借款金额
    private String loanPeriod = ""; // 借款周期

    @Override
    protected OfflineApplyPresenter createPresenter() {
        return new OfflineApplyPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_no_store_apply_confirm);
        ButterKnife.bind(this);
        btnSubmit.setText("立即申请");

        initGetData();
        initListener();
    }


    @OnClick({R.id.btn_submit_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_apply:
                submitLoanInfo();
                break;
        }
    }

    private void initGetData() {
        Intent intent = getIntent();
        online_type = intent.getStringExtra("online_type"); // 产品类型
        product_id = intent.getStringExtra("product_id");  // 产品ID
        apply_amount = intent.getStringExtra("apply_amount"); // 申请金额
        apply_periods = intent.getStringExtra("apply_periods"); // 申请期数
        apply_zq = intent.getStringExtra("apply_zq"); // 申请期数间隔
        apply_periods_unit = intent.getStringExtra("apply_periods_unit"); // 申请周期单位
        renew_loan_type = intent.getStringExtra("renew_loan_type"); // 0.首贷;3.续贷
        moneyProgress = intent.getStringExtra("moneyProgress"); // 金额进度
        periodProgress = intent.getStringExtra("periodProgress"); // 周期进度
        LogUtils.d("【接收数据】:" +
                "\n申请金额apply_amount:" + apply_amount +
                "\n申请期数apply_periods:" + apply_periods +
                "\n申请期数间隔apply_zq:" + apply_zq +
                "\n申请周期单位apply_periods_unit:" + apply_periods_unit +
                "\n产品id:" + product_id +
                "\n首续贷标识renew_loan_type:" + renew_loan_type +
                "\n产品类型online_type:" + online_type);
        if (renew_loan_type.equals("0")) {
            // 首贷
            llFirstFinance.setVisibility(View.VISIBLE);
            llRefinance.setVisibility(View.GONE);
            if (apply_periods_unit.equals("1")) {
                // 天 作为期
                tvLoanPeriod.setText(apply_zq + "天");
            } else if (apply_periods_unit.equals("2")) {
                tvLoanPeriod.setText(apply_periods + "周");
            }
            tvLoanMoney.setText(apply_amount + "元");
        } else if (renew_loan_type.equals("3")) {
            // 续贷
            llRefinance.setVisibility(View.VISIBLE);
            llFirstFinance.setVisibility(View.GONE);
            minLoanPeriod = 14; // 14天
            maxLoanPeriod = 15; // 15周
            minLoanPeriodUint = 1;
            minLoanMoney = 500;
            maxLoanMoney = 5000;
            initSeekMoney(20, minLoanMoney, maxLoanMoney);
            initSeekPeriod(34, minLoanPeriod, maxLoanPeriod, minLoanPeriodUint);
        }

    }

    public void initSeekMoney(int progress, int minMoney, int maxMoney) {
        seekbarMoney.setType(0);
        seekbarMoney.setMONEY_MIN(minMoney);
        seekbarMoney.setMONEY_MAX(maxMoney);
        if (seekbarMoney != null) {
            seekbarMoney.setProgress(progress);
        }
        tvMinLoadMoney.setText(minMoney + "元");
        tvMaxLoadMoney.setText(maxMoney + "元");
    }

    /**
     * @param progress
     * @param minPeriod 最小周期
     * @param maxPeriod 最大周期
     * @param minUnit   周期单位 1.天;2.周
     */
    public void initSeekPeriod(int progress, int minPeriod, int maxPeriod, int minUnit) {
        seekbarPeriod.setType(1);
        seekbarPeriod.setPERIOD_MIN(minPeriod);
        seekbarPeriod.setPERIOD_MIN_UNIT(minUnit);
        seekbarPeriod.setPERIOD_MAX(maxPeriod);
        if (seekbarPeriod != null) {
            seekbarPeriod.setProgress(progress);
        }
        if (minUnit == 1) {
            tvMinLoadWeek.setText(minPeriod + "天");
        } else {
            tvMinLoadWeek.setText(minPeriod + "周");
        }
        loanPeriod = Arithmetic.progressToWeek(progress, minPeriod, maxPeriod, minUnit);
        tvMaxLoadWeek.setText(maxPeriod + "周");
    }

    private void initListener() {
        seekbarMoney.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                loanMoney = Arithmetic.progressToMoney(progress, minLoanMoney, maxLoanMoney);
                LogUtils.e("借款金额:" + loanMoney);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekbarPeriod.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                loanPeriod = Arithmetic.progressToWeek(progress, minLoanPeriod, maxLoanPeriod, minLoanPeriodUint);
                LogUtils.e("借款周期:" + loanPeriod);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void submitLoanInfo() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("online_type", "0"); // 产品类型
        paramsMap.put("product_id", "0"); // 产品ID
        if (renew_loan_type.equals("0")) {
            // 首贷
            paramsMap.put("apply_amount", apply_amount); // 申请金额
            paramsMap.put("apply_periods", apply_periods); // 申请期数
            paramsMap.put("apply_zq", apply_zq); // 申请期数间隔
            paramsMap.put("apply_periods_unit", apply_periods_unit); // 申请周期单位
            paramsMap.put("renew_loan_type", renew_loan_type);  // 首贷标识
        } else if (renew_loan_type.equals("3")) {
            // 续贷
            paramsMap.put("apply_amount", Arithmetic.progressToMoney(seekbarMoney.getProgress(), minLoanMoney, maxLoanMoney) + ""); // 申请金额
            loanPeriod = Arithmetic.progressToWeek(seekbarPeriod.getProgress(), minLoanPeriod, maxLoanPeriod, minLoanPeriodUint);
            if (loanPeriod.contains("天")) {
                int indexEnd = loanPeriod.indexOf("天");
                loanPeriod = loanPeriod.substring(0, indexEnd);
                paramsMap.put("apply_periods", "1"); // 申请期数 ps:14天，14天为1期,共1期,所以期数间隔为14天
                paramsMap.put("apply_zq", "14"); // 申请期数间隔
                paramsMap.put("apply_periods_unit", "1"); // 申请周期单位
            } else if (loanPeriod.contains("周")) {
                int indexEnd = loanPeriod.indexOf("周");
                loanPeriod = loanPeriod.substring(0, indexEnd);
                paramsMap.put("apply_periods", loanPeriod); // 申请期数
                paramsMap.put("apply_zq", "1"); // 申请期数间隔
                paramsMap.put("apply_periods_unit", "2"); // 申请周期单位
            }
            paramsMap.put("renew_loan_type", renew_loan_type);  // 续贷标识
        }
        if (!BaseApplication.isSalesman.equals(Config.USER_SALESMAN)) {
            paramsMap.put("contact_list", new Gson().toJson(CommonUtils.queryContactPhoneNumber(this))); // 通讯录集合
        }
        mPresenter.submitLoanInformation(paramsMap);
    }


    @Override
    public void onSuccessGetLocalStoreList(String apiCode, List<StoreResultBean.StoreBean> beanList) {

    }

    @Override
    public void onSuccessSubmit(String apiCode, String msg) {
        ToastAlone.showLongToast(this, msg);
        Intent intent = new Intent(this, LoanApplyResultActivity.class);
        intent.putExtra("online_type", online_type);
        intent.putExtra("product_id", product_id);
        startActivity(intent);
    }

    @Override
    public void onFail(String apiCode, String failMsg) {
        ToastAlone.showLongToast(this, failMsg);
    }

    @Override
    public void onError(String apiCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
    }
}
