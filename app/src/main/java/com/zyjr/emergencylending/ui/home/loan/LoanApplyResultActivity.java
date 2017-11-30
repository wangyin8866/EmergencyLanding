package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.ActivityCollector;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.ui.home.loan.offline.ApplyConfirmActivity;
import com.zyjr.emergencylending.ui.home.loan.offline.NoStoreApplyConfirmActivity;
import com.zyjr.emergencylending.ui.home.loan.online.ApplyToOfflineConfirmActivity;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neil on 2017/10/16
 * 备注: 借款订单提交结果 只展示一次?
 * 线上(审核中) |线下受理中
 */
public class LoanApplyResultActivity extends BaseActivity {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.iv_order_status)
    ImageView ivOrderStatus;
    @BindView(R.id.tv_step_two)
    TextView tvStepTwo;
    @BindView(R.id.tv_step_three)
    TextView tvStepThree;
    @BindView(R.id.tv_status_desc)
    TextView tvStatusDesc;


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

        initGetData();
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
        String online_type = intent.getStringExtra("online_type");
        String product_id = intent.getStringExtra("product_id");
        if (Constants.ZERO.equals(online_type)) {
            // 线上
            ivOrderStatus.setBackgroundResource(R.mipmap.audit_process);
            tvStepTwo.setText("认证信息");
            tvStatusDesc.setText("审核中");
            // 出栈管理
            ActivityCollector.getInstance().popActivity(LoanMainActivity.class);
            ActivityCollector.getInstance().popActivity(WriteInfoMainActivity.class);
            ActivityCollector.getInstance().popActivity(ApplyConfirmActivity.class);
            ActivityCollector.getInstance().popActivity(NoStoreApplyConfirmActivity.class);
        } else {
            // 线下
            ivOrderStatus.setBackgroundResource(R.mipmap.audit_process_a);
            tvStatusDesc.setText("受理中");
            tvStepTwo.setText("受理中");
            tvStepThree.setText("审核中");
            tvStepThree.setTextColor(getResources().getColor(R.color.front_text_color_hint));
            // 出栈管理
            ActivityCollector.getInstance().popActivity(LoanMainActivity.class);
            ActivityCollector.getInstance().popActivity(WriteInfoMainActivity.class);
            ActivityCollector.getInstance().popActivity(ApplyConfirmActivity.class);
            ActivityCollector.getInstance().popActivity(ApplyToOfflineConfirmActivity.class);
        }
    }

}
