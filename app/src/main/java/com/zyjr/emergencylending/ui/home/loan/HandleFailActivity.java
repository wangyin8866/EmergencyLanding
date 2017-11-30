package com.zyjr.emergencylending.ui.home.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.ActivityCollector;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.LoanOrderBean;
import com.zyjr.emergencylending.entity.MyBorrow;
import com.zyjr.emergencylending.ui.h5.H5WebView;
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
    @BindView(R.id.layout_recommend)
    RelativeLayout layoutRecommend;

    private String stepStatus = "";
    private String orderStatus = "";
    private String flag = "";
    private String msg = "";
    private LoanOrderBean loanOrderBean = null;
    private String title = ""; // title
    private String pushUrl = ""; // 外推url

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
                H5WebView.skipH5WebView(this, title, pushUrl);
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
        String jumpFlag = intent.getStringExtra("jumpFlag");
        if (StringUtil.isNotEmpty(jumpFlag)) {
            if (jumpFlag.equals("order")) {
                // 从订单页面过来的 (做废件处理)
                loanOrderBean = (LoanOrderBean) intent.getSerializableExtra("loanOrderBean");
                pushUrl = loanOrderBean.getOut_push_url();
                title = loanOrderBean.getOut_push_title();
                if (StringUtil.isNotEmpty(pushUrl)) {
                    layoutRecommend.setVisibility(View.VISIBLE);
                }
                String failStatus = intent.getStringExtra("failStatus");
                String failDesc = intent.getStringExtra("failDesc");
                tvApplyResult.setText(failStatus);
                tvApplyResultDesc.setText(failDesc);
                deleteLoanOrder();
            } else if (jumpFlag.equals("brrow")) {
                MyBorrow.ResultBean.HisBorrowListBean borrowBean = (MyBorrow.ResultBean.HisBorrowListBean) intent.getSerializableExtra("bean");
                pushUrl = borrowBean.getOut_push_url();
                title = borrowBean.getOut_push_title();
                if (StringUtil.isNotEmpty(pushUrl)) {
                    layoutRecommend.setVisibility(View.VISIBLE);
                }
                tvApplyResult.setText(borrowBean.getLoan_msg());
                deleteLoanOrder();
            } else if (jumpFlag.equals("precheck")) {
                String flag = intent.getStringExtra("flag");
                String msg = intent.getStringExtra("msg");
                tvApplyResult.setText(msg);
            }
        }
    }

    // 件废除
    private void deleteLoanOrder() {
        Map<String, String> map = new HashMap<>();
        mPresenter.deleteLoanOrder(map);
    }

    @Override
    public void onFail(String apiCode, String failMsg) {
        ToastAlone.showShortToast(this, failMsg);
    }

    @Override
    public void onError(String apiCode, String errorMsg) {
        ToastAlone.showShortToast(this, errorMsg);
    }

    @Override
    public void onSuccessDeleteLoanOrder(String api, String result) {
        // 废件成功后,出栈处理
        ActivityCollector.getInstance().popActivity(LoanOrderStatusActivity.class);
    }

}
