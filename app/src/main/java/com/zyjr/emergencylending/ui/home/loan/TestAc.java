package com.zyjr.emergencylending.ui.home.loan;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.utils.Arithmetic;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.widget.BubbleSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/13
 * 备注: 测试
 */

public class TestAc extends Activity {

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        ButterKnife.bind(this);
        initSeekMoney();
        initSeekWeek();
        initListener();
    }

    private static int money = 2000;
    private static int week = 5;
    private static int moneyProgress = 12;
    private static int weekProgress = 0;
    private static int moneyCritical = 30;
    private static int MIN_WEEK = 2;
    private static int MAX_WEEK = 15;
    private static int MIN_MONEY = 500;
    private static int MAX_MONEY = 5000;

    public void initSeekMoney() {
        seekbarMoney.setMONEY_MIN(MIN_MONEY);
        seekbarMoney.setMONEY_MAX(MAX_MONEY);
        if (seekbarMoney != null) {
            seekbarMoney.setProgress(moneyProgress);
        }
        money = Arithmetic.progressToMoney(moneyProgress, MIN_MONEY, MAX_MONEY);
        tvMinLoadMoney.setText(MIN_MONEY + "元");
        tvMaxLoadMoney.setText(MAX_MONEY + "元");
    }

    public void initSeekWeek() {
        seekbarWeek.setONLINE(1); //标识是线下 还是线上,当如果是线上时,开始显示的是14天(2周),具体以借口返回数据为准
        seekbarWeek.setWEEK_MIN(MIN_WEEK);
        seekbarWeek.setWEEK_MAX(MAX_WEEK);
        if (seekbarWeek != null) {
            seekbarWeek.setProgress(1);
        }
        week = Arithmetic.progressToWeek(weekProgress, MIN_WEEK, MAX_WEEK);
        tvMinLoadWeek.setText(MIN_WEEK * 7 + "天");
        tvMaxLoadWeek.setText(MAX_WEEK + "周");
    }

    public void defaultSeekWeek() {
        seekbarWeek.setONLINE(1);
        seekbarWeek.setWEEK_MIN(2);
        seekbarWeek.setWEEK_MAX(15);
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


}
