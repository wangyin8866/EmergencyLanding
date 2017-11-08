package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyjr.emergencylending.MainActivity;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.LoanOrderBean;
import com.zyjr.emergencylending.ui.home.View.LoanOrderView;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.BankcardInfoActivity;
import com.zyjr.emergencylending.ui.home.presenter.LoanOrderPresenter;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/16
 * 备注: 借款订单状态
 * 线上急速借款(填写资料:1、认证中:2、审核中:3、领取金额:4、放款中:5、还款中:6)
 * 传统借款(填写资料:1、认证中:2、受理中:3、领取金额:4、放款中:5、还款中:6)
 * step_status                                         |    order_status
 * 1.填写资料                                               4.录件暂存
 * 2.认证                                                   0.初审通过
 * 3.审核中(线下:初审通过,已拒绝;线上:步骤状态控制)           2.终审通过
 * 4.领取金额(终审通过)                                      3.面签通过
 * 5.放款中(面签通过)                                        9.已拒绝
 * 6.还款中(已放款)                                          1.已放款
 * 7.受理中(对应线下录件暂存)                                 6.结清
 * *                                                         7.放款失败
 * *                                                         10.订单待提交
 * <p>
 */
public class LoanOrderStatusActivity extends BaseActivity<LoanOrderPresenter, LoanOrderView> implements LoanOrderView {
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
    @BindView(R.id.tv_loan_period)
    TextView tvLoadPeriod; // 借款申请周期
    @BindView(R.id.tv_order_desc1)
    TextView tvOrderDesc1;  // 订单描述1
    @BindView(R.id.tv_order_desc2)
    TextView tvOrderDesc2;  // 订单描述2
    @BindView(R.id.tv_loan_desc)
    TextView tvLoanDesc; // 借款金额描述

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


    @OnClick({R.id.tv_order_status1, R.id.tv_order_status2, R.id.tv_order_status3, R.id.tv_order_status4, R.id.tv_order_status5, R.id.tv_order_status6, R.id.btn_status_operate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_order_status2:  // 认证中 | 受理中 2


//                setOrderStatusInfo(1, 2);
                break;
            case R.id.tv_order_status3:  // 审核中
//                setOrderStatusInfo(1, 3);
                break;
            case R.id.tv_order_status4:  // 领取金额
//                setOrderStatusInfo(1, 4);
                break;
            case R.id.tv_order_status5:  // 放款中
//                setOrderStatusInfo(1, 5);
                break;
            case R.id.tv_order_status6:  // 还款中
//                setOrderStatusInfo(1, 6);
                break;

            case R.id.btn_status_operate:
                jumpToNextPage(loanOrderBean);
                break;
        }
    }

    private void loadingLoanOrder() {
        Map<String, String> map = new HashMap<>();
        mPresenter.getCurrentOrderDetail(map);
    }

    private void getCurrentEffectiveOrder() {
        Map<String, String> map = new HashMap<>();
        mPresenter.getCurrentEffectiveLoanOrder(map);
    }


    private void jumpToNextPage(LoanOrderBean loanOrderBean) {
        if (loanOrderBean == null) {
            ToastAlone.showLongToast(this, "获取订单信息失败,请重试");
            return;
        } else {
            String stepStatus = loanOrderBean.getStep_status();
            String orderStatus = loanOrderBean.getOrder_status();
            if (stepStatus.equals("2") && orderStatus.equals("10")) {
                // 前往认证
                Intent intent = new Intent(this, AuthCenterActivity.class);
                startActivity(intent);
            } else if (stepStatus.equals("2") && orderStatus.equals("9")) {
                // 推送拒件 重新申请

            } else if (stepStatus.equals("4") && orderStatus.equals("2")) {
                // 领取金额
                LogUtils.d("领取金额,stepStatus:" + stepStatus + ",orderStatus:" + orderStatus);
                Intent intent = new Intent(this, ReceiveMoneyActivity.class);
                startActivity(intent);
            } else if (stepStatus.equals("4") && orderStatus.equals("4")) {
                // 验证问题
                LogUtils.d("验证问题,stepStatus:" + stepStatus + ",orderStatus:" + orderStatus);

            } else if (stepStatus.equals("4") && orderStatus.equals("11")) {
                // 领取超时 重新申请
                //TODO 此处需要发送请求至服务端 将件作废处理
                finish();
            } else if (stepStatus.equals("5") && orderStatus.equals("7")) {
                // 前往修改 银行卡修改页面
                LogUtils.d("放款失败---前往修改,stepStatus:" + stepStatus + ",orderStatus:" + orderStatus);
                Intent intent = new Intent(this, BankcardInfoActivity.class);
                intent.putExtra("isEdit", "1");
                intent.putExtra("status", "1");
                startActivity(intent);
            } else if (stepStatus.equals("6") && orderStatus.equals("1")) {
                // 放款成功,立即还款
                LogUtils.d("放款成功,立即还款,stepStatus:" + stepStatus + ",orderStatus:" + orderStatus);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);
                finish();
            }
        }
    }

    private void initData() {

    }

    /**
     * 订单状态
     *
     * @param flag        普通用户|业务员
     * @param stepStatus  步骤码
     * @param orderStatus 订单状态
     */
    private void setOrderStatusInfo(String flag, String stepStatus, String orderStatus, String msg) {
        Drawable drawable = null;
        int orderStatusIocn = 0;
        if (stepStatus.equals("2") && orderStatus.equals("10")) {
            // 认证中
            drawable = getResources().getDrawable(R.mipmap.orderprocess_a);
            orderStatusIocn = R.mipmap.emptypage_authentication;
            setTextView(tvOrderStatus2, "认证中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus3, "审核中", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus4, "领取金额", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus5, "放款中", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus6, "还款中", getResources().getColor(R.color.order_uncomplected_color));
            tvLoanDesc.setText("申请金额");
            btnOrderOperate.setText("前往认证");
            btnOrderOperate.setEnabled(true);
        } else if (stepStatus.equals("7")) {
            // 受理中
            if (orderStatus.equals("10") || orderStatus.equals("4")) {
                btnOrderOperate.setText("受理中");
                tvLoanDesc.setText("申请金额");
                btnOrderOperate.setEnabled(false);
            } else if (orderStatus.equals("9")) {
                btnOrderOperate.setText("重新申请");
                ToastAlone.showLongToast(this, msg);
            }
            drawable = getResources().getDrawable(R.mipmap.orderprocess_a);
            orderStatusIocn = R.mipmap.emptypage_examine;
            setTextView(tvOrderStatus2, "受理中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus3, "审核中", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus4, "领取金额", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus5, "放款中", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus6, "还款中", getResources().getColor(R.color.order_uncomplected_color));
        } else if (stepStatus.equals("3")) {
            // 审核中
            if (orderStatus.equals("10") || orderStatus.equals("0") || orderStatus.equals("2")) {
                btnOrderOperate.setText("审核中");
                tvLoanDesc.setText("申请金额");
                orderStatusIocn = R.mipmap.emptypage_examine;
            } else if (orderStatus.equals("9")) {
                btnOrderOperate.setText("审核拒绝");
                tvLoanDesc.setText("申请金额");
                orderStatusIocn = R.mipmap.emptypage_fail;
                tvOrderDesc1.setText(msg);
            }
            btnOrderOperate.setEnabled(false);
            drawable = getResources().getDrawable(R.mipmap.orderprocess_b);
            setTextView(tvOrderStatus2, "认证中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus3, "审核中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus4, "领取金额", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus5, "放款中", getResources().getColor(R.color.order_uncomplected_color));
            setTextView(tvOrderStatus6, "还款中", getResources().getColor(R.color.order_uncomplected_color));
        } else if (stepStatus.equals("4")) {
            // 领取金额
            tvLoanDesc.setText("审批额度");
            if (orderStatus.equals("2")) {
                btnOrderOperate.setText("领取金额");
                tvOrderDesc1.setText("请尽快领取金额");
                orderStatusIocn = R.mipmap.emptypage_getthemoney;
                btnOrderOperate.setEnabled(true);
            } else if (orderStatus.equals("11")) {
                btnOrderOperate.setText("重新申请");
                tvOrderDesc1.setText("领取超时");
                orderStatusIocn = R.mipmap.emptypage_fail;
            } else if (orderStatus.equals("9")) {
                btnOrderOperate.setText("领取拒绝");
                orderStatusIocn = R.mipmap.emptypage_fail;
                tvOrderDesc1.setText(msg);
            }
            drawable = getResources().getDrawable(R.mipmap.orderprocess_c);
            setTextView(tvOrderStatus2, "认证中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus3, "审核中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus4, "领取金额", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus5, "放款中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus6, "还款中", getResources().getColor(R.color.order_uncomplected_color));
        } else if (stepStatus.equals("5")) {
            // 放款中
            tvLoanDesc.setText("审批额度");
            if (orderStatus.equals("2") || orderStatus.equals("3")) {
                btnOrderOperate.setText("放款中");
                orderStatusIocn = R.mipmap.emptypage_loan;
                btnOrderOperate.setEnabled(false);
            } else if (orderStatus.equals("7")) {
                orderStatusIocn = R.mipmap.emptypage_fail;
                btnOrderOperate.setText("前往修改");
                tvOrderDesc1.setText("放款失败");
                tvOrderDesc2.setText("核实并修改银行卡,等待系统自动放款");
            } else if (orderStatus.equals("9")) {
                orderStatusIocn = R.mipmap.emptypage_fail;
                btnOrderOperate.setText("放款拒绝");
                tvOrderDesc1.setText(msg);
            }
            drawable = getResources().getDrawable(R.mipmap.orderprocess_d);
            setTextView(tvOrderStatus2, "认证中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus3, "审核中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus4, "领取金额", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus5, "放款中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus6, "还款中", getResources().getColor(R.color.order_uncomplected_color));
        } else if (stepStatus.equals("6")) {
            // 立即还款
            tvLoanDesc.setText("审批额度");
            if (orderStatus.equals("1")) {
                btnOrderOperate.setText("立即还款");
                btnOrderOperate.setEnabled(true);
            }
            drawable = getResources().getDrawable(R.mipmap.orderprocess_e);
            orderStatusIocn = R.mipmap.emptypage_repayment;
            setTextView(tvOrderStatus2, "认证中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus3, "审核中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus4, "领取金额", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus5, "放款中", getResources().getColor(R.color.white));
            setTextView(tvOrderStatus6, "还款中", getResources().getColor(R.color.white));
        }
        ivOrderStatusIcon.setImageResource(orderStatusIocn);
        ivOrderStatus.setBackgroundDrawable(drawable);
    }

    private void setTextView(TextView tv, String content, int color) {
        tv.setText(content);
        tv.setTextColor(color);
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

    @Override
    protected void onResume() {
        super.onResume();
        loadingLoanOrder();
    }

    @Override
    public void onSuccessGet(String returnCode, LoanOrderBean bean) {
        loanOrderBean = bean;
        tvLoadMoney.setText(loanOrderBean.getLoan_amount() + "元");
        if (loanOrderBean.getZq_unit().equals("1")) {
            tvLoadPeriod.setText(loanOrderBean.getLoan_zq() + "天");
        } else if (loanOrderBean.getZq_unit().equals("2")) {
            tvLoadPeriod.setText(loanOrderBean.getLoan_zq() + "周");
        }
        setOrderStatusInfo("", loanOrderBean.getStep_status(), loanOrderBean.getOrder_status(), "");

        if (BaseApplication.isSalesman.equals(Config.USER_SALESMAN)) {
            //业务员
        } else {

        }

    }

    @Override
    public void onSuccessGetEffectiveOrder(String api, String result) {

    }

    @Override
    public void onFail(String apiCode, String failMsg) {
        ToastAlone.showLongToast(this, failMsg);
    }

    @Override
    public void onError(String apiCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
    }


}
