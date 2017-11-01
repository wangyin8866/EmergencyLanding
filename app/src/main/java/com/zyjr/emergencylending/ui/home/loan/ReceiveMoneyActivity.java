package com.zyjr.emergencylending.ui.home.loan;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.ReceiveMoneyBean;
import com.zyjr.emergencylending.ui.home.View.ReceiveMoneyView;
import com.zyjr.emergencylending.ui.home.presenter.ReceiveMoneyPresenter;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/14
 * 备注: 确认领取金额
 */
public class ReceiveMoneyActivity extends BaseActivity<ReceiveMoneyPresenter, ReceiveMoneyView> implements ReceiveMoneyView {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.tv_loan_money)
    TextView tvLoanMoney; // 借款金额
    @BindView(R.id.tv_loan_period)
    TextView tvLoanPeriod;  // 借款周期
    @BindView(R.id.tv_period_amount)
    TextView tvPeriodAmount; // 期还款额
    @BindView(R.id.tv_loan_agreement_1)
    TextView tvLoanAgreement1; // 借款协议
    @BindView(R.id.tv_loan_agreement_2)
    TextView tvLoanAgreement2; // 信用管理服务协议
    @BindView(R.id.tv_loan_agreement_3)
    TextView tvLoanAgreement3; // 账户管理与数据管理协议
    @BindView(R.id.tv_loan_agreement_4)
    TextView tvLoanAgreement4; // 还款明细说明
    private ReceiveMoneyBean receiveMoneyBean = null;

    @Override
    protected ReceiveMoneyPresenter createPresenter() {
        return new ReceiveMoneyPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_money);
        ButterKnife.bind(this);

        init();
        loadingReceiveMoneyInfo();
    }

    @OnClick({R.id.tv_loan_agreement_1, R.id.tv_loan_agreement_2, R.id.tv_loan_agreement_3, R.id.tv_loan_agreement_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_loan_agreement_1:
                break;

            case R.id.tv_loan_agreement_2:
                break;

            case R.id.tv_loan_agreement_3:
                break;

            case R.id.tv_loan_agreement_4:
                break;
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

    private void loadingReceiveMoneyInfo() {
        Map<String, String> map = new HashMap<>();
        mPresenter.getReceiveMoneyInfo(map);
    }

    private void showReceiveMoneyInfo(ReceiveMoneyBean bean) {
        tvLoanMoney.setText(bean.getLoan_amount()); // 借款金额
        tvLoanPeriod.setText(bean.getLoan_period() + bean.getLoan_unit()); // 借款周期 (天|周)
        tvPeriodAmount.setText(bean.getPeriod_amount());  // 期还款额

    }

    @Override
    public void onSuccessGet(String apiCode, ReceiveMoneyBean bean) {
        receiveMoneyBean = bean;
        showReceiveMoneyInfo(receiveMoneyBean);
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
