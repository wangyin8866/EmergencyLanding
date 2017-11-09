package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.ReceiveMoneyBean;
import com.zyjr.emergencylending.ui.home.View.ReceiveMoneyView;
import com.zyjr.emergencylending.ui.home.presenter.ReceiveMoneyPresenter;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.btn_receive_quickly)
    Button btnReceiveQuickly;
    @BindView(R.id.cb_check)
    CheckBox cbxAgree;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry; // 网络加载失败时重试
    @BindView(R.id.sv_main)
    ScrollView svMain;  // 主布局

    private ReceiveMoneyBean receiveMoneyBean = null;
    private ReceiveMoneyBean.Contract jiekuanContract = null;
    private ReceiveMoneyBean.Contract xinyongContract = null;
    private ReceiveMoneyBean.Contract zhanghuContract = null;
    private ReceiveMoneyBean.Contract huankuanContract = null;

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

    @OnClick({R.id.tv_loan_agreement_1, R.id.tv_loan_agreement_2, R.id.tv_loan_agreement_3, R.id.tv_loan_agreement_4, R.id.btn_receive_quickly, R.id.cb_check, R.id.btn_retry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_loan_agreement_1: // 借款协议
                if (jiekuanContract == null) {
                    return;
                }
                PdfViewerActivity.jumpToPdfView(ReceiveMoneyActivity.this, jiekuanContract.getContract_name(), jiekuanContract.getContract_url());
                break;

            case R.id.tv_loan_agreement_2: // 信用管理服务协议
                if (xinyongContract == null) {
                    return;
                }
                PdfViewerActivity.jumpToPdfView(ReceiveMoneyActivity.this, xinyongContract.getContract_name(), xinyongContract.getContract_url());
                break;

            case R.id.tv_loan_agreement_3: // 账户管理与数据服务协议
                if (zhanghuContract == null) {
                    return;
                }
                PdfViewerActivity.jumpToPdfView(ReceiveMoneyActivity.this, zhanghuContract.getContract_name(), zhanghuContract.getContract_url());
                break;

            case R.id.tv_loan_agreement_4: // 还款明细说明
                if (huankuanContract == null) {
                    return;
                }
                PdfViewerActivity.jumpToPdfView(ReceiveMoneyActivity.this, huankuanContract.getContract_name(), huankuanContract.getContract_url());
                break;

            case R.id.cb_check:

                break;

            case R.id.btn_receive_quickly:
                validateData();

                break;

            case R.id.btn_retry:
                loadingReceiveMoneyInfo();
                break;
        }
    }

    private void validateData() {
        if (receiveMoneyBean == null) {
            ToastAlone.showLongToast(this, "获取信息失败,请重试");
            return;
        }
        if (!cbxAgree.isChecked()) {
            ToastAlone.showLongToast(this, "请阅读并同意签约上述合同");
            return;
        }
        confirmReceiveInfo();

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

    private void showReceiveMoneyInfo(ReceiveMoneyBean bean) {
        tvLoanMoney.setText(bean.getLoan_amount()); // 借款金额
        // 区分 天/周
        if (bean.getLoan_unit().equals("1")) {
            tvLoanPeriod.setText(bean.getLoan_period() + "天"); // 借款周期 (天)
            tvPeriodAmount.setText(bean.getPeriod_amount() + "元/期");  // 期还款额  元/期
        } else if (bean.getLoan_unit().equals("2")) {
            tvLoanPeriod.setText(bean.getLoan_period() + "周"); // 借款周期 (周)
            tvPeriodAmount.setText(bean.getPeriod_amount() + "元/周");  // 期还款额 元/周
        }
        List<ReceiveMoneyBean.Contract> contractList = bean.getLoan_contract();
        for (int i = 0; i < contractList.size(); i++) {
            ReceiveMoneyBean.Contract contract = contractList.get(i);
            switch (contract.getContract_no()) {
                case "loanAgreement": // 借款协议
                    jiekuanContract = contract;
                    break;

                case "creditManagement":  // 信用管理
                    xinyongContract = contract;
                    break;

                case "accountManagement": // 账户管理与服务
                    zhanghuContract = contract;
                    break;

                case "reimbursementDetail": // 还款说明协议
                    huankuanContract = contract;
                    break;
            }
        }

    }

    private void loadingReceiveMoneyInfo() {
        Map<String, String> map = new HashMap<>();
        mPresenter.getReceiveMoneyInfo(map);
    }

    private void confirmReceiveInfo() {
        Map<String, String> map = new HashMap<>();
        mPresenter.confirmReceiveInfo(map);
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
    public void onSuccessGet(String apiCode, ReceiveMoneyBean bean) {
        showSuccess();
        receiveMoneyBean = bean;
        showReceiveMoneyInfo(receiveMoneyBean);
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
    public void onSuccessConfirmReceive(String apiCode, String msg) {
        Intent intent = new Intent(this, SendLoanProcessingActivity.class);
        startActivity(intent);
        finish();
    }


}
