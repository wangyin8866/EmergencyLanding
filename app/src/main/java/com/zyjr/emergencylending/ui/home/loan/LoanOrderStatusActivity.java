package com.zyjr.emergencylending.ui.home.loan;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.ImageView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.widget.step.HorizontalStepView;
import com.zyjr.emergencylending.widget.step.StepBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/16
 * 备注: 借款订单状态
 */
public class LoanOrderStatusActivity extends BaseActivity{

    @BindView(R.id.step_view)
    HorizontalStepView setpview;
    @BindView(R.id.iv_order_status_icon)
    ImageView ivOrderStatusIcon;
    @BindView(R.id.btn_status_operate)
    Button btnOrderOperate;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_order_status);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        ArrayList<StepBean> stepBeen = CommonUtils.setOrderStatus(5);
        setpview.setStepViewTexts(stepBeen)//总步骤
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorComplectingPosition(5)
                .setStepsViewIndicatorCompletedLineColor(getResources().getColor(R.color.white))//设置完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(getResources().getColor(R.color.order_uncomplected_color))//设置未完成线的颜色
                .setStepViewComplectedTextColor(getResources().getColor(android.R.color.white))//设置完成文字的颜色
                .setStepViewUnComplectedTextColor(getResources().getColor(R.color.order_uncomplected_color))//设置未完成文字的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getBaseContext(), R.mipmap.complted))//设置已完成的图片
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getBaseContext(), R.mipmap.default_icon))//设置默认图片
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getBaseContext(), R.mipmap.attention));//设置正在进行中图片
//        ivOrderStatusIcon.setImageResource(R.mipmap.about_icon_qq);
        btnOrderOperate.setEnabled(false);
    }


}
