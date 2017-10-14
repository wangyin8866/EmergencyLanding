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
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.ProIntroduceBean;
import com.zyjr.emergencylending.utils.Arithmetic;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.widget.BubbleSeekBar;
import com.zyjr.emergencylending.widget.recyc.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/11
 * 备注: 借款(急速 和传统 )
 */
public class LoanMainActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.seekbar_loan_money)
    BubbleSeekBar seekbarMoney;
    @BindView(R.id.seekbar_loan_week)
    BubbleSeekBar seekbarWeek;
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
    @BindView(R.id.layout_fastloan_support_cities)
    LinearLayout llFastloanSupportCities; // 急速借款支持城市
    @BindView(R.id.layout_traditional_support_cities)
    LinearLayout llTraditionalSupportCities; // 传统借款支持城市

    private String flag = "";
    private int money = 0;
    private int week = 0;
    private int moneyProgress = 1;
    private int weekProgress = 1;
    private int moneyCritical = 30;
    private int MIN_WEEK = 0;
    private int MAX_WEEK = 0;
    private int MIN_MONEY = 0;
    private int MAX_MONEY = 0;
    private List<ProIntroduceBean> proIntroduceList = null;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_main);
        ButterKnife.bind(this);
        initData();
        initGetData();
        initListener();
        LogUtils.e("weekProgress:" + weekProgress + ",moneyProgress:" + moneyProgress);
    }

    private void initData() {
        proIntroduceList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ProIntroduceBean item = new ProIntroduceBean("新产品" + i, "快来体验" + i);
            proIntroduceList.add(item);
        }
        ProIntroduceAdapter adapter = new ProIntroduceAdapter(R.layout.rv_item_pro_introduce, proIntroduceList);
        rvProductIntroduce.setLayoutManager(new LinearLayoutManager(this));
        rvProductIntroduce.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 12, getResources().getColor(R.color.white)));
        rvProductIntroduce.setAdapter(adapter);
    }


    private void initGetData() {
        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
        if (flag.equals("online")) {
            topBar.setTitle("急速借款");
            MIN_WEEK = 2;
            MAX_WEEK = 15;
            MIN_MONEY = 500;
            MAX_MONEY = 5000;
            initSeekMoney(10, MIN_MONEY, MAX_MONEY);
            initSeekWeek(1, MIN_WEEK, MAX_WEEK, flag);
        } else if (flag.equals("offline")) {
            topBar.setTitle("传统借款");
            MIN_WEEK = 15;
            MAX_WEEK = 52;
            MIN_MONEY = 5000;
            MAX_MONEY = 30000;
            initSeekMoney(10, MIN_MONEY, MAX_MONEY);
            initSeekWeek(1, MIN_WEEK, MAX_WEEK, flag);
            llFastloanSupportCities.setVisibility(View.GONE);
            llTraditionalSupportCities.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.btn_apply_quickly})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_apply_quickly:
                startActivity(new Intent(LoanMainActivity.this, WriteInfoMainActivity.class));
                break;
        }
    }

    private void initListener() {
        seekbarMoney.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                moneyProgress = progress;
                money = Arithmetic.progressToMoney(moneyProgress, MIN_MONEY, MAX_MONEY);
                LogUtils.e("金额:" + money);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekbarWeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weekProgress = progress;
                week = Arithmetic.progressToWeek(weekProgress, MIN_WEEK, MAX_WEEK);
                LogUtils.e("周期:" + week);
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
        money = Arithmetic.progressToMoney(progress, minMoney, maxMoney);
        tvMinLoadMoney.setText(minMoney + "元");
        tvMaxLoadMoney.setText(maxMoney + "元");
    }

    public void initSeekWeek(int progress, int minWeek, int maxWeek, String flag) {
        seekbarWeek.setType(1);
        seekbarWeek.setWEEK_MIN(minWeek);
        seekbarWeek.setWEEK_MAX(maxWeek);
        if (seekbarWeek != null) {
            seekbarWeek.setProgress(progress);
        }
        if (flag.equals("online")) {
            seekbarWeek.setONLINE(1); //标识是线下 还是线上,当如果是线上时,开始显示的是14天(2周),具体以借口返回数据为准
            tvMinLoadWeek.setText(minWeek * 7 + "天");
        } else {
            tvMinLoadWeek.setText(minWeek + "周");
        }
        week = Arithmetic.progressToWeek(progress, minWeek, maxWeek);
        tvMaxLoadWeek.setText(maxWeek + "周");
    }

    public void defaultSeekWeek() {
        seekbarWeek.setType(1);
        seekbarWeek.setONLINE(1);
        seekbarWeek.setWEEK_MIN(2);
        seekbarWeek.setWEEK_MAX(15);
    }

}
