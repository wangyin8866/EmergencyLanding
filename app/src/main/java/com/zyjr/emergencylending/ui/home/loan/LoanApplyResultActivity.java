package com.zyjr.emergencylending.ui.home.loan;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/16
 * 备注: 借款订单提交结果 只展示一次?
 * 线上(审核中) |线下受理中
 */
public class LoanApplyResultActivity extends BaseActivity{
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.iv_order_status)
    ImageView ivOrderStatus;
    @BindView(R.id.tv_step_two)
    TextView tvStepTwo;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_apply_result);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
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

}
