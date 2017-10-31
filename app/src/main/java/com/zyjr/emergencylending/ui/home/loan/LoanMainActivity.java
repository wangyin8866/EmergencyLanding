package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.ProIntroduceAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.SupportCityBean;
import com.zyjr.emergencylending.ui.home.View.ProductInfoView;
import com.zyjr.emergencylending.ui.home.presenter.ProductInfoPresenter;
import com.zyjr.emergencylending.utils.Arithmetic;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.widget.CustomSeekBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/11
 * 备注: 借款(急速 和传统 )
 * 1.网络请求：获取产品介绍--->包含额度
 */
public class LoanMainActivity extends BaseActivity<ProductInfoPresenter, ProductInfoView> implements ProductInfoView {

    @BindView(R.id.top_bar)
    TopBar topBar;
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
    @BindView(R.id.rv_product_introduce)
    RecyclerView rvProductIntroduce;

    @BindView(R.id.btn_apply_quickly)
    Button btnApplyQuicky;
    @BindView(R.id.layout_online_support_cities)
    LinearLayout llOnlineSupCities; // 线上借款支持城市
    @BindView(R.id.layout_offline_support_cities)
    LinearLayout llOfflineSupCities; // 传统借款支持城市
    @BindView(R.id.tv_offline_support_cities_desc)
    TextView tvOfflineSupCities; // 线下支持城市

    private String flag = "";
    private String online_type = ""; // 产品类型
    private String product_id = ""; // 产品id
    // 急速贷款
    private int moneyProgress = 1; // 金额进度
    private int periodProgress = 1; // 借款周期进度
    private int minLoanPeriod = 0; // 最低借款期限
    private int minLoanPeriodUint = 0; // 最低借款期限
    private int maxLoanPeriod = 0; // 最高借款期限
    private int minLoanMoney = 0; // 最低借款金额
    private int maxLoanMoney = 0; // 最高借款金额
    // 提交
    private int loanMoney = 0; // 借款金额
    private String loanPeriod = ""; // 借款周期
    private String loanPeriodUnit = "";
    private List<String> proIntroduceList = null;


    @Override
    protected ProductInfoPresenter createPresenter() {
        return new ProductInfoPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_main);
        ButterKnife.bind(this);

        init();
        initGetData();
        initData();
        initListener();
    }


    @OnClick({R.id.btn_apply_quickly, R.id.layout_online_support_cities})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_apply_quickly:
                // TODO 携带 借款数据到下个页面
                Intent intent = new Intent(this, WriteInfoMainActivity.class);
                loanMoney = Arithmetic.progressToMoney(seekbarMoney.getProgress(), minLoanMoney, maxLoanMoney);
                loanPeriod = Arithmetic.progressToWeek(seekbarPeriod.getProgress(), minLoanPeriod, maxLoanPeriod, minLoanPeriodUint);
                intent.putExtra("online_type", online_type);
                intent.putExtra("product_id", product_id);
                intent.putExtra("loanMoney", loanMoney + "");
                if (loanPeriod.contains("天")) {
                    int indexEnd = loanPeriod.indexOf("天");
                    loanPeriod = loanPeriod.substring(0, indexEnd);
                    loanPeriodUnit = "1";
                    intent.putExtra("loanPeriod", loanPeriod + "");
                    intent.putExtra("loanPeriodUnit", loanPeriodUnit);
                } else if (loanPeriod.contains("周")) {
                    int indexEnd = loanPeriod.indexOf("周");
                    loanPeriod = loanPeriod.substring(0, indexEnd);
                    loanPeriodUnit = "2";
                    intent.putExtra("loanPeriod", loanPeriod + "");
                    intent.putExtra("loanPeriodUnit", loanPeriodUnit);
                }
                LogUtils.d("传递借款参数->" + "online_type:" + online_type + ",loanMoney:" + loanMoney + ",loanPeriod:" + loanPeriod + ",loanPeriodUnit:" + loanPeriodUnit + ",product_id:" + product_id);
                startActivity(intent);
                break;
            case R.id.layout_online_support_cities:
                startActivity(new Intent(LoanMainActivity.this, SupportCitiesActivity.class));
                break;
        }
    }

    private void initData() {
        // TODO 获取产品介绍


    }


    private void initGetData() {
        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
        if (flag.equals("online")) {
            topBar.setTitle("急速借款");
            online_type = "0";
            product_id = "0";
            minLoanPeriod = 14; // 14天
            maxLoanPeriod = 15; // 15周
            minLoanPeriodUint = 1;
            minLoanMoney = 500;
            maxLoanMoney = 5000;
            initSeekMoney(10, minLoanMoney, maxLoanMoney);
            initSeekPeriod(1, minLoanPeriod, maxLoanPeriod, minLoanPeriodUint);
            llOnlineSupCities.setVisibility(View.VISIBLE);
            llOfflineSupCities.setVisibility(View.GONE);
            loadingProIntroduce("0");
        } else if (flag.equals("offline")) {
            topBar.setTitle("传统借款");
            online_type = "1";
            product_id = "1";
            minLoanPeriod = 15;
            maxLoanPeriod = 52;
            minLoanPeriodUint = 2;
            minLoanMoney = 5000;
            maxLoanMoney = 30000;
            initSeekMoney(10, minLoanMoney, maxLoanMoney);
            initSeekPeriod(1, minLoanPeriod, maxLoanPeriod, minLoanPeriodUint);
            llOnlineSupCities.setVisibility(View.GONE);
            llOfflineSupCities.setVisibility(View.VISIBLE);
            loadingProIntroduce("1");
        }
    }


    private void initListener() {
        seekbarMoney.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                moneyProgress = progress;
                loanMoney = Arithmetic.progressToMoney(moneyProgress, minLoanMoney, maxLoanMoney);
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
                periodProgress = progress;
                loanPeriod = Arithmetic.progressToWeek(periodProgress, minLoanPeriod, maxLoanPeriod, minLoanPeriodUint);
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

    public void initSeekMoney(int progress, int minMoney, int maxMoney) {
        seekbarMoney.setType(0);
        seekbarMoney.setMONEY_MIN(minMoney);
        seekbarMoney.setMONEY_MAX(maxMoney);
        if (seekbarMoney != null) {
            seekbarMoney.setProgress(progress);
        }
        loanMoney = Arithmetic.progressToMoney(progress, minMoney, maxMoney);
        LogUtils.d("借款金额:" + loanMoney);
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
        LogUtils.d("借款周期:" + loanPeriod);
        tvMaxLoadWeek.setText(maxPeriod + "周");
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

    private void loadingProIntroduce(String type) {
        Map<String, String> map = new HashMap<>();
        map.put("product_id", type);
        mPresenter.getProIntroduce(map);
    }

    @Override
    public void onSuccessGetSupportCity(String returnCode, List<SupportCityBean> cityList) {
        String str = "";
        for (SupportCityBean item : cityList) {
            str += item.getCity_name() + "、";
        }
        tvOfflineSupCities.setText(str);
    }

    @Override
    public void onSuccessGetIntro(String returnCode, List<String> result) {
        proIntroduceList = result;
        ProIntroduceAdapter adapter = new ProIntroduceAdapter(R.layout.rv_item_pro_introduce, proIntroduceList);
        rvProductIntroduce.setLayoutManager(new LinearLayoutManager(this));
//        rvProductIntroduce.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 12, getResources().getColor(R.color.white)));
        rvProductIntroduce.setAdapter(adapter);
        if (flag.equals("offline")) {
            // TODO 获取支持城市
            Map<String, String> map = new HashMap<>();
            mPresenter.getSupportCities(map);
        }
    }

    @Override
    public void onFail(String returnCode, String errorMessage) {

    }

    @Override
    public void onError(String returnCode, String errorMsg) {

    }

}
