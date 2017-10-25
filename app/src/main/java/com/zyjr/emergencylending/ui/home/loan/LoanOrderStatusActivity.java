package com.zyjr.emergencylending.ui.home.loan;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.LoanOrderBean;
import com.zyjr.emergencylending.entity.WriteInfoBean;
import com.zyjr.emergencylending.ui.home.View.LoanOrderView;
import com.zyjr.emergencylending.ui.home.presenter.LoanOrderPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/16
 * 备注: 借款订单状态
 * 线上急速借款(填写资料:1、认证中:2、审核中:3、领取金额:4、放款中:5、还款中:6)
 * 传统借款(填写资料:1、认证中:2、受理中:3、领取金额:4、放款中:5、还款中:6)
 */
public class LoanOrderStatusActivity extends BaseActivity<LoanOrderPresenter,LoanOrderView> implements LoanOrderView {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.iv_order_status)
    ImageView ivOrderStatus;  // 订单状态图
    @BindView(R.id.tv_order_status1)
    TextView tvOrderStatus1; // 订单状态1 填写资料
    @BindView(R.id.tv_order_status2)
    TextView tvOrderStatus2; // 订单状态2 认证中
    @BindView(R.id.tv_order_status3)
    TextView tvOrderStatus3; // 订单状态3 审核中
    @BindView(R.id.tv_order_status4)
    TextView tvOrderStatus4; // 订单状态4 领取金额
    @BindView(R.id.tv_order_status5)
    TextView tvOrderStatus5; // 订单状态5 放款中
    @BindView(R.id.tv_order_status6)
    TextView tvOrderStatus6; // 订单状态6 还款中
    @BindView(R.id.tv_loan_money)
    TextView tvLoadMoney; // 借款申请金额
    @BindView(R.id.tv_loan_week)
    TextView tvLoadWeek; // 借款申请周期

    @BindView(R.id.iv_order_status_icon)
    ImageView ivOrderStatusIcon;
    @BindView(R.id.btn_status_operate)
    Button btnOrderOperate;
    private LoanOrderBean loanOrderBean = null;

    @Override
    protected LoanOrderPresenter createPresenter() {
        return new LoanOrderPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_order_status);
        ButterKnife.bind(this);

        init();
        initData();
    }

    private void initData() {

    }

    @OnClick({R.id.tv_order_status1, R.id.tv_order_status2, R.id.tv_order_status3, R.id.tv_order_status4, R.id.tv_order_status5, R.id.tv_order_status6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_order_status2:  // 认证中 2
                setOrderStatusInfo(1, 2);
                break;
            case R.id.tv_order_status3:  // 审核中
                setOrderStatusInfo(1, 3);
                break;
            case R.id.tv_order_status4:  // 领取金额
                setOrderStatusInfo(1, 4);
                break;
            case R.id.tv_order_status5:  // 放款中
                setOrderStatusInfo(1, 5);
                break;
            case R.id.tv_order_status6:  // 还款中
                setOrderStatusInfo(1, 6);
                break;
        }
    }


    private void setOrderStatusInfo(int flag, int status) {
        setOrderStatus(flag, status);
    }


    private void setOrderStatus(int flag, int status) {
        Drawable drawable = null;
        int orderStatusIocn = 0;
        if (status == 2 || status == 1) {
            drawable = getResources().getDrawable(R.mipmap.orderprocess_a);
            orderStatusIocn = R.mipmap.emptypage_authentication;
            setTextView(tvOrderStatus2, "认证中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus3, "审核中", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus4, "领取金额", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus5, "放款中", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus6, "还款中", getResources().getColor(R.color.order_uncomplected_color));
            btnOrderOperate.setText("前往认证");
            btnOrderOperate.setEnabled(true);
        } else if (status == 3) {
            drawable = getResources().getDrawable(R.mipmap.orderprocess_b);
            orderStatusIocn = R.mipmap.emptypage_examine;
            setTextView(tvOrderStatus2, "认证中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus3, "审核中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus4, "领取金额", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus5, "放款中", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus6, "还款中", getResources().getColor(R.color.order_uncomplected_color));
            btnOrderOperate.setText("审核中");
            btnOrderOperate.setEnabled(false);
        } else if (status == 4) {
            drawable = getResources().getDrawable(R.mipmap.orderprocess_c);
            orderStatusIocn = R.mipmap.emptypage_getthemoney;
            setTextView(tvOrderStatus2, "认证中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus3, "审核中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus4, "领取金额", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus5, "放款中", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus6, "还款中", getResources().getColor(R.color.order_uncomplected_color));
            btnOrderOperate.setText("领取金额");
            btnOrderOperate.setEnabled(true);
        } else if (status == 5) {
            drawable = getResources().getDrawable(R.mipmap.orderprocess_d);
            orderStatusIocn = R.mipmap.emptypage_loan;
            setTextView(tvOrderStatus2, "认证中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus3, "审核中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus4, "领取金额", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus5, "放款中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus6, "还款中", getResources().getColor(R.color.order_uncomplected_color));
            btnOrderOperate.setText("放款中");
            btnOrderOperate.setEnabled(false);
        } else if (status == 6) {
            drawable = getResources().getDrawable(R.mipmap.orderprocess_e);
            orderStatusIocn = R.mipmap.emptypage_repayment;
            setTextView(tvOrderStatus2, "认证中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus3, "审核中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus4, "领取金额", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus5, "放款中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus6, "还款中", getResources().getColor(R.color.white));
            btnOrderOperate.setText("立即还款");
            btnOrderOperate.setEnabled(true);
        }
        ivOrderStatusIcon.setImageResource(orderStatusIocn);
        ivOrderStatus.setBackgroundDrawable(drawable);
    }

    private void setTextView(TextView tv, String content, int color) {
        tv.setText(content);
        tv.setTextColor(color);
    }

    @Override
    public void onSuccessGet(String returnCode, LoanOrderBean model) {
        loanOrderBean = model;

    }

    @Override
    public void onFail(String errorMessage) {

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
