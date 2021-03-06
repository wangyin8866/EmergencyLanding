package com.zyjr.emergencylending.ui.salesman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.ui.home.loan.WriteInfoMainActivity;
import com.zyjr.emergencylending.utils.Arithmetic;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.widget.CustomSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author wangyin
 * @date 2017/10/18
 * 立即借款页面
 */

public class BorrowActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.seekbar_loan_money)
    CustomSeekBar seekbarMoney;
    @BindView(R.id.seekbar_loan_week)
    CustomSeekBar seekbarWeek;
    @BindView(R.id.tv_loan_money_min)
    TextView tvMinLoadMoney;
    @BindView(R.id.tv_loan_money_max)
    TextView tvMaxLoadMoney;
    @BindView(R.id.tv_loan_week_min)
    TextView tvMinLoadWeek;
    @BindView(R.id.tv_loan_week_max)
    TextView tvMaxLoadWeek;

    @BindView(R.id.btn_apply_quickly)
    Button btnApplyQuicky;
    private int money = 0;
    private String week = "";
    private int moneyProgress = 1;
    private int weekProgress = 1;
    private int MIN_WEEK = 0;
    private int MAX_WEEK = 0;
    private int MIN_MONEY = 0;
    private int MAX_MONEY = 0;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_activity_loan_main);
        ButterKnife.bind(this);
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });
        init();
        initListener();
    }

    private void init() {
        MIN_WEEK = 15;
        MAX_WEEK = 52;
        MIN_MONEY = 5000;
        MAX_MONEY = 30000;
        initSeekMoney(16, MIN_MONEY, MAX_MONEY);
        initSeekWeek(8, MIN_WEEK, MAX_WEEK, 2);
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
                week = Arithmetic.progressToWeek(weekProgress, MIN_WEEK, MAX_WEEK, 2);
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

    public void initSeekWeek(int progress, int minWeek, int maxWeek, int minUnit) {
        seekbarWeek.setType(1);
        seekbarWeek.setPERIOD_MIN_UNIT(2);
        seekbarWeek.setPERIOD_MIN(minWeek);
        seekbarWeek.setPERIOD_MAX(maxWeek);
        if (seekbarWeek != null) {
            seekbarWeek.setProgress(progress);
        }

        tvMinLoadWeek.setText(minWeek + "周");
        week = Arithmetic.progressToWeek(progress, minWeek, maxWeek, minUnit);
        tvMaxLoadWeek.setText(maxWeek + "周");
    }

    @OnClick(R.id.btn_apply_quickly)
    public void onViewClicked() {
        String str = week.substring(0, week.length() - 1);
        Intent intent = new Intent(this, WriteInfoMainActivity.class);
        intent.putExtra("apply_amount", money + "");
        intent.putExtra("apply_periods", str);
        intent.putExtra("apply_zq", "1");
        intent.putExtra("apply_periods_unit", "2");
        intent.putExtra("online_type", "1");
        intent.putExtra("product_id", "1");
        startActivity(intent);
        finish();
    }
}
