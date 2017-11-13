package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
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
import com.zyjr.emergencylending.ui.home.View.HandleFailView;
import com.zyjr.emergencylending.ui.home.View.LoanOrderView;
import com.zyjr.emergencylending.ui.home.presenter.HandleFailPresenter;
import com.zyjr.emergencylending.ui.home.presenter.LoanOrderPresenter;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neil on 2017/10/16
 * 备注: 处理失败页面 包含审核失败和 申请失败
 * 1.黑名单 ;2.年龄不满足18-55岁
 */
public class HandleFailActivity extends BaseActivity<HandleFailPresenter, HandleFailView> implements HandleFailView {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.iv_apply_status_icon)
    ImageView ivApplyStatusIcon;
    @BindView(R.id.tv_apply_result)
    TextView tvApplyResult;
    @BindView(R.id.tv_apply_result_desc)
    TextView tvApplyResultDesc;
    @BindView(R.id.btn_apply_qunadai)
    Button btnApplyQunadai;
    private String stepStatus = "";
    private String orderStatus = "";

    @Override
    protected HandleFailPresenter createPresenter() {
        return new HandleFailPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_handle_fail);
        ButterKnife.bind(this);

        init();
        initGetData();
    }

    @OnClick({R.id.btn_apply_qunadai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_apply_qunadai:  // 去哪贷

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

    private void initGetData() {
        Intent intent = getIntent();
        stepStatus = intent.getStringExtra("stepStatus");
        orderStatus = intent.getStringExtra("orderStatus");
        if (StringUtil.isNotEmpty(stepStatus) && StringUtil.isNotEmpty(orderStatus)) {
            // 从订单页面过来的 (做废件处理)
            deleteLoanOrder();
        }
    }

    // 件废除
    private void deleteLoanOrder() {
        Map<String, String> map = new HashMap<>();
        mPresenter.deleteLoanOrder(map);
    }

    @Override
    public void onFail(String apiCode, String failMsg) {
        ToastAlone.showLongToast(this, failMsg);
    }

    @Override
    public void onError(String apiCode, String errorMsg) {
        ToastAlone.showLongToast(this, errorMsg);
    }

    @Override
    public void onSuccessDeleteLoanOrder(String api, String result) {
        ToastAlone.showLongToast(this, result);
    }

}
