package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zyjr.emergencylending.MainActivity;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.LoanOrderBean;
import com.zyjr.emergencylending.ui.home.View.LoanOrderView;
import com.zyjr.emergencylending.ui.home.loan.basicInfo.BankcardInfoActivity;
import com.zyjr.emergencylending.ui.home.presenter.LoanOrderPresenter;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;

import org.w3c.dom.Text;

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
 * <p>
 */
public class LoanOrderStatusActivity extends BaseActivity<LoanOrderPresenter, LoanOrderView> implements LoanOrderView {
    @BindView(R.id.top_bar)
    TopBar topBar;
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
    @BindView(R.id.tv_product_type)
    TextView tvProductType; // 产品类型

    @BindView(R.id.ll_retry)
    LinearLayout llRetry; // 网络加载失败时重试
    @BindView(R.id.sv_main)
    ScrollView svMain;  // 主布局
    @BindView(R.id.ll_loan_info)
    LinearLayout llLoanInfo; // 借款信息

    // 订单状态线
    @BindView(R.id.view_line_status1_right)
    View viewLineStatus1Right; // 状态1右侧色
    @BindView(R.id.iv_order_status2)
    ImageView ivOrderStatus2; // 状态2
    @BindView(R.id.view_line_status2_left)
    View viewLineStatus2Left; // 状态2左侧色
    @BindView(R.id.view_line_status2_right)
    View viewLineStatus2Right; // 状态2右侧色
    @BindView(R.id.iv_order_status3)
    ImageView ivOrderStatus3; // 状态3
    @BindView(R.id.view_line_status3_left)
    View viewLineStatus3Left; // 状态3左侧色
    @BindView(R.id.view_line_status3_right)
    View viewLineStatus3Right; // 状态3右侧色
    @BindView(R.id.iv_order_status4)
    ImageView ivOrderStatus4; // 状态4
    @BindView(R.id.view_line_status4_left)
    View viewLineStatus4Left; // 状态4左侧色
    @BindView(R.id.view_line_status4_right)
    View viewLineStatus4Right; // 状态4右侧色
    @BindView(R.id.iv_order_status5)
    ImageView ivOrderStatus5; // 状态5
    @BindView(R.id.view_line_status5_left)
    View viewLineStatus5Left; // 状态5左侧色
    @BindView(R.id.view_line_status5_right)
    View viewLineStatus5Right; // 状态5右侧色
    @BindView(R.id.iv_order_status6)
    ImageView ivOrderStatus6; // 状态6
    @BindView(R.id.view_line_status6_left)
    View viewLineStatus6Left; // 状态6左侧色

    private LoanOrderBean loanOrderBean = null;
    private String failStatus = ""; // 失败状态
    private String failDesc = ""; // 失败信息

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
    }


    @OnClick({R.id.btn_retry, R.id.btn_status_operate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_status_operate:
                jumpToNextPage(loanOrderBean);
                break;

            case R.id.btn_retry:
                loadingLoanOrder();
                break;
        }
    }

    private void loadingLoanOrder() {
        Map<String, String> map = new HashMap<>();
        mPresenter.getCurrentOrderDetail(map);
    }

    private void deleteLoanOrder() {
        Map<String, String> map = new HashMap<>();
        mPresenter.deleteLoanOrder(map);
    }


    private void jumpToNextPage(LoanOrderBean loanOrderBean) {
        if (loanOrderBean == null) {
            ToastAlone.showLongToast(this, "获取订单信息失败,请重试");
            return;
        } else {
            String stepStatus = loanOrderBean.getStep_status();
            String orderStatus = loanOrderBean.getOrder_status();
            String isClerkOpt = loanOrderBean.getIs_clerk_opt(); // 是否业务员操作 1:业务员;0:用户
            if (StringUtil.isEmpty(stepStatus)) {
                LogUtils.d("该笔订单步骤码为空");
                return;
            }
            if (StringUtil.isEmpty(orderStatus)) {
                LogUtils.d("该笔订单订单状态码为空");
                return;
            }
            if (stepStatus.equals("2")) {
                // 认证中
                if (orderStatus.equals("10")) {
                    Intent intent = new Intent(this, AuthCenterActivity.class);  // 前往认证
                    startActivity(intent);
                }
            } else if (stepStatus.equals("7")) {
                // 受理中
                if (orderStatus.equals("9")) {
                    // 审批拒件
                    jumpToFailPage(loanOrderBean);
                }
            } else if (stepStatus.equals("3")) {
                // 审核中
                if (orderStatus.equals("9")) {
                    // 审核拒绝
                    jumpToFailPage(loanOrderBean);
                }
            } else if (stepStatus.equals("4")) {
                // 领取金额 (验证问题、领取金额、领取超时、领取拒件)
                if (orderStatus.equals("2")) {
                    if (isClerkOpt.equals("1")) {
                        // 是业务员帮录件(验证问题)
                        LogUtils.d("(业务员录)验证问题,stepStatus:" + stepStatus + ",orderStatus:" + orderStatus + ",isClerkOpt:" + isClerkOpt);
                        startActivity(new Intent(this, QuestionValidateActivity.class));
                    } else if (isClerkOpt.equals("0")) {
                        // 领取金额
                        LogUtils.d("(普通录)领取金额,stepStatus:" + stepStatus + ",orderStatus:" + orderStatus + ",isClerkOpt:" + isClerkOpt);
                        Intent intent = new Intent(this, ReceiveMoneyActivity.class);
                        startActivity(intent);
                    }
                } else if (orderStatus.equals("11")) {
                    // 领取超时(重新申请)
                    deleteLoanOrder();
                } else if (orderStatus.equals("9")) {
                    // 领取拒件(领取拒绝)
                    jumpToFailPage(loanOrderBean);
                }
            } else if (stepStatus.equals("5")) {
                // 放款中
                if (orderStatus.equals("7")) {
                    // 放款失败,前往修改 银行卡修改页面
                    LogUtils.d("放款失败---前往修改,stepStatus:" + stepStatus + ",orderStatus:" + orderStatus);
                    Intent intent = new Intent(this, BankcardInfoActivity.class);
                    intent.putExtra("isEdit", "1");
                    intent.putExtra("status", "1");
                    startActivity(intent);
                } else if (orderStatus.equals("9")) {
                    // 放款拒绝
                    jumpToFailPage(loanOrderBean);
                }
            } else if (stepStatus.equals("6")) {
                // 还款中
                if (orderStatus.equals("1")) {
                    LogUtils.d("放款成功,立即还款,stepStatus:" + stepStatus + ",orderStatus:" + orderStatus);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("index", 1);
                    startActivity(intent);
                    finish();
                }
            } else if (stepStatus.equals("7")) {
                // 推送拒件 (领取拒绝)
                jumpToFailPage(loanOrderBean);
            }
        }
    }

    private void jumpToFailPage(LoanOrderBean loanOrderBean) {
        Intent intent = new Intent(this, HandleFailActivity.class);
        intent.putExtra("loanOrderBean", loanOrderBean);
        intent.putExtra("jumpFlag", "order");
        intent.putExtra("failStatus", failStatus);
        intent.putExtra("failDesc", failDesc);
        startActivity(intent);
    }

    /**
     * 订单状态
     *
     * @param isClerkOpt  普通用户|业务员
     * @param productId   线上线下标识
     * @param amount      借款金额
     * @param stepStatus  步骤码
     * @param orderStatus 订单状态
     * @param orderDesc   订单描述
     */
    private void setOrderStatusInfo(String isClerkOpt, String productId, String amount, String stepStatus, String orderStatus, String orderDesc) {
        int orderStatusIocn = 0;
        if (stepStatus.equals("2") && orderStatus.equals("10")) {
            // 认证中
            llLoanInfo.setVisibility(View.GONE);
            btnOrderOperate.setVisibility(View.VISIBLE);
            orderDesc("认证未完成，请前往认证！", "");
            btnOrderOperate.setText("前往认证");
            btnOrderOperate.setEnabled(true);
            orderStatusIocn = R.mipmap.emptypage_authentication;
            setTextView(tvOrderStatus2, "认证中", R.color.white);
            setTextView(tvOrderStatus3, "审核中", R.color.order_uncompleted_color);
            setTextView(tvOrderStatus4, "领取金额", R.color.order_uncompleted_color);
            setTextView(tvOrderStatus5, "放款中", R.color.order_uncompleted_color);
            setTextView(tvOrderStatus6, "还款中", R.color.order_uncompleted_color);
        } else if (stepStatus.equals("7")) {
            llLoanInfo.setVisibility(View.VISIBLE);
            showLoanInfo();
            // 受理中
            if (orderStatus.equals("10") || orderStatus.equals("4")) {
                btnOrderOperate.setVisibility(View.GONE);
                orderDesc("订单正在受理中，请耐心等待！", "");
                tvLoanDesc.setText("申请金额");
                orderStatusIocn = R.mipmap.emptypage_examine;
            } else if (orderStatus.equals("9")) {
                btnOrderOperate.setVisibility(View.VISIBLE);
                btnOrderOperate.setEnabled(true);
                tvLoanDesc.setText("申请金额");
                orderStatusIocn = R.mipmap.emptypage_fail;
                orderDesc(orderDesc, "");
                btnOrderOperate.setText("审批拒件");
                failStatus = "审批拒件";
                failDesc = orderDesc;
            }
            setTextView(tvOrderStatus2, "受理中", R.color.white);
            setTextView(tvOrderStatus3, "审核中", R.color.order_uncompleted_color);
            setTextView(tvOrderStatus4, "领取金额", R.color.order_uncompleted_color);
            setTextView(tvOrderStatus5, "放款中", R.color.order_uncompleted_color);
            setTextView(tvOrderStatus6, "还款中", R.color.order_uncompleted_color);
        } else if (stepStatus.equals("3")) {
            // 审核中
            if (orderStatus.equals("10") || orderStatus.equals("0") || orderStatus.equals("2")) {
                if (productId.equals("0")) {
                    // 线上
                    if (StringUtil.isEmpty(amount)) {
                        // 首贷
                        llLoanInfo.setVisibility(View.GONE);
                    } else {
                        llLoanInfo.setVisibility(View.VISIBLE);
                        showLoanInfo();
                    }
                    tvLoanDesc.setText("审批金额");
                } else if (productId.equals("1")) {
                    // 线下
                    llLoanInfo.setVisibility(View.VISIBLE);
                    tvLoanDesc.setText("申请金额");
                }
                btnOrderOperate.setVisibility(View.GONE);
                orderDesc("订单正在审核中，请耐心等待！", "");
                orderStatusIocn = R.mipmap.emptypage_examine;
            } else if (orderStatus.equals("9")) {
                llLoanInfo.setVisibility(View.VISIBLE);
                showLoanInfo();
                btnOrderOperate.setVisibility(View.VISIBLE);
                btnOrderOperate.setText("审批拒件");
                tvLoanDesc.setText("审批金额");
                orderStatusIocn = R.mipmap.emptypage_fail;
                orderDesc(orderDesc, "");
                failStatus = "审批拒件";
                failDesc = orderDesc;
            }
            setTextView(tvOrderStatus2, "认证中", R.color.white);
            setTextView(tvOrderStatus3, "审核中", R.color.white);
            setTextView(tvOrderStatus4, "领取金额", R.color.order_uncompleted_color);
            setTextView(tvOrderStatus5, "放款中", R.color.order_uncompleted_color);
            setTextView(tvOrderStatus6, "还款中", R.color.order_uncompleted_color);
        } else if (stepStatus.equals("4")) {
            // 领取金额
            llLoanInfo.setVisibility(View.VISIBLE);
            showLoanInfo();
            btnOrderOperate.setVisibility(View.VISIBLE);
            tvLoanDesc.setText("审批金额");
            if (orderStatus.equals("2")) {
                if (isClerkOpt.equals("1")) {
                    // 订单是业务员操作
                    btnOrderOperate.setText("验证问题");
                    orderDesc("完成验证后，即可领取金额！", "");
                    btnOrderOperate.setEnabled(true);
                    orderStatusIocn = R.mipmap.emptypage_getthemoney;
                } else {
                    btnOrderOperate.setText("领取金额");
                    orderDesc("请尽快领取金额，过期将失效！", "");
                    orderStatusIocn = R.mipmap.emptypage_getthemoney;
                    btnOrderOperate.setEnabled(true);
                }
            } else if (orderStatus.equals("11")) {
                btnOrderOperate.setEnabled(true);
                btnOrderOperate.setText("重新申请");
                orderDesc("领取金额超时", "");
                orderStatusIocn = R.mipmap.emptypage_fail;
            } else if (orderStatus.equals("9")) {
                btnOrderOperate.setEnabled(true);
                btnOrderOperate.setText("领取拒件");
                orderStatusIocn = R.mipmap.emptypage_fail;
                orderDesc(orderDesc, "");
                failStatus = "领取拒件";
                failDesc = orderDesc;
            }
            setTextView(tvOrderStatus2, "认证中", R.color.white);
            setTextView(tvOrderStatus3, "审核中", R.color.white);
            setTextView(tvOrderStatus4, "领取金额", R.color.white);
            setTextView(tvOrderStatus5, "放款中", R.color.white);
            setTextView(tvOrderStatus6, "还款中", R.color.order_uncompleted_color);
        } else if (stepStatus.equals("5")) {
            // 放款中
            llLoanInfo.setVisibility(View.VISIBLE);
            showLoanInfo();
            tvLoanDesc.setText("审批金额");
            if (orderStatus.equals("2") || orderStatus.equals("3")) {
                llLoanInfo.setVisibility(View.VISIBLE);
                btnOrderOperate.setVisibility(View.GONE);
                orderDesc("订单正在放款中，请耐心等待！", "");
                orderStatusIocn = R.mipmap.emptypage_loan;
            } else if (orderStatus.equals("7")) {
                btnOrderOperate.setVisibility(View.VISIBLE);
                btnOrderOperate.setEnabled(true);
                orderStatusIocn = R.mipmap.emptypage_fail;
                btnOrderOperate.setText("前往修改");
                orderDesc("放款失败", "核实并修改银行卡,等待系统自动放款");
            } else if (orderStatus.equals("9")) {
                btnOrderOperate.setVisibility(View.VISIBLE);
                btnOrderOperate.setEnabled(true);
                btnOrderOperate.setText("放款拒件");
                orderStatusIocn = R.mipmap.emptypage_fail;
                orderDesc(orderDesc, "");
                failStatus = "放款拒件";
                failDesc = orderDesc;
            }
            setTextView(tvOrderStatus2, "认证中", R.color.white);
            setTextView(tvOrderStatus3, "审核中", R.color.white);
            setTextView(tvOrderStatus4, "领取金额", R.color.white);
            setTextView(tvOrderStatus5, "放款中", R.color.white);
            setTextView(tvOrderStatus6, "还款中", R.color.order_uncompleted_color);
        } else if (stepStatus.equals("6")) {
            llLoanInfo.setVisibility(View.VISIBLE);
            showLoanInfo();
            // 立即还款
            tvLoanDesc.setText("审批金额");
            if (orderStatus.equals("1")) {
                btnOrderOperate.setVisibility(View.VISIBLE);
                btnOrderOperate.setText("立即还款");
                orderDesc("按时还款，保持良好的信誉，有助于提额哦！", "");
                btnOrderOperate.setEnabled(true);
            }
            orderStatusIocn = R.mipmap.emptypage_repayment;
            setTextView(tvOrderStatus2, "认证中", R.color.white);
            setTextView(tvOrderStatus3, "审核中", R.color.white);
            setTextView(tvOrderStatus4, "领取金额", R.color.white);
            setTextView(tvOrderStatus5, "放款中", R.color.white);
            setTextView(tvOrderStatus6, "还款中", R.color.white);
        }
        ivOrderStatusIcon.setImageResource(orderStatusIocn);
        setLineColor(stepStatus);
    }

    private void setTextView(TextView tv, String content, int color) {
        tv.setText(content);
        tv.setTextColor(getResources().getColor(color));
    }

    private void orderDesc(String desc1, String desc2) {
        tvOrderDesc1.setText(desc1);
        tvOrderDesc2.setText(desc2);
    }

    private void setLineColor(String stepCode) {
        if (stepCode.equals("2") || stepCode.equals("7")) {
            // 2认证中 ; 7受理中
            ivOrderStatus3.setImageResource(R.mipmap.orderprocess_oval_c);
            ivOrderStatus4.setImageResource(R.mipmap.orderprocess_oval_c);
            ivOrderStatus5.setImageResource(R.mipmap.orderprocess_oval_c);
            ivOrderStatus6.setImageResource(R.mipmap.orderprocess_oval_c);
            viewLineStatus2Right.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus3Left.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus3Right.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus4Left.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus4Right.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus5Left.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus5Right.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus6Left.setBackgroundResource(R.color.order_uncompleted_color);
        } else if (stepCode.equals("3")) {
            // 审核中
            ivOrderStatus2.setImageResource(R.mipmap.orderprocess_oval_a);
            ivOrderStatus3.setImageResource(R.mipmap.orderprocess_oval_b);
            ivOrderStatus4.setImageResource(R.mipmap.orderprocess_oval_c);
            ivOrderStatus5.setImageResource(R.mipmap.orderprocess_oval_c);
            ivOrderStatus6.setImageResource(R.mipmap.orderprocess_oval_c);
            viewLineStatus2Right.setBackgroundResource(R.color.white);
            viewLineStatus3Left.setBackgroundResource(R.color.white);
            viewLineStatus3Right.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus4Left.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus4Right.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus5Left.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus5Right.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus6Left.setBackgroundResource(R.color.order_uncompleted_color);
        } else if (stepCode.equals("4")) {
            // 领取金额
            ivOrderStatus2.setImageResource(R.mipmap.orderprocess_oval_a);
            ivOrderStatus3.setImageResource(R.mipmap.orderprocess_oval_a);
            ivOrderStatus4.setImageResource(R.mipmap.orderprocess_oval_b);
            ivOrderStatus5.setImageResource(R.mipmap.orderprocess_oval_c);
            ivOrderStatus6.setImageResource(R.mipmap.orderprocess_oval_c);
            viewLineStatus2Right.setBackgroundResource(R.color.white);
            viewLineStatus3Left.setBackgroundResource(R.color.white);
            viewLineStatus3Right.setBackgroundResource(R.color.white);
            viewLineStatus4Left.setBackgroundResource(R.color.white);
            viewLineStatus4Right.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus5Left.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus5Right.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus6Left.setBackgroundResource(R.color.order_uncompleted_color);
        } else if (stepCode.equals("5")) {
            // 放款中
            ivOrderStatus2.setImageResource(R.mipmap.orderprocess_oval_a);
            ivOrderStatus3.setImageResource(R.mipmap.orderprocess_oval_a);
            ivOrderStatus4.setImageResource(R.mipmap.orderprocess_oval_a);
            ivOrderStatus5.setImageResource(R.mipmap.orderprocess_oval_b);
            ivOrderStatus6.setImageResource(R.mipmap.orderprocess_oval_c);
            viewLineStatus2Right.setBackgroundResource(R.color.white);
            viewLineStatus3Left.setBackgroundResource(R.color.white);
            viewLineStatus3Right.setBackgroundResource(R.color.white);
            viewLineStatus4Left.setBackgroundResource(R.color.white);
            viewLineStatus4Right.setBackgroundResource(R.color.white);
            viewLineStatus5Left.setBackgroundResource(R.color.white);
            viewLineStatus5Right.setBackgroundResource(R.color.order_uncompleted_color);
            viewLineStatus6Left.setBackgroundResource(R.color.order_uncompleted_color);
        } else if (stepCode.equals("6")) {
            // 还款中
            ivOrderStatus2.setImageResource(R.mipmap.orderprocess_oval_a);
            ivOrderStatus3.setImageResource(R.mipmap.orderprocess_oval_a);
            ivOrderStatus4.setImageResource(R.mipmap.orderprocess_oval_a);
            ivOrderStatus5.setImageResource(R.mipmap.orderprocess_oval_a);
            ivOrderStatus6.setImageResource(R.mipmap.orderprocess_oval_b);
            viewLineStatus2Right.setBackgroundResource(R.color.white);
            viewLineStatus3Left.setBackgroundResource(R.color.white);
            viewLineStatus3Right.setBackgroundResource(R.color.white);
            viewLineStatus4Left.setBackgroundResource(R.color.white);
            viewLineStatus4Right.setBackgroundResource(R.color.white);
            viewLineStatus5Left.setBackgroundResource(R.color.white);
            viewLineStatus5Right.setBackgroundResource(R.color.white);
            viewLineStatus6Left.setBackgroundResource(R.color.white);
        }
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

    private void showError() {
        llRetry.setVisibility(View.VISIBLE);
        svMain.setVisibility(View.GONE);
    }

    private void showSuccess() {
        llRetry.setVisibility(View.GONE);
        svMain.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadingLoanOrder();
    }

    @Override
    public void onSuccessGet(String returnCode, LoanOrderBean bean) {
        loanOrderBean = bean;
        showSuccess();
        setOrderStatusInfo(loanOrderBean.getIs_clerk_opt(), loanOrderBean.getProduct_id(), loanOrderBean.getLoan_amount(), loanOrderBean.getStep_status(), loanOrderBean.getOrder_status(), loanOrderBean.getOrder_code_desc());
    }

    private void showLoanInfo() {
        if (loanOrderBean.getProduct_id().equals("0")) {
            tvProductType.setText("急速借款");
        } else if (loanOrderBean.getProduct_id().equals("1")) {
            tvProductType.setText("传统借款");
        }
        tvLoadMoney.setText(loanOrderBean.getLoan_amount() + "元");
        if (loanOrderBean.getZq_unit().equals("1")) {
            tvLoadPeriod.setText(loanOrderBean.getLoan_zq() + "天");
        } else if (loanOrderBean.getZq_unit().equals("2")) {
            tvLoadPeriod.setText(loanOrderBean.getLoan_zq() + "周");
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
        showError();
    }

    @Override
    public void onSuccessDeleteLoanOrder(String api, String result) {
        LogUtils.d("废件处理成功--" + result);
        finish();
    }


}
