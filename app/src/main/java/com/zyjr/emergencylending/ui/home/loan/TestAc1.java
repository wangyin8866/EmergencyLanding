package com.zyjr.emergencylending.ui.home.loan;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.ImageView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.utils.CommonUtils;
import com.zyjr.emergencylending.widget.step.HorizontalStepView;
import com.zyjr.emergencylending.widget.step.StepBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/13
 * 备注: 测试
 */

public class TestAc1 extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_order_status);
        ButterKnife.bind(this);
    }

}
