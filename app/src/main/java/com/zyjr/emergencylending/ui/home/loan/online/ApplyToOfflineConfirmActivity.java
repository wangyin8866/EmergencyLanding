package com.zyjr.emergencylending.ui.home.loan.online;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.adapter.SupportStoreAdapter;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.StoreBean;
import com.zyjr.emergencylending.utils.Arithmetic;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.widget.BubbleSeekBar;
import com.zyjr.emergencylending.widget.CustomSeekBar;
import com.zyjr.emergencylending.widget.SelectorImageView;
import com.zyjr.emergencylending.widget.recyc.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/14
 * 备注: 线上申请 转线下
 */
public class ApplyToOfflineConfirmActivity extends BaseActivity {

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
    @BindView(R.id.rv_store_supported)
    RecyclerView rvStoreSupported;
    @BindView(R.id.pb_store_supported_loading)
    ProgressBar pbLoadingStore;
    @BindView(R.id.btn_submit_apply)
    Button btnSubmitApply;
    private List<StoreBean> storeBeanList = null;
    private SupportStoreAdapter adapter = null;
    private StoreBean storeBean = null;

    private String online_type = ""; // 产品类型
    private String product_id = ""; // 产品id
    private int moneyProgress = 1; // 金额进度
    private int periodProgress = 1; // 借款周期进度
    private int minLoanPeriod = 0; // 最低借款期限
    private int minLoanPeriodUint = 0; // 最低借款期限
    private int maxLoanPeriod = 0; // 最高借款期限
    private int minLoanMoney = 0; // 最低借款金额
    private int maxLoanMoney = 0; // 最高借款金额
    private int loanMoney = 0; // 借款金额
    private String loanPeriod = ""; // 借款周期
    private String loanPeriodUnit = ""; // 周期单位 1:天;2:周

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_apply_confirm_to_offline);
        ButterKnife.bind(this);

        initData();
        initListener();
    }

    @OnClick({R.id.btn_submit_apply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_apply:
                validateData();


                break;
        }
    }


    private void initData() {
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });

        storeBeanList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            StoreBean item = new StoreBean("L上海市" + i, "百度文库是百度发布的供胜多负少大法师", "118号" + i, false);
            storeBeanList.add(item);
        }
        pbLoadingStore.setVisibility(View.GONE);
        adapter = new SupportStoreAdapter(R.layout.rv_item_store_info, storeBeanList);
        rvStoreSupported.setLayoutManager(new LinearLayoutManager(this));
        rvStoreSupported.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.auth_success)));
        rvStoreSupported.setAdapter(adapter);
        rvStoreSupported.setVisibility(View.VISIBLE);
        adapter.bindToRecyclerView(rvStoreSupported);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < storeBeanList.size(); i++) {
                    SelectorImageView v = (SelectorImageView) adapter.getViewByPosition(i, R.id.iv_item_store_selected);
                    v.toggle(false);
                }
                storeBean = (StoreBean) adapter.getItem(position);
                SelectorImageView v = (SelectorImageView) adapter.getViewByPosition(position, R.id.iv_item_store_selected);
                v.toggle(true);
            }
        });
        online_type = "1";
        product_id = "1";
        minLoanPeriod = 15;
        maxLoanPeriod = 52;
        loanPeriodUnit = "2";
        minLoanMoney = 5000;
        maxLoanMoney = 30000;
        initSeekMoney(16, minLoanMoney, maxLoanMoney);
        initSeekPeriod(8, minLoanPeriod, maxLoanPeriod, 2);
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
                loanPeriod = Arithmetic.progressToWeek(periodProgress, minLoanPeriod, maxLoanPeriod, 2);
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

    private void validateData() {
        if (storeBean == null) {
            ToastAlone.showLongToast(this, "请选择您所在的城市");
            return;
        }

    }

    private void submitTransformApply(){
        loanMoney = Arithmetic.progressToMoney(seekbarMoney.getProgress(), minLoanMoney, maxLoanMoney);
        loanPeriod = Arithmetic.progressToWeek(seekbarPeriod.getProgress(), minLoanPeriod, maxLoanPeriod, 2);
        int indexEnd = loanPeriod.indexOf("周");
        loanPeriod = loanPeriod.substring(0, indexEnd);
        LogUtils.d("传递借款参数->" + "online_type:" + online_type + ",loanMoney:" + loanMoney + ",loanPeriod:" + loanPeriod + ",loanPeriodUnit:" + loanPeriodUnit + ",product_id:" + product_id);
    }

}
